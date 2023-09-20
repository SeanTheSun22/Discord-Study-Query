package discordstudyquery.guild.structure;

import discordstudyquery.adapter.ChannelAdapter;
import discordstudyquery.adapter.ComponentAdapter;
import discordstudyquery.database.DatabaseEditor;

public class Category extends AbstractDiscordContainer {
    public Category(String name, Long id, Guild parent) {
        super(name, id, parent);
    }

    public void loadChild(ComponentAdapter adapter) {
        ChannelAdapter channelAdapter = DatabaseEditor.getChannelFromSQL((ChannelAdapter) adapter);
        Channel child = new Channel(channelAdapter.getName(), channelAdapter.getID(), this);
        registerChild(child);
    }
}
