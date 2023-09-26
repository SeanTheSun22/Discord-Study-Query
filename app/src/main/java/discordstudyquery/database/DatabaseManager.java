package discordstudyquery.database;

import java.sql.SQLException;

public class DatabaseManager {
    public static void main(String[] args) throws SQLException {
        
    }
    public static boolean createLogin(String username, String password) throws SQLException {
        try {
            DatabaseSQLQuery.runSQLQuery("admin/CreateLogin.sql", "AdminMaster.json", new String[] {username, password});
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createUser(String username) throws SQLException {
        try {    
            DatabaseSQLQuery.runSQLQuery("admin/CreateUser.sql", "AdminDatabase.json", new String[] {username});
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createDiscordModelTable() throws SQLException {
        try {   
            DatabaseSQLQuery.runSQLQuery("admin/CreateDiscordModelTable.sql", "AdminDatabase.json");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
