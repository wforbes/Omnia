package net.wforbes.omnia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public Connection connection;

    public boolean connect() {
        connection = null;
        try {
            String url = "jdbc:sqlite:C:/sqlite/db/omnia.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Database connection successful");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
