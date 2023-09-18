package discordstudyquery.database;

import java.sql.*;
import java.util.*;

public class DemoApplication {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(DemoApplication.class.getClassLoader().getResourceAsStream("credentials/database/ReadWriteConnection.properties"));

        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);

		Todo todo = new Todo(1L, "configuration", "congratulations, you have set up JDBC correctly!", true);
        insertData(todo, connection);
        todo = readData(connection);
        todo.setDetails("congratulations, you have updated data!");
        updateData(todo, connection);
        deleteData(todo, connection);

        System.out.println(todo);

        connection.close();
    }

    private static void insertData(Todo todo, Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO todo (id, description, details, done) VALUES (?, ?, ?, ?);");
    
        insertStatement.setLong(1, todo.getId());
        insertStatement.setString(2, todo.getDescription());
        insertStatement.setString(3, todo.getDetails());
        insertStatement.setBoolean(4, todo.isDone());
        insertStatement.executeUpdate();
    }

    private static Todo readData(Connection connection) throws SQLException {
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM todo;");
        ResultSet resultSet = readStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        Todo todo = new Todo();
        todo.setId(resultSet.getLong("id"));
        todo.setDescription(resultSet.getString("description"));
        todo.setDetails(resultSet.getString("details"));
        todo.setDone(resultSet.getBoolean("done"));
        return todo;
    }
    private static void updateData(Todo todo, Connection connection) throws SQLException {
        PreparedStatement updateStatement = connection
                .prepareStatement("UPDATE todo SET description = ?, details = ?, done = ? WHERE id = ?;");
    
        updateStatement.setString(1, todo.getDescription());
        updateStatement.setString(2, todo.getDetails());
        updateStatement.setBoolean(3, todo.isDone());
        updateStatement.setLong(4, todo.getId());
        updateStatement.executeUpdate();
        readData(connection);
    }
    private static void deleteData(Todo todo, Connection connection) throws SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM todo WHERE id = ?;");
        deleteStatement.setLong(1, todo.getId());
        deleteStatement.executeUpdate();
        readData(connection);
    }
}

class Todo {

    private Long id;
    private String description;
    private String details;
    private boolean done;

    public Todo() {
    }

    public Todo(Long id, String description, String details, boolean done) {
        this.id = id;
        this.description = description;
        this.details = details;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", done=" + done +
                '}';
    }
}