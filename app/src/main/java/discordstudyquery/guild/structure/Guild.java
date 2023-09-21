package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.database.databaseadapter.CategoryDatabaseAdapter;
import discordstudyquery.database.databaseadapter.ChannelDatabaseAdapter;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.discord.jdaadapter.CategoryJDAAdapter;
import discordstudyquery.discord.jdaadapter.ChannelJDAAdapter;

public class Guild extends AbstractDiscordContainer {
    public Guild(String name, Long id, Shard parent) {
        super(name, id, parent);
    }

    public void loadChild(AbstractJDAAdapter discordAdapter) {
        if (discordAdapter instanceof CategoryJDAAdapter) {
            CategoryDatabaseAdapter categoryAdapter = DatabaseEditor.getCategoryFromSQL(discordAdapter.getID());
            Category child = new Category(discordAdapter.getName(), categoryAdapter.getID(), this);
            registerChild(child);
        } else if (discordAdapter instanceof ChannelJDAAdapter) {
            ChannelDatabaseAdapter channelAdapter = DatabaseEditor.getChannelFromSQL(discordAdapter.getID());
            Channel child = new Channel(discordAdapter.getName(), channelAdapter.getID(), this, channelAdapter.getPingRoleID(), channelAdapter.getModeratorRoleID());
            registerChild(child);
        } else {
            throw new UnsupportedOperationException("Guild child must be a Category or Channel");
        }
    }

    public void unload() {
        DatabaseEditor.updateGuildInSQL(this);
        unregister();
    }
}
