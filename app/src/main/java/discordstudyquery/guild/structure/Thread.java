package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;

public class Thread extends AbstractDiscordContainer {
    private Long creatorUserID = -1L;
    private Long reopenUserID = -1L;
    public Thread(String name, Long id, Channel parent) {
        super(name, id, parent);
    }

    public Thread (String name, Long id, Channel parent, Long creatorUserID, Long reopenUserID) {
        super(name, id, parent);
        this.creatorUserID = creatorUserID;
        this.reopenUserID = reopenUserID;
    }

    public Long getCreatorUserID() {return creatorUserID;}
    public void setReopenUserID(Long reopenUserID) {this.reopenUserID = reopenUserID;}
    public Long getReopenUserID() {return reopenUserID;}

    public void loadChild(AbstractJDAAdapter adapter) {
        throw new UnsupportedOperationException("Cannot load child of thread");
    }

    public void unload() {
        DatabaseEditor.updateThreadInSQL(this);
        unregister();
    }
}
