package discordstudyquery.guild;

import discordstudyquery.guild.structure.Shard;
import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;
import discordstudyquery.guild.structure.AbstractDiscordContainer;

import java.util.ArrayList;

public class DiscordModel {
    private static Long SHARD_COUNT = 0L;
    private Shard shard;
    public DiscordModel() {
        shard = new Shard("Shard", SHARD_COUNT);
        SHARD_COUNT++;
    }

    public Shard getShard() {
        return shard;
    }

    public void loadComponent(ArrayList<AbstractJDAAdapter> componentStack) {
        AbstractDiscordContainer current = shard;
        for (AbstractJDAAdapter componentAdapter : componentStack) {
            if (!shard.contains(componentAdapter.getID())) {
                current.loadChild(componentAdapter);
            }
            current = current.getChildWithID(componentAdapter.getID());
        }
    }
    public AbstractDiscordContainer getComponentWithID(Long id) {
        return shard.getChildWithID(id);
    }
    public void unloadAll() {
        System.out.println(shard);
        shard.unloadAll();
        System.out.println(shard);
    }

    public void cleanUp() {
        System.out.println("Cleaning");
        System.out.println(shard);
        shard.cleanUp();
        System.out.println(shard);
    }

    public boolean contains(Long id) {
        return shard.contains(id);
    }

    public String toString() {
        return shard.toString();
    }
}