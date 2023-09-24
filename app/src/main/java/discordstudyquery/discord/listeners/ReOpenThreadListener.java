package discordstudyquery.discord.listeners;

import javax.annotation.Nonnull;

import discordstudyquery.guild.DiscordModel;
import discordstudyquery.guild.structure.Thread;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReOpenThreadListener extends ListenerAdapter {
    private DiscordModel model;
    public ReOpenThreadListener(DiscordModel model) {
        super();
        this.model = model;
    }
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        new ReOpenThreadThread(event).start();
    }
    class ReOpenThreadThread extends java.lang.Thread {
        MessageReactionAddEvent event;
        public ReOpenThreadThread(MessageReactionAddEvent event) {
            super();
            this.event = event;
        }
        @SuppressWarnings("unlikely-arg-type")
        public void run() {
            User user = event.getUser();
            if (user == null || user.isBot()) return;
            if (!(event.getChannel() instanceof ThreadChannel)) return;
            if (!(((ThreadChannel) event.getChannel()).isLocked())) return;
            if (!event.getEmoji().equals(Emoji.fromUnicode("U+1F62D"))) return;
            event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                if (!message.getAuthor().equals(event.getJDA().getSelfUser())) return;
                while (!model.contains(event.getChannel().getIdLong())) try {java.lang.Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
                event.getChannel().sendMessage("Thread reopened by <@" + event.getUserId() + ">. Use 'query close' to close the thread again.").queue();
                ((Thread) model.getComponentWithID(event.getChannel().getIdLong())).setReopenUserID(user.getIdLong());
                ((ThreadChannel) event.getChannel()).getManager().setLocked(false).queue();
            });
        }
    }
}
