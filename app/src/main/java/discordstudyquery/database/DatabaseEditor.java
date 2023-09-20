package discordstudyquery.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import discordstudyquery.adapter.GuildAdapter;
import discordstudyquery.adapter.CategoryAdapter;
import discordstudyquery.adapter.ChannelAdapter;
import discordstudyquery.adapter.ThreadAdapter;

public class DatabaseEditor {
    public static GuildAdapter getGuildFromSQL(GuildAdapter inputGuild) {
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetGuildWithID.sql", "UserDatabase.json", new String[] {inputGuild.getID().toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            GuildAdapter guild = new GuildAdapter(rs.getString("Name"), rs.getLong("GuildID"));
            ((Connection) connectionData[0]).close();
            return guild;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                GuildAdapter guild = new GuildAdapter(inputGuild.getName(), inputGuild.getID());
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertGuild.sql", "UserDatabase.json", new String[] {guild.getID().toString(), guild.getName()});
                    return guild;
                } catch (SQLException e1) {
                    System.out.println("Error updating guild " + guild.getName() + " in database, retrying...");
                    try {Thread.sleep(1000);} catch (InterruptedException e2) {}
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return guild;
                }
            }
            return getGuildFromSQL(inputGuild);
        }
    }

    public static CategoryAdapter getCategoryFromSQL(CategoryAdapter inputCategory) {
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetCategoryWithID.sql", "UserDatabase.json", new String[] {inputCategory.getID().toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            CategoryAdapter category = new CategoryAdapter(rs.getString("Name"), rs.getLong("CategoryID"), rs.getLong("ParentID"));
            ((Connection) connectionData[0]).close();
            return category;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                CategoryAdapter category = new CategoryAdapter(inputCategory.getName(), inputCategory.getID(), inputCategory.getParentID());
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertCategory.sql", "UserDatabase.json", new String[] {category.getID().toString(), category.getParentID().toString(),category.getName()});
                    return category;
                } catch (SQLException e1) {
                    System.out.println("Error updating category " + inputCategory.getName() + " in database, retrying...");
                    try {Thread.sleep(1000);} catch (InterruptedException e2) {}
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return category;
                }
            }
            return getCategoryFromSQL(inputCategory);
        }
    }

    public static ChannelAdapter getChannelFromSQL(ChannelAdapter inputChannel) {
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetChannelWithID.sql", "UserDatabase.json", new String[] {inputChannel.getID().toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            ChannelAdapter channel = new ChannelAdapter(rs.getString("Name"), rs.getLong("ChannelID"), rs.getLong("ParentID"));
            ((Connection) connectionData[0]).close();
            return channel;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                ChannelAdapter channel = new ChannelAdapter(inputChannel.getName(), inputChannel.getID(), inputChannel.getParentID());
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertChannel.sql", "UserDatabase.json", new String[] {channel.getID().toString(), channel.getParentID().toString(), channel.getName()});
                    return channel;
                } catch (SQLException e1) {
                    System.out.println("Error updating channel " + inputChannel.getName() + " in database, retrying...");
                    try {Thread.sleep(1000);} catch (InterruptedException e2) {}
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return channel;
                }
            }
            return getChannelFromSQL(inputChannel);
        }
    }

    public static ThreadAdapter getThreadFromSQL(ThreadAdapter inputThread) {
        try {
            Object[] connectionData = DatabaseSQLQuery.getSQLQueryWithResult("readwrite/GetThreadWithID.sql", "UserDatabase.json", new String[] {inputThread.getID().toString()});
            ResultSet rs = (ResultSet) connectionData[1];
            rs.next();
            ThreadAdapter thread = new ThreadAdapter(rs.getString("Name"), rs.getLong("ThreadID"), rs.getLong("ParentID"));
            ((Connection) connectionData[0]).close();
            return thread;
        } catch (SQLException e) {
            if (e.getMessage().equals("The result set has no current row.")) {
                ThreadAdapter thread = new ThreadAdapter(inputThread.getName(), inputThread.getID(), inputThread.getParentID());
                try {
                    DatabaseSQLQuery.runSQLQuery("readwrite/InsertThread.sql", "UserDatabase.json", new String[] {thread.getID().toString(), thread.getParentID().toString(), thread.getName()});
                    return thread;
                } catch (SQLException e1) {
                    System.out.println("Error updating thread " + inputThread.getName() + " in database, retrying...");
                    try {Thread.sleep(1000);} catch (InterruptedException e2) {}
                    System.out.println(e1.getMessage());
                    e1.printStackTrace();
                    return thread;
                }
            }
            return getThreadFromSQL(inputThread);
        }
    }
}
