package discordstudyquery.jdaadapter;

public class AbstractJDAAdapter {
    String name;
    Long id;
    public AbstractJDAAdapter(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {return name;}
    public Long getID() {return id;}
}