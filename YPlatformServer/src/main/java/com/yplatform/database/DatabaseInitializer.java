package com.yplatform.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initializeDatabaseIfNeeded() {
        if (!isDatabaseInitialized()) {
            initializeDatabase();
        }
    }

    private static boolean isDatabaseInitialized() {
        try (Connection connection = SQLiteConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='User';");
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error checking database initialization", e);
        }
    }


    private static void initializeDatabase() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = SQLiteConnectionManager.getConnection();
            statement = connection.createStatement();

            // User Table
            statement.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "username TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "email TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL" +
                    ");");

            statement.execute("INSERT INTO `User` (username, name, email, password) " +
                    "VALUES ('oz', 'oz', 'oz', 'oz')");

            // Post Table
            statement.execute("CREATE TABLE IF NOT EXISTS Post (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "content TEXT NOT NULL," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "username TEXT NOT NULL," +
                    "FOREIGN KEY (username) REFERENCES User(username)" +
                    ");");

            // Following Table
            statement.execute("CREATE TABLE IF NOT EXISTS Following (" +
                    "followerUsername TEXT NOT NULL," +
                    "followingUsername TEXT NOT NULL," +
                    "PRIMARY KEY (followerUsername, followingUsername)," +
                    "FOREIGN KEY (followerUsername) REFERENCES User(username)," +
                    "FOREIGN KEY (followingUsername) REFERENCES User(username)" +
                    ");");

            // Reaction Table
            statement.execute("CREATE TABLE IF NOT EXISTS Reaction (" +
                    "postId INTEGER NOT NULL," +
                    "username TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "FOREIGN KEY (postId) REFERENCES Post(id)," +
                    "FOREIGN KEY (username) REFERENCES User(username)," +
                    "PRIMARY KEY (postId, username, type)" +
                    ");");

            System.out.println("Database tables created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing the database", e);
        } finally {
            SQLiteConnectionManager.closeConnection(connection);
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
