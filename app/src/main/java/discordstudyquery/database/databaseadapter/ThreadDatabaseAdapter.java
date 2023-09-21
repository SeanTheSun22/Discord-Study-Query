package discordstudyquery.database.databaseadapter;

public class ThreadDatabaseAdapter extends AbstractDatabaseAdapter {
    private Long originalCreatorId;
    public ThreadDatabaseAdapter(Long id, Long originalCreatorId) {
        super(id);
        this.originalCreatorId = originalCreatorId;
    }
    public Long getOriginalCreatorId() {return originalCreatorId;}
}
