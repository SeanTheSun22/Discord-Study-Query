package discordstudyquery.adapter;

public class CategoryAdapter extends ComponentAdapter {
    private Long parentID;
    public CategoryAdapter(String name, Long id, Long parentID) {
        super(name, id);
        this.parentID = parentID;
    }

    public Long getParentID() {return parentID;}
}
