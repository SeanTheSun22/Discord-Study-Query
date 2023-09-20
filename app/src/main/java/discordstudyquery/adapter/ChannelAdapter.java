package discordstudyquery.adapter;

public class ChannelAdapter extends ComponentAdapter {
    private Long parentID;
    public ChannelAdapter(String name, Long id, Long parentID) {
        super(name, id);
        this.parentID = parentID;
    }

    public Long getParentID() {return parentID;}
}
