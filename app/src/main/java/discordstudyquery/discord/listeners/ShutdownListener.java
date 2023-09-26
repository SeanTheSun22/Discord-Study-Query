package discordstudyquery.discord.listeners;

import javax.annotation.Nonnull;

import discordstudyquery.App;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShutdownListener extends ListenerAdapter {
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getChannelType() != ChannelType.PRIVATE) return;
        if (!event.getAuthor().getId().equals("313193470552440832")) return;
        if (event.getMessage().getContentRaw().toLowerCase().equals("stop")) {
            event.getChannel().sendMessage("stopping").queue(message -> {
                App.stop();
            });
        } else if (event.getMessage().getContentRaw().toLowerCase().equals("restart")) {
            event.getChannel().sendMessage("restarting").queue(message -> {
                App.restart();
            });
        }
    }
}
