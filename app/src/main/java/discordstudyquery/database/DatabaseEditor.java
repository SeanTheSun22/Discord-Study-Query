package discordstudyquery.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nonnull;

import discordstudyquery.database.databaseadapter.CategoryDatabaseAdapter;
import discordstudyquery.database.databaseadapter.ChannelDatabaseAdapter;
import discordstudyquery.database.databaseadapter.GuildDatabaseAdapter;
import discordstudyquery.database.databaseadapter.ThreadDatabaseAdapter;

import discordstudyquery.guild.structure.Guild;
import discordstudyquery.guild.structure.Category;
import discordstudyquery.guild.structure.Channel;
import discordstudyquery.guild.structure.Thread;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DatabaseEditor {
    private static JDA jda;
    public static void setJDA(JDA jda) {
        DatabaseEditor.jda = jda;
    }
    private static void sendErrorMessage(@Nonnull String message) {
        TextChannel channel = jda.getTextChannelById(1156156207527100416L);
        System.out.println(channel);
        if (channel != null) channel.sendMessage(message).queue();
    }
    public static GuildDatabaseAdapter getGuildFromSQL(Long id, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetGuildWithID.sql", "UserDatabase.json", new String[] {id.toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            // use rs.getString("field") to get the data from the result set
            GuildDatabaseAdapter guild = new GuildDatabaseAdapter(id);
            ((Connection) connectionData[0]).close();
            return guild;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                GuildDatabaseAdapter guild = new GuildDatabaseAdapter(id);
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertGuild.sql", "UserDatabase.json", new String[] {id.toString()});
                    return guild;
                } catch (SQLException e1) {
                    System.out.println("Guild not inserted to database but sucessfully loaded");
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return guild;
                }
            }
            if (retryCount[0] > 5) {
                System.out.println("Error getting guild " + id + " from database, retried 5 times, giving up");
                sendErrorMessage("Error getting guild " + id + " from database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return new GuildDatabaseAdapter(id);
            }
            System.out.println("Error getting guild " + id + " from database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            return getGuildFromSQL(id, retryCount[0] + 1);
        }
    }

    public static CategoryDatabaseAdapter getCategoryFromSQL(Long id, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetCategoryWithID.sql", "UserDatabase.json", new String[] {id.toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            // use rs.getString("field") to get the data from the result set
            CategoryDatabaseAdapter category = new CategoryDatabaseAdapter(id);
            ((Connection) connectionData[0]).close();
            return category;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                CategoryDatabaseAdapter category = new CategoryDatabaseAdapter(id);
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertCategory.sql", "UserDatabase.json", new String[] {id.toString()});
                    return category;
                } catch (SQLException e1) {
                    System.out.println("Category not inserted to database but sucessfully loaded");
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return category;
                }
            }
            if (retryCount[0] > 5) {
                System.out.println("Error getting category " + id + " from database, retried 5 times, giving up");
                sendErrorMessage("Error getting category " + id + " from database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return new CategoryDatabaseAdapter(id);
            }
            System.out.println("Error getting category " + id + " from database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            return getCategoryFromSQL(id, retryCount[0] + 1);
        }
    }
    
    public static ChannelDatabaseAdapter getChannelFromSQL(Long id, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetChannelWithID.sql", "UserDatabase.json", new String[] {id.toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            // use rs.getString("field") to get the data from the result set
            ChannelDatabaseAdapter channel = new ChannelDatabaseAdapter(id, rs.getLong("PingRoleID"), rs.getLong("ModeratorRoleID"));
            ((Connection) connectionData[0]).close();
            return channel;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                ChannelDatabaseAdapter channel = new ChannelDatabaseAdapter(id);
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertChannel.sql", "UserDatabase.json", new String[] {id.toString()});
                    return channel;
                } catch (SQLException e1) {
                    System.out.println("Channel not inserted to database but sucessfully loaded");
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return channel;
                }
            }
            if (retryCount[0] > 5) {
                System.out.println("Error getting channel " + id + " from database, retried 5 times, giving up");
                sendErrorMessage("Error getting channel " + id + " from database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return new ChannelDatabaseAdapter(id);
            }
            System.out.println("Error getting channel " + id + " from database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            return getChannelFromSQL(id, retryCount[0] + 1);
        }
    }

    public static ThreadDatabaseAdapter getThreadFromSQL(Long id, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetThreadWithID.sql", "UserDatabase.json", new String[] {id.toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            ThreadDatabaseAdapter thread = new ThreadDatabaseAdapter(id, rs.getLong("OriginalCreatorID"), rs.getLong("ReopenUserID"));
            ((Connection) connectionData[0]).close();
            return thread;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                ThreadDatabaseAdapter thread = new ThreadDatabaseAdapter(id, -1L, -1L);
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertThread.sql", "UserDatabase.json", new String[] {id.toString(), "-1", "-1"});
                    return thread;
                } catch (SQLException e1) {
                    System.out.println("Thread not inserted to database but sucessfully loaded");
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return thread;
                }
            }
            if (retryCount[0] > 5) {
                System.out.println("Error getting thread " + id + " from database, retried 5 times, giving up");
                sendErrorMessage("Error getting thread " + id + " from database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return new ThreadDatabaseAdapter(id, -1L, -1L);
            }
            System.out.println("Error getting thread " + id + " from database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            return getThreadFromSQL(id, retryCount[0] + 1);
        }
    }

    

    public static void updateGuildInSQL(Guild guild, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateGuild.sql", "UserDatabase.json", new String[] {guild.getId().toString()});
        } catch (SQLException e) {
            if (retryCount[0] > 5) {
                System.out.println("Error updating guild " + guild.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage("Error updating guild " + guild.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            System.out.println("Error updating guild " + guild.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateGuildInSQL(guild, retryCount[0] + 1);
        }
    }
    
    public static void updateCategoryInSQL(Category category, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateCategory.sql", "UserDatabase.json", new String[] {category.getId().toString()});
        } catch (SQLException e) {
            if (retryCount[0] > 5) {
                System.out.println("Error updating category " + category.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage("Error updating category " + category.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            System.out.println("Error updating category " + category.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateCategoryInSQL(category, retryCount[0] + 1);
        }
    }
    
    public static void updateChannelInSQL(Channel channel, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateChannel.sql", "UserDatabase.json", new String[] {channel.getId().toString(), channel.getPingRoleID().toString(), channel.getModeratorRoleID().toString()});
        } catch (SQLException e) {
            if (retryCount[0] > 5) {
                System.out.println("Error updating channel " + channel.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage("Error updating channel " + channel.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            System.out.println("Error updating channel " + channel.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateChannelInSQL(channel, retryCount[0] + 1);
        }
    }

    public static void updateThreadInSQL(Thread thread, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateThread.sql", "UserDatabase.json", new String[] {thread.getId().toString(), thread.getCreatorUserID().toString(), thread.getReopenUserID().toString()});
        } catch (SQLException e) {
            if (retryCount[0] > 5) {
                System.out.println("Error updating thread " + thread.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage("Error updating thread " + thread.getId() + " in database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            System.out.println("Error updating thread " + thread.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateThreadInSQL(thread, retryCount[0] + 1);
        }
    }

    public static void pushThreadToSQL(Long id, Long userID, int ... retryCount) {
        if (retryCount.length == 0) retryCount = new int[] {0};
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateThread.sql", "UserDatabase.json", new String[] {id.toString(), userID.toString(), "-1"});
        } catch (SQLException e) {
            if (retryCount[0] > 5) {
                System.out.println("Error updating thread " + id + " in database, retried 5 times, giving up");
                sendErrorMessage("Error updating thread " + id + " in database, retried 5 times, giving up");
                sendErrorMessage(e.getMessage() + "");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            System.out.println("Error updating thread " + id + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            pushThreadToSQL(id, userID, retryCount[0] + 1);
        }
    }
}
