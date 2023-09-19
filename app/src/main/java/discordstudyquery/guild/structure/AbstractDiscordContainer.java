package discordstudyquery.guild.structure;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class AbstractDiscordContainer {
    /*
     * AbstractDiscordContainer is the base class for all Discord objects. It 
     * is used to create a tree structure of Shards, Guilds, Categories, 
     * Channels, and Threads.
     */

    private String name;
    private String id;
    private AbstractDiscordContainer parent;
    private ArrayList<AbstractDiscordContainer> children = new ArrayList<AbstractDiscordContainer>();

    protected AbstractDiscordContainer(String name, String id, AbstractDiscordContainer parent) {
        this.name = name;
        this.id = id;
        this.parent = parent;
        if (parent != null) {parent.addChild(this);}
    }

    private void setName(String name) {this.name = name;}
    private String getName() {return this.name;}
    private String getId() {return this.id;}

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
    public void unregisterChild(String id) {
        AbstractDiscordContainer child = this.getChildWithID(id);
        this.removeChild(child);
        child.setParent(null);
    }

    public void setParent(AbstractDiscordContainer parent) {this.parent = parent;}
    public AbstractDiscordContainer getParent() {return this.parent;}

    private void addChild(AbstractDiscordContainer child) {this.children.add(child);}
    private void removeChild(AbstractDiscordContainer child) {this.children.remove(child);}
    public ArrayList<AbstractDiscordContainer> getChildren() {return this.children;}
    public AbstractDiscordContainer getChildWithID(String id) {
        for (AbstractDiscordContainer child : this.children) {
            if (child.equals(id)) {
                return child;
            }
        }
        throw new NoSuchElementException("No child with ID " + id + " found.");
    }

    private boolean equals(String id) {
        return this.id.equals(id);
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