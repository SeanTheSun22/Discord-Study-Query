package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.database.databaseadapter.ThreadDatabaseAdapter;
import discordstudyquery.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.jdaadapter.ThreadJDAAdapter;

public class Channel extends AbstractDiscordContainer {
    public Channel(String name, Long id, Category parent) {
        super(name, id, parent);
    }
    public Channel(String name, Long id, Guild parent) {
        super(name, id, parent);
    }

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
