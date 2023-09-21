package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.database.databaseadapter.ChannelDatabaseAdapter;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.discord.jdaadapter.ChannelJDAAdapter;

public class Category extends AbstractDiscordContainer {
    public Category(String name, Long id, Guild parent) {
        super(name, id, parent);
    }

    public void loadChild(AbstractJDAAdapter discordAdapter) {
        if (!(discordAdapter instanceof ChannelJDAAdapter)) {throw new UnsupportedOperationException("Category child must be a Channel");}
        ChannelDatabaseAdapter channelAdapter = DatabaseEditor.getChannelFromSQL(discordAdapter.getID());
        Channel child = new Channel(discordAdapter.getName(), channelAdapter.getID(), this);
        registerChild(child);
    }

    public void unload() {
        DatabaseEditor.updateCategoryInSQL(this);
        unregister();
    }
}
