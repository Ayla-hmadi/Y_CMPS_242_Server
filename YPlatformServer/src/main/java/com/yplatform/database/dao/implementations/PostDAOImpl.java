package com.yplatform.database.dao.implementations;

import com.yplatform.database.SQLiteConnectionManager;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostDAOImpl implements PostDAO {

    @Override
    public void addPost(Post post) {
        String sql = "INSERT INTO Post (content, timestamp, username) VALUES (?, ?, ?)";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, post.getContent());
            pstmt.setTimestamp(2, new Timestamp(post.getTimestamp().getTime()));
            pstmt.setString(3, post.getUsername());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public Post getPost(int id) {
        String sql = "SELECT * FROM Post WHERE id = ?";
        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Post(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp"),
                            rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM Post";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getTimestamp("timestamp"),
                        rs.getString("username")));
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return posts;
    }

    @Override
    public void updatePost(Post post) {
        String sql = "UPDATE Post SET content = ?, timestamp = ?, username = ? WHERE id = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, post.getContent());
            pstmt.setTimestamp(2, new Timestamp(post.getTimestamp().getTime()));
            pstmt.setString(3, post.getUsername());
            pstmt.setInt(4, post.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public void deletePost(int id) {
        String sql = "DELETE FROM Post WHERE id = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public List<Post> getPostsByUsers(List<String> usernames) {
        List<Post> posts = new ArrayList<>();

        if (usernames.isEmpty()) {
            return posts;
        }

        String placeholders = String.join(",", Collections.nCopies(usernames.size(), "?"));
        String sql = "SELECT * FROM Post WHERE username IN (" + placeholders + ")";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < usernames.size(); i++) {
                pstmt.setString(i + 1, usernames.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp"),
                            rs.getString("username")
                    );
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return posts;
    }



    @Override
    public List<Post> getPostsByUser(String username) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM Post WHERE username = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp"),
                            username));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return posts;
    }


    @Override
    public List<Post> getRandomPostsFromUsers(List<String> usernames, int limit) {
        List<Post> posts = new ArrayList<>();

        if (usernames.isEmpty()) {
            return getRandomPosts(limit);
        }

        String placeholders = String.join(",", Collections.nCopies(usernames.size(), "?"));
        String sql = "SELECT * FROM Post WHERE username NOT IN (" + placeholders + ") ORDER BY RANDOM() LIMIT ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < usernames.size(); i++) {
                pstmt.setString(i + 1, usernames.get(i));
            }

            pstmt.setInt(usernames.size() + 1, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp"),
                            rs.getString("username")
                    );
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return posts;
    }

    private List<Post> getRandomPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM Post ORDER BY RANDOM() LIMIT ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp"),
                            rs.getString("username")
                    );
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return posts;
    }
}
