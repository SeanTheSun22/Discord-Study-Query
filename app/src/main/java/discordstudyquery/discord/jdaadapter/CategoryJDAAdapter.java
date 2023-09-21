package discordstudyquery.discord.jdaadapter;

public class CategoryJDAAdapter extends AbstractJDAAdapter {
    private Long parentID;
    public CategoryJDAAdapter(String name, Long id, Long parentID) {
        super(name, id);
        this.parentID = parentID;
    }

    public Long getParentID() {return parentID;}
}
