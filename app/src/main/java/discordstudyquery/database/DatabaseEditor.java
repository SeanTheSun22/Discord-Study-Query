package discordstudyquery.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import discordstudyquery.database.databaseadapter.CategoryDatabaseAdapter;
import discordstudyquery.database.databaseadapter.ChannelDatabaseAdapter;
import discordstudyquery.database.databaseadapter.GuildDatabaseAdapter;
import discordstudyquery.database.databaseadapter.ThreadDatabaseAdapter;

import discordstudyquery.guild.structure.Guild;
import discordstudyquery.guild.structure.Category;
import discordstudyquery.guild.structure.Channel;
import discordstudyquery.guild.structure.Thread;

public class DatabaseEditor {
    public static GuildDatabaseAdapter getGuildFromSQL(Long id) {
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
            return getGuildFromSQL(id);
        }
    }

    public static CategoryDatabaseAdapter getCategoryFromSQL(Long id) {
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
            return getCategoryFromSQL(id);
        }
    }
    
    public static ChannelDatabaseAdapter getChannelFromSQL(Long id) {
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
            return getChannelFromSQL(id);
        }
    }

    public static ThreadDatabaseAdapter getThreadFromSQL(Long id) {
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetThreadWithID.sql", "UserDatabase.json", new String[] {id.toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            ThreadDatabaseAdapter thread = new ThreadDatabaseAdapter(id, rs.getLong("OriginalCreatorID"));
            ((Connection) connectionData[0]).close();
            return thread;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                ThreadDatabaseAdapter thread = new ThreadDatabaseAdapter(id, -1L);
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertThread.sql", "UserDatabase.json", new String[] {id.toString(), "-1"});
                    return thread;
                } catch (SQLException e1) {
                    System.out.println("Thread not inserted to database but sucessfully loaded");
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return thread;
                }
            }
            return getThreadFromSQL(id);
        }
    }

    

    public static void updateGuildInSQL(Guild guild) {
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateGuild.sql", "UserDatabase.json", new String[] {guild.getId().toString()});
        } catch (SQLException e) {
            System.out.println("Error updating guild " + guild.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateGuildInSQL(guild);
        }
    }
    
    public static void updateCategoryInSQL(Category category) {
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateCategory.sql", "UserDatabase.json", new String[] {category.getId().toString()});
        } catch (SQLException e) {
            System.out.println("Error updating category " + category.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateCategoryInSQL(category);
        }
    }
    
    public static void updateChannelInSQL(Channel channel) {
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateChannel.sql", "UserDatabase.json", new String[] {channel.getId().toString(), channel.getPingRoleID().toString(), channel.getModeratorRoleID().toString()});
        } catch (SQLException e) {
            System.out.println("Error updating channel " + channel.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateChannelInSQL(channel);
        }
    }

    public static void updateThreadInSQL(Thread thread) {
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateThread.sql", "UserDatabase.json", new String[] {thread.getId().toString(), thread.getCreatorUserId().toString()});
        } catch (SQLException e) {
            System.out.println("Error updating thread " + thread.getId() + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            updateThreadInSQL(thread);
        }
    }

    public static void pushThreadToSQL(Long id, Long userID) {
        try {
            DatabaseSQLQuery.runSQLQuery("readwrite/UpdateThread.sql", "UserDatabase.json", new String[] {id.toString(), userID.toString()});
        } catch (SQLException e) {
            System.out.println("Error updating thread " + id + " in database, retrying...");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {java.lang.Thread.sleep(1000);} catch (InterruptedException e2) {}
            pushThreadToSQL(id, userID);
        }
    }
}
