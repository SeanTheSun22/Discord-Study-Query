package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;

public class Thread extends AbstractDiscordContainer {
    private Long creatorUserId;
    public Thread(String name, Long id, Channel parent) {
        super(name, id, parent);
    }

    public Thread (String name, Long id, Channel parent, Long creatorUserId) {
        super(name, id, parent);
        this.creatorUserId = creatorUserId;
    }

    public Long getCreatorUserId() {return creatorUserId;}

    public void loadChild(AbstractJDAAdapter adapter) {
        throw new UnsupportedOperationException("Cannot load child of thread");
    }

    public void unload() {
        DatabaseEditor.updateThreadInSQL(this);
        unregister();
    }
}
