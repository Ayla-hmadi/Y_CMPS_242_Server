package com.yplatform.database.dao.implementations;

import com.yplatform.database.dao.interfaces.ReactionDAO;
import com.yplatform.database.SQLiteConnectionManager;
import com.yplatform.models.Reaction;
import com.yplatform.models.enums.ReactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReactionDAOImpl implements ReactionDAO {

    @Override
    public void addReaction(Reaction reaction) {
        String sql = "INSERT INTO Reaction (postId, username, type) VALUES (?, ?, ?)";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, reaction.getPostId());
            pstmt.setString(2, reaction.getUsername());
            pstmt.setString(3, reaction.getType().toString());


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public List<Reaction> getReactionsByUserAndPost(String username, int postId) {
        List<Reaction> reactions = new ArrayList<>();
        String sql = "SELECT * FROM Reaction WHERE username = ? AND postId = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reactions.add(new Reaction(
                            rs.getInt("postId"),
                            username,
                            ReactionType.valueOf(rs.getString("type"))));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return reactions;
    }

    @Override
    public boolean updateReaction(Reaction reaction) {
        String sql = "UPDATE Reaction SET type = ? WHERE postId = ? AND username = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reaction.getType().toString());
            pstmt.setInt(2, reaction.getPostId());
            pstmt.setString(3, reaction.getUsername());

            int updatedRows = pstmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
            return false;
        }
    }

    @Override
    public void removeReaction(Reaction reaction) {
        String sql = "DELETE FROM Reaction WHERE postId = ? AND username = ? AND type = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, reaction.getPostId());
            pstmt.setString(2, reaction.getUsername());
            pstmt.setString(3, reaction.getType().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public List<Reaction> getReactionsByPost(int postId) {
        List<Reaction> reactions = new ArrayList<>();
        String sql = "SELECT * FROM Reaction WHERE postId = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reactions.add(new Reaction(
                            rs.getInt("postId"),
                            rs.getString("username"),
                            ReactionType.valueOf(rs.getString("type"))));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return reactions;
    }

    @Override
    public List<Reaction> getReactionsByUser(String username) {
        List<Reaction> reactions = new ArrayList<>();
        String sql = "SELECT * FROM Reaction WHERE username = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reactions.add(new Reaction(
                            rs.getInt("postId"),
                            username,
                            ReactionType.valueOf(rs.getString("type"))));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return reactions;
    }
}
