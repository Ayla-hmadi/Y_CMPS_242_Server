package com.yplatform.database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initializeDatabaseIfNeeded() {
        if (!isDatabaseInitialized()) {
            initializeDatabase();
            seedData();
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

    private static void seedData() {
        try (Connection connection = SQLiteConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            // Users
            String hashedPassword = BCrypt.hashpw("hashed_password1", BCrypt.gensalt());
            statement.execute("INSERT INTO User (username, name, email, password) VALUES ('alice', 'Alice', 'alice@example.com', '" + hashedPassword + "')");

            String hashedPassword2 = BCrypt.hashpw("hashed_password2", BCrypt.gensalt());
            statement.execute("INSERT INTO User (username, name, email, password) VALUES ('bob', 'Bob', 'bob@example.com', 'hashed_password2')");

            // Posts
            statement.execute("INSERT INTO Post (content, username) VALUES ('Alice first post', 'alice')");
            statement.execute("INSERT INTO Post (content, username) VALUES ('Alice second post', 'alice')");
            statement.execute("INSERT INTO Post (content, username) VALUES ('Bob first post', 'bob')");

            // Following
            statement.execute("INSERT INTO Following (followerUsername, followingUsername) VALUES ('alice', 'bob')");
            statement.execute("INSERT INTO Following (followerUsername, followingUsername) VALUES ('bob', 'alice')");

            // Reactions
            statement.execute("INSERT INTO Reaction (postId, username, type) VALUES (1, 'bob', 'LIKE')");
            statement.execute("INSERT INTO Reaction (postId, username, type) VALUES (2, 'bob', 'LOVE')");
            statement.execute("INSERT INTO Reaction (postId, username, type) VALUES (3, 'alice', 'LIKE')");

        } catch (SQLException e) {
            throw new RuntimeException("Error seeding the database", e);
        }
    }

}
