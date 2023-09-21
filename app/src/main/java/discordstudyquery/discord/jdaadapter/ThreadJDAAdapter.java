package discordstudyquery.discord.jdaadapter;

public class ThreadJDAAdapter extends AbstractJDAAdapter {
    private Long parentID;
    private Long creatorUserId;
    public ThreadJDAAdapter(String name, Long id, Long parentID) {
        this(name, id, parentID, -1L);
    }
    public ThreadJDAAdapter(String name, Long id, Long parentID, Long creatorUserId) {
        super(name, id);
        this.parentID = parentID;
        this.creatorUserId = creatorUserId;
    }

    public Long getCreatorUserId() {return creatorUserId;}

    public Long getParentID() {return parentID;}
}
