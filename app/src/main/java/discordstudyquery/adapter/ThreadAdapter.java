package discordstudyquery.adapter;

public class ThreadAdapter extends ComponentAdapter {
    private Long parentID;
    public ThreadAdapter(String name, Long id, Long parentID) {
        super(name, id);
        this.parentID = parentID;
    }

    public Long getParentID() {return parentID;}
}
