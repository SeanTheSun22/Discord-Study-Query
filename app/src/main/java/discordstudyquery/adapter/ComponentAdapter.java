package discordstudyquery.adapter;

public class ComponentAdapter {
    String name;
    Long id;
    Long parentID;
    public ComponentAdapter(String name, Long id, Long parentID) {
        this.name = name;
        this.id = id;
        this.parentID = parentID;
    }

    public String getName() {return name;}
    public Long getID() {return id;}
    public Long getParentID() {return parentID;}
}