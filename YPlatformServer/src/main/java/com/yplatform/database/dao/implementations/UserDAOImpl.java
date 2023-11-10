package com.yplatform.database.dao.implementations;

import com.yplatform.database.SQLiteConnectionManager;
import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO User (username, name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, hashPassword(user.getPassword()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public Optional<User> getUser(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("email"),
                            "");
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("email"),
                        "");
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET name = ?, email = ?, password = ? WHERE username = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, hashPassword(user.getPassword()));
            pstmt.setString(4, user.getUsername());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public void deleteUser(String username) {
        String sql = "DELETE FROM User WHERE username = ?";

        try (Connection connection = SQLiteConnectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
