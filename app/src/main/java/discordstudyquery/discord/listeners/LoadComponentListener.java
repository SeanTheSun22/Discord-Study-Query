package discordstudyquery.discord.listeners;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import discordstudyquery.adapter.CategoryAdapter;
import discordstudyquery.adapter.ChannelAdapter;
import discordstudyquery.adapter.ComponentAdapter;
import discordstudyquery.adapter.GuildAdapter;
import discordstudyquery.adapter.ThreadAdapter;
import discordstudyquery.guild.DiscordModel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LoadComponentListener extends ListenerAdapter {
    DiscordModel model;
    public LoadComponentListener(DiscordModel model) {
        super();
        this.model = model;
    }
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        System.out.println(model.toString());
        // Check if the relevant command is being used
        if (event.getAuthor().isBot()) return;
        if (model.contains(Long.valueOf(event.getChannel().getId()))) return;
        
        ArrayList<ComponentAdapter> componentStack = new ArrayList<ComponentAdapter>();
        switch(event.getChannelType()) {
            case TEXT:
                componentStack.add(new GuildAdapter(event.getGuild().getName(), event.getGuild().getIdLong()));
                if (event.getMessage().getCategory() == null) {
                    componentStack.add(new ChannelAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), event.getGuild().getIdLong()));
                } else {
                    componentStack.add(new CategoryAdapter(event.getMessage().getCategory().getName(), event.getMessage().getCategory().getIdLong(), event.getGuild().getIdLong()));
                    componentStack.add(new ChannelAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), event.getMessage().getCategory().getIdLong()));
                } 
                break;
            case GUILD_PUBLIC_THREAD:
                System.out.println(((ThreadChannel)event.getChannel()).getParentChannel());
                System.out.println(event.getGuildChannel());
                componentStack.add(new GuildAdapter(event.getGuild().getName(), event.getGuild().getIdLong()));
                if (event.getMessage().getCategory() == null) {
                    componentStack.add(new ChannelAdapter(((ThreadChannel) event.getChannel()).getParentChannel().getName(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong(), event.getGuild().getIdLong()));
                } else {
                    componentStack.add(new CategoryAdapter(event.getMessage().getCategory().getName(), event.getMessage().getCategory().getIdLong(), event.getGuild().getIdLong()));
                    componentStack.add(new ChannelAdapter(((ThreadChannel) event.getChannel()).getParentChannel().getName(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong(), event.getMessage().getCategory().getIdLong()));
                }
                componentStack.add(new ThreadAdapter(event.getChannel().getName(), event.getChannel().getIdLong(), ((ThreadChannel) event.getChannel()).getParentChannel().getIdLong()));
                break;
            default:
                return;
        }
        model.loadComponent(componentStack);
    }
}
