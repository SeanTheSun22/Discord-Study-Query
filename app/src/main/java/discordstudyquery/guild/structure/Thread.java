package discordstudyquery.guild.structure;

import discordstudyquery.adapter.ComponentAdapter;

public class Thread extends AbstractDiscordContainer {
    public Thread(String name, Long id, Channel parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        throw new UnsupportedOperationException("Cannot load child of thread");
    }
}
