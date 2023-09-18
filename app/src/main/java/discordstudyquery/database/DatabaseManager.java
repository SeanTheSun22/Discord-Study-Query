package discordstudyquery.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseManager {
    public static void createLogin(String username, String password) throws SQLException {
        Map<String, String> credentials;
        try {
            credentials = readCredentials("credentials/database/AdminMaster.json");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        Connection connection = DriverManager.getConnection(credentials.get("url"), credentials.get("username"), credentials.get("password"));

        PreparedStatement createLoginStatement = connection.prepareStatement(String.format(
            "DROP LOGIN %s;\n" + 
            "CREATE LOGIN %s WITH PASSWORD = '%s';\n",
            username, username, password));
        createLoginStatement.executeUpdate();

        connection.close();
    }

    public static void createUser(String username) throws SQLException {
        Map<String, String> credentials;
        try {
            credentials = readCredentials("credentials/database/AdminDemo.json");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        Connection connection = DriverManager.getConnection(credentials.get("url"), credentials.get("username"), credentials.get("password"));

        PreparedStatement createAccountStatement = connection.prepareStatement(String.format(
            "DROP USER IF EXISTS [%s];" +
            "CREATE USER [%s] FOR LOGIN [%s] WITH DEFAULT_SCHEMA = dbo;" +
            "ALTER ROLE db_datareader ADD MEMBER [%s];" + 
            "ALTER ROLE db_datawriter ADD MEMBER [%s];",
            username, username, username, username, username));
        createAccountStatement.executeUpdate();

        connection.close();
    }

    public static Map<String, String> readCredentials(String filePath) throws FileNotFoundException, IOException {
        /*
         * Credentials file in the format:
         * {
         *     "url":"jdbc:sqlserver://<HostName>.database.windows.net:1433;database=<DatabaseName>",
         *     "username":"<Username>@<HostName>",
         *     "password":"<Password>"
         * }
         */
        InputStream inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {throw new FileNotFoundException("File not found: " + filePath);}

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String, String>>() {};

        return objectMapper.readValue(inputStream, typeReference);
    }
}
