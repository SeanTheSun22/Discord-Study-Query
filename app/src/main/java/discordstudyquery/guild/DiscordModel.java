package discordstudyquery.guild;

import discordstudyquery.guild.structure.Shard;
import discordstudyquery.adapter.ComponentAdapter;
import discordstudyquery.guild.structure.AbstractDiscordContainer;

import java.util.ArrayList;

public class DiscordModel {
    public static Long SHARD_COUNT = 0L;
    Shard shard;
    public DiscordModel() {
        shard = new Shard("Shard", SHARD_COUNT);
        SHARD_COUNT++;
    }

    public void loadComponent(ArrayList<ComponentAdapter> componentStack) {
        AbstractDiscordContainer current = shard;
        for (ComponentAdapter componentAdapter : componentStack) {
            if (!shard.contains(componentAdapter.getID())) {
                current.loadChild(componentAdapter);
            }
            current = current.getChildWithID(componentAdapter.getID());
        }
    }

    public boolean contains(Long id) {
        return shard.contains(id);
    }

    public String toString() {
        return shard.toString();
    }
}
