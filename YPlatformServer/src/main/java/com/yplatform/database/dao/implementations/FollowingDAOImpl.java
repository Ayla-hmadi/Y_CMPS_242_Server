package com.yplatform.database.dao.implementations;

import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.database.SQLiteConnectionManager;
import com.yplatform.models.Following;
import com.yplatform.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowingDAOImpl implements FollowingDAO {

    @Override
    public void addFollowing(Following following) {
        String sql = "INSERT INTO Following (followerUsername, followingUsername) VALUES (?, ?)";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, following.getFollowerUsername());
            pstmt.setString(2, following.getFollowingUsername());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public void removeFollowing(Following following) {
        String sql = "DELETE FROM Following WHERE followerUsername = ? AND followingUsername = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, following.getFollowerUsername());
            pstmt.setString(2, following.getFollowingUsername());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public List<Following> getFollowers(String username) {
        List<Following> followers = new ArrayList<>();
        String sql = "SELECT followerUsername FROM Following WHERE followingUsername = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                followers.add(new Following(rs.getString("followerUsername"), username));
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return followers;
    }

    @Override
    public List<Following> getFollowing(String username) {
        List<Following> followings = new ArrayList<>();
        String sql = "SELECT followingUsername FROM Following WHERE followerUsername = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                followings.add(new Following(username, rs.getString("followingUsername")));
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return followings;
    }

    @Override
    public List<User> getRandomUsersToFollow(String currentUsername, int limit) {
        List<User> randomUsers = new ArrayList<>();
        String sql = "SELECT * FROM User WHERE username <> ? ORDER BY RANDOM() LIMIT ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, currentUsername);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    randomUsers.add(new User(
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("email"),
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return randomUsers;
    }
}
