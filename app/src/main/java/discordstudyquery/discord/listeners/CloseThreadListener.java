package discordstudyquery.discord.listeners;

import javax.annotation.Nonnull;

import discordstudyquery.guild.DiscordModel;
import discordstudyquery.guild.structure.Channel;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import discordstudyquery.guild.structure.Thread;

public class CloseThreadListener extends ListenerAdapter {
    private DiscordModel model;
    public CloseThreadListener(DiscordModel model) {
        super();
        this.model = model;
    }
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        new CloseThreadThread(event).start();
    }
    class CloseThreadThread extends java.lang.Thread {
        private MessageReceivedEvent event;
        public CloseThreadThread(MessageReceivedEvent event) {
            super();
            this.event = event;
        }
        public void run() {
            if (event.getAuthor().isBot()) return;
            if (!(event.getChannel() instanceof ThreadChannel)) return;
            if (!event.getMessage().getContentRaw().toLowerCase().equals("query close")) return;
            while (!model.contains(event.getChannel().getIdLong())) try {java.lang.Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            Member member = event.getMember();
            if (member == null) return;
            boolean hasPermission = false;
            if ((((Thread) model.getComponentWithID(event.getChannel().getIdLong())).getCreatorUserID() == event.getAuthor().getIdLong()) ||
               ((((Thread) model.getComponentWithID(event.getChannel().getIdLong())).getReopenUserID() == event.getAuthor().getIdLong())) ||
                member.getPermissions().contains(Permission.MANAGE_CHANNEL)) hasPermission = true;
            for (Role role : member.getRoles()) { 
                if (role.getIdLong() == ((Channel) model.getComponentWithID(((ThreadChannel) event.getChannel()).getParentChannel().getIdLong())).getModeratorRoleID()) { 
                    hasPermission = true;
                    break;
                }
            }
            if (!hasPermission) return;
            event.getChannel().sendMessage("Thread closed. Anyone may reopen the thread by reacting with :sob:.").queue((message) -> {
                message.addReaction(Emoji.fromUnicode("U+1F62D")).queue();
            });
            ((ThreadChannel) event.getChannel()).getManager().setLocked(true).queue();
        }
    }
}
