package discordstudyquery.database.databaseadapter;

public class ThreadDatabaseAdapter extends AbstractDatabaseAdapter {
    private Long originalCreatorID;
    private Long reopenUserID;
    public ThreadDatabaseAdapter(Long id, Long originalCreatorID, Long reopenUserID) {
        super(id);
        this.originalCreatorID = originalCreatorID;
        this.reopenUserID = reopenUserID;
    }
    public Long getOriginalCreatorID() {return originalCreatorID;}
    public Long getReopenUserID() {return reopenUserID;}
}
