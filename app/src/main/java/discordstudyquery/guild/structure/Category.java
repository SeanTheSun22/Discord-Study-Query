package discordstudyquery.guild.structure;

import discordstudyquery.adapter.ComponentAdapter;

public class Category extends AbstractDiscordContainer {
    public Category(String name, Long id, Guild parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        Channel child = new Channel(adapter.getName(), adapter.getID(), this);
        registerChild(child);
    }
}
