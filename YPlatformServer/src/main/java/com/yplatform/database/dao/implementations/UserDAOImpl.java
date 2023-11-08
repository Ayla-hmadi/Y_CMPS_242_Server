package com.yplatform.database.dao.implementations;

import com.yplatform.database.SQLiteConnectionManager;
import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    public void addUser(User user) {
    }

    public Optional<User> getUser(String username) {
        User user = new User();

        return Optional.of(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>();
    }

    public void updateUser(User user) {
    }

    public void deleteUser(String username) {
    }

}