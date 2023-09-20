package discordstudyquery.guild.structure;

import discordstudyquery.adapter.ComponentAdapter;
import discordstudyquery.adapter.GuildAdapter;
import discordstudyquery.database.DatabaseEditor;

public class Shard extends AbstractDiscordContainer {
    public Shard(String name, Long id) {
        super(name, id, null);
    }

    public void loadChild(ComponentAdapter adapter) {
        GuildAdapter guildAdapter = DatabaseEditor.getGuildFromSQL((GuildAdapter) adapter);
        Guild child = new Guild(guildAdapter.getName(), guildAdapter.getID(), this);
        registerChild(child);
    }
}
