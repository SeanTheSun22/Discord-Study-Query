package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.database.databaseadapter.ThreadDatabaseAdapter;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.discord.jdaadapter.ThreadJDAAdapter;

public class Channel extends AbstractDiscordContainer {
    public Channel(String name, Long id, Category parent) {super(name, id, parent);}
    public Channel(String name, Long id, Category parent, Long pingRoleID, Long moderatorRoleID) {
        super(name, id, parent);
        this.pingRoleID = pingRoleID;
    }
    public Channel(String name, Long id, Guild parent) {super(name, id, parent);}
    public Channel(String name, Long id, Guild parent, Long pingRoleID, Long moderatorRoleID) {
        super(name, id, parent);
        this.pingRoleID = pingRoleID;
    }

    private Long pingRoleID = -1L;
    private Long moderatorRoleID = -1L;
    public Long getPingRoleID() {return pingRoleID;}
    public Long getModeratorRoleID() {return moderatorRoleID;}
    public void setPingRoleID(Long pingRoleID) {this.pingRoleID = pingRoleID;}
    public void setModeratorRoleID(Long moderatorRoleID) {this.moderatorRoleID = moderatorRoleID;}

    public void loadChild(AbstractJDAAdapter adapter) {
        if (!(adapter instanceof ThreadJDAAdapter)) {throw new UnsupportedOperationException("Channel child must be a Thread");}
        ThreadDatabaseAdapter threadAdapter = DatabaseEditor.getThreadFromSQL(adapter.getID());
        Thread child = new Thread(adapter.getName(), threadAdapter.getID(), this, threadAdapter.getOriginalCreatorId());
        registerChild(child);
    }

    public void unload() {
        DatabaseEditor.updateChannelInSQL(this);
        unregister();
    }
}
