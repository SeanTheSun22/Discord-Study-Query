package discordstudyquery.guild.structure;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import discordstudyquery.discord.jdaadapter.AbstractJDAAdapter;

public abstract class AbstractDiscordContainer {
    /*
     * AbstractDiscordContainer is the base class for all Discord objects. It 
     * is used to create a tree structure of Shards, Guilds, Categories, 
     * Channels, and Threads.
     */

    protected String name;
    protected Long id;
    private Long lastUpdatedTime;
    protected AbstractDiscordContainer parent;
    private ArrayList<AbstractDiscordContainer> children = new ArrayList<AbstractDiscordContainer>();

    protected AbstractDiscordContainer(String name, Long id, AbstractDiscordContainer parent) {
        this.name = name;
        this.id = id;
        this.parent = parent;
        updated();
        if (parent != null) {parent.addChild(this);}
    }

    private void updated() {
        lastUpdatedTime = System.currentTimeMillis();
        if (this.getParent() != null) {this.getParent().updated();}
    }

    public void cleanUp() {
        for (AbstractDiscordContainer child : this.children) {
            child.cleanUp();
        }
        if (System.currentTimeMillis() - lastUpdatedTime > 1200 * 1000) {
            unloadAll();
        }
    }

    public Long getId() {return this.id;}

    public void registerToParent(AbstractDiscordContainer parent) {
        unregister();
        this.setParent(parent);
        parent.addChild(this);
    }
    public void unregister() {
        if (this.getParent() == null) {return;}
        this.getParent().removeChild(this);
        this.setParent(null);
    }
    public void registerChild(AbstractDiscordContainer child) {
        child.unregister();
        this.addChild(child);
        child.setParent(this);
    }
    public void unregisterChild(AbstractDiscordContainer child) {
        this.removeChild(child);
        child.setParent(null);
    }
    public void unregisterChild(Long id) {
        AbstractDiscordContainer child = this.getChildWithID(id);
        this.removeChild(child);
        child.setParent(null);
    }

    public void setParent(AbstractDiscordContainer parent) {this.parent = parent;}
    public AbstractDiscordContainer getParent() {return this.parent;}

    private void addChild(AbstractDiscordContainer child) {this.children.add(child);}
    private void removeChild(AbstractDiscordContainer child) {this.children.remove(child);}
    public ArrayList<AbstractDiscordContainer> getChildren() {return this.children;}
    public AbstractDiscordContainer getChildWithID(Long id) {
        for (AbstractDiscordContainer child : this.children) {
            if (child.equals(id)) {
                return child;
            }
            if (child.contains(id)) {
                return child.getChildWithID(id);
            }
        }
        throw new NoSuchElementException("No child with ID " + id + " found.");
    }

    public abstract void loadChild(AbstractJDAAdapter adapter);
    public abstract void unload();
    public void unloadAll() {
        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).unloadAll();
            children.get(i).unload();
        }
    }

    private boolean equals(Long id) {
        return this.id.equals(id);
    }

    public boolean contains(Long id) {
        if (this.equals(id)) {
            updated();
            return true;
        }
        for (AbstractDiscordContainer child : this.children) {
            if (child == null) {System.out.println(children.toString()); break;}
            if (child.contains(id)) {return true;}
        }
        return false;
    }

    public String toString() {
        String untabbedString = name ;
        for (AbstractDiscordContainer child : this.children) {
            untabbedString += "\n" + child.toString();
        }
        String tabbedString = untabbedString.replaceAll("\n", "\n\t");
        return tabbedString;
    }
}