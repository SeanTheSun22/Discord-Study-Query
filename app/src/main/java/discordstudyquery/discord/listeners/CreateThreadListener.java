package discordstudyquery.discord.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

import javax.annotation.Nonnull;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.guild.DiscordModel;

public class CreateThreadListener extends ListenerAdapter {
    DiscordModel model;
    public CreateThreadListener(DiscordModel model) {
        super();
        this.model = model;
    }
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        new CreateThreadThread(event).start();
    }

    class CreateThreadThread extends Thread {
        private MessageReceivedEvent event;
        public CreateThreadThread(MessageReceivedEvent event) {
            super();
            this.event = event;
        }

        public void run() {
            if (event.getAuthor().isBot() && !event.getAuthor().getName().equals("Study Query")) return;
            if (!(event.getChannel() instanceof TextChannel)) return;

            String content = event.getMessage().getContentRaw();
            String[] contentSplit = content.split(" ");
            if (contentSplit.length != 4 || !contentSplit[0].equals("query")) return;  

            TextChannel textChannel = (TextChannel) event.getChannel();
            String threadName = contentSplit[1] + " " + contentSplit[2];
            List<ThreadChannel> initialThreads = textChannel.getThreadChannels();

            while (!model.contains(textChannel.getIdLong())) try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            
            textChannel.createThreadChannel(threadName).queue();

            List<ThreadChannel> threads;
            while ((threads = textChannel.getThreadChannels()).size() == initialThreads.size()) try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            for (ThreadChannel thread : threads) {
                if (thread.getName().equals(threadName)) {
                    DatabaseEditor.pushThreadToSQL(thread.getIdLong(), event.getMessage().getAuthor().getIdLong());
                    thread.sendMessage("ok buddy").queue();
                    return;
                }
            }
            textChannel.sendMessage("lol something broke that thread isnt initialised properly :(").queue();
        }
 
    }
}