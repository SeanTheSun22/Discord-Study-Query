package discordstudyquery.discord.listeners;

import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.discord.jdaadapter.CategoryJDAAdapter;
import discordstudyquery.discord.jdaadapter.ChannelJDAAdapter;
import discordstudyquery.discord.jdaadapter.GuildJDAAdapter;
import discordstudyquery.discord.jdaadapter.ThreadJDAAdapter;
import discordstudyquery.guild.DiscordModel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

import javax.annotation.Nonnull;
@SuppressWarnings("null")
public class LoadComponentListener extends ListenerAdapter {
    DiscordModel model;
    public LoadComponentListener(DiscordModel model) {
        super();
        this.model = model;
    }
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        new LoadComponentThread(event).start();
    }

    class LoadComponentThread extends Thread {
        private MessageReceivedEvent event;
        public LoadComponentThread(MessageReceivedEvent event) {
            super();
            this.event = event;
        }
        public void run() {
            if (model.contains(Long.valueOf(event.getChannel().getId()))) return;
            if (event.getAuthor().isBot() && !(event.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong())) return;
            ArrayList<AbstractJDAAdapter> componentStack = new ArrayList<AbstractJDAAdapter>();
            switch(event.getChannelType()) {
                case TEXT:
                    componentStack.add(new GuildJDAAdapter(event.getGuild().getName(), event.getGuild().getIdLong()));
                    if (event.getMessage().getCategory() == null) {
                        componentStack.add(new ChannelJDAAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), event.getGuild().getIdLong()));
                    } else {
                        componentStack.add(new CategoryJDAAdapter(event.getMessage().getCategory().getName(), event.getMessage().getCategory().getIdLong(), event.getGuild().getIdLong()));
                        componentStack.add(new ChannelJDAAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), event.getMessage().getCategory().getIdLong()));
                    } 
                    break;
                case GUILD_PUBLIC_THREAD:
                    componentStack.add(new GuildJDAAdapter(event.getGuild().getName(), event.getGuild().getIdLong()));
                    if (event.getMessage().getCategory() == null) {
                        componentStack.add(new ChannelJDAAdapter(((ThreadChannel) event.getChannel()).getParentChannel().getName(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong(), event.getGuild().getIdLong()));
                    } else {
                        componentStack.add(new CategoryJDAAdapter(event.getMessage().getCategory().getName(), event.getMessage().getCategory().getIdLong(), event.getGuild().getIdLong()));
                        componentStack.add(new ChannelJDAAdapter(((ThreadChannel) event.getChannel()).getParentChannel().getName(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong(), event.getMessage().getCategory().getIdLong()));
                    }
                    componentStack.add(new ThreadJDAAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong()));
                    break;
                default:
                    return;
            }
            model.loadComponent(componentStack);
        }
    }
}
