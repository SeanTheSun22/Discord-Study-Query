package discordstudyquery.discord.jdaadapter;

public class ChannelJDAAdapter extends AbstractJDAAdapter {
    private Long parentID;
    public ChannelJDAAdapter(String name, Long id, Long parentID) {
        super(name, id);
        this.parentID = parentID;
    }

    public Long getParentID() {return parentID;}
}
