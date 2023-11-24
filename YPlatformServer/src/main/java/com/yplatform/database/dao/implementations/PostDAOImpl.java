package com.yplatform.database.dao.implementations;

import com.yplatform.database.SQLiteConnectionManager;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Post;

import java.sql.*;
import java.util.ArrayList;
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
}
