package discordstudyquery.guild.structure;

import discordstudyquery.adapter.ComponentAdapter;
import discordstudyquery.adapter.ThreadAdapter;
import discordstudyquery.database.DatabaseEditor;

public class Channel extends AbstractDiscordContainer {
    public Channel(String name, Long id, Category parent) {
        super(name, id, parent);
    }
    public Channel(String name, Long id, Guild parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        ThreadAdapter threadAdapter = DatabaseEditor.getThreadFromSQL((ThreadAdapter) adapter);
        Thread child = new Thread(threadAdapter.getName(), threadAdapter.getID(), this);
        registerChild(child);
    }
}
