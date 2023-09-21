package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.database.databaseadapter.GuildDatabaseAdapter;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.discord.jdaadapter.GuildJDAAdapter;

public class Shard extends AbstractDiscordContainer {
    public Shard(String name, Long id) {
        super(name, id, null);
    }

    public void loadChild(AbstractJDAAdapter discordAdapter) {
        if (!(discordAdapter instanceof GuildJDAAdapter)) {throw new UnsupportedOperationException("Shard child must be a Guild");}
        GuildDatabaseAdapter databaseAdapter = DatabaseEditor.getGuildFromSQL(discordAdapter.getID());
        Guild child = new Guild(discordAdapter.getName(), databaseAdapter.getID(), this);
        registerChild(child);
    }

    public void unload() {
        System.out.println("Unloading shard");
    }
}
