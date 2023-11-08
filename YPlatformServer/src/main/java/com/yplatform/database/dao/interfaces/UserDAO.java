package com.yplatform.database.dao.interfaces;

import com.yplatform.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void addUser(User user);
    Optional<User> getUser(String username);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(String username);
}
