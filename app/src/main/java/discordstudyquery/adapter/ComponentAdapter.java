package discordstudyquery.adapter;

public class ComponentAdapter {
    String name;
    Long id;
    public ComponentAdapter(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {return name;}
    public Long getID() {return id;}
}