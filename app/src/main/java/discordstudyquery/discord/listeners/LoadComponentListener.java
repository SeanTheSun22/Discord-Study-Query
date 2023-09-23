package discordstudyquery.discord.listeners;

import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.discord.jdaadapter.CategoryJDAAdapter;
import discordstudyquery.discord.jdaadapter.ChannelJDAAdapter;
import discordstudyquery.discord.jdaadapter.GuildJDAAdapter;
import discordstudyquery.discord.jdaadapter.ThreadJDAAdapter;
import discordstudyquery.guild.DiscordModel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

import javax.annotation.Nonnull;
@SuppressWarnings("null")
public class LoadComponentListener extends ListenerAdapter {
    DiscordModel model;
    private final Object lock = new Object();
    public LoadComponentListener(DiscordModel model) {
        super();
        this.model = model;
    }
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        new LoadComponentMessageThread(event).start();
    }
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        new LoadComponentReactionThread(event).start();
    }

    class LoadComponentMessageThread extends Thread {
        private MessageReceivedEvent event;
        public LoadComponentMessageThread(MessageReceivedEvent event) {
            super();
            this.event = event;
        }
        
        public void run() {
            synchronized (lock) {
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
                        System.out.println("Incorrect channel loading: " + event.getChannelType());
                        return;
                }
                model.loadComponent(componentStack);
            }
        }
    }

    class LoadComponentReactionThread extends Thread {
        private MessageReactionAddEvent event;
        public LoadComponentReactionThread(MessageReactionAddEvent event) {
            super();
            this.event = event;
        }
        
        public void run() {
            synchronized (lock) {
                if (model.contains(Long.valueOf(event.getChannel().getId()))) return;
                if (event.getUser().isBot()) return;
                ArrayList<AbstractJDAAdapter> componentStack = new ArrayList<AbstractJDAAdapter>();
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {    
                    switch(event.getChannelType()) {
                        case TEXT:
                            componentStack.add(new GuildJDAAdapter(event.getGuild().getName(), event.getGuild().getIdLong()));
                            if (message.getCategory() == null) {
                                componentStack.add(new ChannelJDAAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), event.getGuild().getIdLong()));
                            } else {
                                componentStack.add(new CategoryJDAAdapter(message.getCategory().getName(), message.getCategory().getIdLong(), event.getGuild().getIdLong()));
                                componentStack.add(new ChannelJDAAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), message.getCategory().getIdLong()));
                            } 
                            break;
                        case GUILD_PUBLIC_THREAD:
                            componentStack.add(new GuildJDAAdapter(event.getGuild().getName(), event.getGuild().getIdLong()));
                            if (message.getCategory() == null) {
                                componentStack.add(new ChannelJDAAdapter(((ThreadChannel) event.getChannel()).getParentChannel().getName(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong(), event.getGuild().getIdLong()));
                            } else {
                                componentStack.add(new CategoryJDAAdapter(message.getCategory().getName(), message.getCategory().getIdLong(), event.getGuild().getIdLong()));
                                componentStack.add(new ChannelJDAAdapter(((ThreadChannel) event.getChannel()).getParentChannel().getName(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong(), message.getCategory().getIdLong()));
                            }
                            componentStack.add(new ThreadJDAAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong()));
                            break;
                        default:
                            System.out.println("Incorrect channel loading: " + event.getChannelType());
                            return;
                    }
                    model.loadComponent(componentStack);
                });
            }
        }
    }
}
