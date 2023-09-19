package discordstudyquery.discord.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class CreateThreadListener extends ListenerAdapter {
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        // Check if the relevant command is being used
        if (event.getAuthor().isBot()) return;
        String content = event.getMessage().getContentRaw();
        String[] contentSplit = content.split(" ");
        if (contentSplit.length != 4 || !contentSplit[0].equals("query")) return;
        
        // Create the thread
        if (event.getChannel() instanceof TextChannel) {
            TextChannel textChannel = (TextChannel) event.getChannel();
            String threadName = contentSplit[1] + " " + contentSplit[2];
            textChannel.createThreadChannel(threadName).queue();
            textChannel.sendMessage("Thread created!").queue();
        }     
    }
}
