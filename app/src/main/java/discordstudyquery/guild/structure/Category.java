package discordstudyquery.guild.structure;

public class Category extends AbstractDiscordContainer {
    public Category(String name, Long id, Guild parent) {
        super(name, id, parent);
    }

    public void loadChild(Long id) {
        
    }
}
