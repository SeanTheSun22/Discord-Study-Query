package discordstudyquery.guild.structure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AbstractDiscordContainerTest {
    private Shard shard;
    private Guild guild;
    private Category category;
    private Channel channel;
    private Thread thread;

    @BeforeEach
    public void createStructure() {
        shard = new Shard("shard", 1L);
        guild = new Guild("guild", 2L, shard);
        category = new Category("category", 3L, guild);
        channel = new Channel("channel", 4L, category);
        thread = new Thread("thread", 5L, channel);
    }

    @Test
    public void testConstructionStructure() {
        /*
         * Check whether the constructors set the parent and children correctly.
         */
        assertTrue(shard.getChildren().contains(guild));
        assertTrue(guild.getChildren().contains(category));
        assertTrue(category.getChildren().contains(channel));
        assertTrue(channel.getChildren().contains(thread));
        
        assertTrue(guild.getParent().equals(shard));
        assertTrue(category.getParent().equals(guild));
        assertTrue(channel.getParent().equals(category));
        assertTrue(thread.getParent().equals(channel));
    }

    @Test
    public void testRegisterToParent() {
        /*
         * Check whether the registerToParent method sets the parent and children correctly.
         */
        Guild guild2 = new Guild("guild2", 6L, null);
        guild2.registerToParent(shard); 
        assertTrue(shard.getChildren().contains(guild2));
        assertTrue(guild2.getParent().equals(shard));

        Shard shard2 = new Shard("shard2", 7L);
        guild.registerToParent(shard2);
        assertTrue(shard2.getChildren().contains(guild));
        assertTrue(guild.getParent().equals(shard2));
        assertFalse(shard.getChildren().contains(guild));
    }

    @Test
    public void testUnregister() {
        /*
         * Check whether the unregister method sets the parent and children correctly.
         */
        guild.unregister();
        assertFalse(shard.getChildren().contains(guild));
        assertTrue(guild.getParent() == null);
    }

    @Test
    public void testRegisterChild() {
        /*
         * Check whether the registerChild method sets the parent and children correctly.
         */
        Category category2 = new Category("category2", 6L, null);
        guild.registerChild(category2);
        assertTrue(guild.getChildren().contains(category2));
        assertTrue(category2.getParent().equals(guild));

        Guild guild2 = new Guild("guild2", 7L, null);
        guild2.registerChild(category);
        assertTrue(guild2.getChildren().contains(category));
        assertTrue(category.getParent().equals(guild2));
        assertFalse(guild.getChildren().contains(category));
    }

    @Test 
    public void testUnregisterChild() {
        /*
         * Check whether the unregisterChild method sets the parent and children correctly.
         */
        guild.unregisterChild(category);
        assertFalse(guild.getChildren().contains(category));
        assertTrue(category.getParent() == null);
    }

    @Test
    public void testToString() {
        /*
         * Check whether the toString method returns the correct string.
         */
        assertTrue(shard.toString().equals(
            "shard\n" +
            "\tguild\n" +
            "\t\tcategory\n" +
            "\t\t\tchannel\n" +
            "\t\t\t\tthread"));

        Guild guild2 = new Guild("guild2", 6L, null);
        guild2.registerToParent(shard);

        assertTrue(shard.toString().equals(
            "shard\n" +
            "\tguild\n" +
            "\t\tcategory\n" +
            "\t\t\tchannel\n" +
            "\t\t\t\tthread\n" +
            "\tguild2"));
    }
}
