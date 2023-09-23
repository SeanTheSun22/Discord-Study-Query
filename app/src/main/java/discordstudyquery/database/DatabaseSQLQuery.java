package discordstudyquery.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseSQLQuery {
    public static void runSQLQuery(String queryFilePath, String credentialFilePath) throws SQLException {
        runSQLQuery(queryFilePath, credentialFilePath, new String[] {});
    }
    public static void runSQLQuery(String queryFilePath, String credentialFilePath, String[] arguments) throws SQLException {
        /*
         * Runs the SQL query in the file at resources/database/queries/<filePath>
         */

        Map<String, String> credentials;
        try {
            credentials = readCredentials("credentials/database/" + credentialFilePath);
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

        Scanner scanner = new Scanner(DatabaseManager.class.getClassLoader().getResourceAsStream("database/queries/" + queryFilePath));
        
        String currentLine;
        ArrayList<String> executeString = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            for (int i = 0; i < arguments.length; i++) {
                currentLine = currentLine.replace("{" + i + "}", arguments[i]);
            }
            executeString.add(currentLine);
        }
        System.out.println(String.join(" ", executeString));
        PreparedStatement statement = connection.prepareStatement(String.join(" ", executeString));
        statement.executeUpdate();
        connection.close();
    }

    public static Object[] getSQLQueryWithResult(String queryFilePath, String credentialFilePath, String[] arguments) throws SQLException {
        /*
         * Runs the SQL query in the file at resources/database/queries/<filePath>
         */

        Map<String, String> credentials;
        try {
            credentials = readCredentials("credentials/database/" + credentialFilePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        Connection connection = DriverManager.getConnection(credentials.get("url"), credentials.get("username"), credentials.get("password"));

        Scanner scanner = new Scanner(DatabaseManager.class.getClassLoader().getResourceAsStream("database/queries/" + queryFilePath));
        ArrayList<String> executeString = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            for (int i = 0; i < arguments.length; i++) {
                currentLine = currentLine.replace("{" + i + "}", arguments[i]);
            }
            executeString.add(currentLine);
        }
        scanner.close();
        System.out.println(String.join(" ", executeString));
        PreparedStatement preparedStatement = connection.prepareStatement(String.join(" ", executeString));
        ResultSet resultSet = preparedStatement.executeQuery();

        return new Object[] {connection, resultSet};
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
