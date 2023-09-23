package discordstudyquery.discord.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

import javax.annotation.Nonnull;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.guild.DiscordModel;
import discordstudyquery.guild.structure.Channel;

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
            if (contentSplit.length < 4 || !contentSplit[0].equals("query")) return;
            contentSplit[3] = String.join(" ", Arrays.copyOfRange(contentSplit, 3, contentSplit.length));

            TextChannel textChannel = (TextChannel) event.getChannel();
            String threadName = contentSplit[1] + " " + contentSplit[2];

            while (!model.contains(textChannel.getIdLong())) try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            
            textChannel.createThreadChannel(threadName).queue((thread) -> {
                    DatabaseEditor.pushThreadToSQL(thread.getIdLong(), event.getMessage().getAuthor().getIdLong());
                    thread.sendMessage("Thank you for your query, someone will be with you shortly. Use 'query close' when you question has been answered.").queue();
                    String questionString = "Original question: " + contentSplit[3];
                    if (((Channel) model.getComponentWithID(textChannel.getIdLong())).getPingRoleID() == -1L) {
                        questionString +="\n<@&" + ((Channel) model.getComponentWithID(textChannel.getIdLong())).getPingRoleID() + "> pls help :pleading_face:";
                    }
                    thread.sendMessage(questionString).queue();
            });
        }
    }
}