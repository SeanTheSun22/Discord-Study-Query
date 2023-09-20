package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.adapter.CategoryAdapter;
import discordstudyquery.adapter.ComponentAdapter;

public class Guild extends AbstractDiscordContainer {
    public Guild(String name, Long id, Shard parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        CategoryAdapter categoryAdapter = DatabaseEditor.getCategoryFromSQL((CategoryAdapter) adapter);
        Category child = new Category(categoryAdapter.getName(), categoryAdapter.getID(), this);
        registerChild(child);
    }
}
