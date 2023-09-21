package discordstudyquery.discord.listeners;

import java.util.List;

import javax.annotation.Nonnull;

import discordstudyquery.guild.DiscordModel;
import discordstudyquery.guild.structure.Channel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingModRoleListener extends ListenerAdapter {
    private DiscordModel model;
    public PingModRoleListener(DiscordModel model) {
        super();
        this.model = model;
    }
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        new PingModRoleThread(event).start();
    }

    class PingModRoleThread extends Thread {
        private MessageReceivedEvent event;
        public PingModRoleThread(MessageReceivedEvent event) {
            super();
            this.event = event;
        }
        public void run() {
            if (event.getAuthor().isBot()) return;
            if (!(event.getChannel() instanceof StandardGuildMessageChannel)) return;
            Message message;
            if (!((message = event.getMessage()).getContentRaw().toLowerCase().startsWith("query setping ") || 
                                         message.getContentRaw().toLowerCase().startsWith("query setmod ") ||
                                         message.getContentRaw().toLowerCase().startsWith("query removeping") ||
                                         message.getContentRaw().toLowerCase().startsWith("query removemod"))) return;
            while (!model.contains(event.getChannel().getIdLong())) try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}

            if (message.getContentRaw().toLowerCase().startsWith("query removeping")) {
                ((Channel) model.getComponentWithID(event.getChannel().getIdLong())).setPingRoleID(-1L);
                event.getChannel().sendMessage("ping role removed").queue();
                return;
            } else if (message.getContentRaw().toLowerCase().startsWith("query removemod")) {
                ((Channel) model.getComponentWithID(event.getChannel().getIdLong())).setModeratorRoleID(-1L);
                event.getChannel().sendMessage("mod role removed").queue();
                return;
            }

            List<Role> roles;
            if ((roles = message.getMentions().getRoles()).size() != 1) {
                event.getChannel().sendMessage("bruh you need to mention one role").queue(); 
                return;
            }
            System.out.println(roles.toString());
            if (message.getContentRaw().toLowerCase().startsWith("query setping ")) {
                ((Channel) model.getComponentWithID(event.getChannel().getIdLong())).setPingRoleID(roles.get(0).getIdLong());
                event.getChannel().sendMessage("ping role set to <@&" + roles.get(0).getIdLong() + ">").queue();
            } else if (message.getContentRaw().toLowerCase().startsWith("query setmod ")) {
                ((Channel) model.getComponentWithID(event.getChannel().getIdLong())).setModeratorRoleID(roles.get(0).getIdLong());
                event.getChannel().sendMessage("mod role set to <@&" + roles.get(0).getIdLong() + ">").queue();
            }
        }
    }
}
