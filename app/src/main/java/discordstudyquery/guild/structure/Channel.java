package discordstudyquery.guild.structure;

import discordstudyquery.adapter.ComponentAdapter;

public class Channel extends AbstractDiscordContainer {
    public Channel(String name, Long id, Category parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        Thread child = new Thread(adapter.getName(), adapter.getID(), this);
        registerChild(child);
    }
}
