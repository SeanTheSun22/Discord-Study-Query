package discordstudyquery.guild.structure;

import discordstudyquery.database.DatabaseEditor;
import discordstudyquery.adapter.CategoryAdapter;
import discordstudyquery.adapter.ChannelAdapter;
import discordstudyquery.adapter.ComponentAdapter;

public class Guild extends AbstractDiscordContainer {
    public Guild(String name, Long id, Shard parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        if (adapter instanceof CategoryAdapter) {
            CategoryAdapter categoryAdapter = DatabaseEditor.getCategoryFromSQL((CategoryAdapter) adapter);
            Category child = new Category(categoryAdapter.getName(), categoryAdapter.getID(), this);
            registerChild(child);
        } else if (adapter instanceof ChannelAdapter) {
            ChannelAdapter channelAdapter = DatabaseEditor.getChannelFromSQL((ChannelAdapter) adapter);
            Channel child = new Channel(channelAdapter.getName(), channelAdapter.getID(), this);
            registerChild(child);
        }
    }
}
