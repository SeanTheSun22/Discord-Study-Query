package discordstudyquery.database.databaseadapter;

public abstract class AbstractDatabaseAdapter {
    private Long id;
    protected AbstractDatabaseAdapter(Long id) {
        this.id = id;
    }
    public Long getID() {return id;}
}
