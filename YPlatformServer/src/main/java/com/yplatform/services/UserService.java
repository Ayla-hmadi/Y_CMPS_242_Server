package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.models.User;
import com.yplatform.utils.LoggingUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean addUser(User user) {
        try {
            if (userExists(user.getUsername(), user.getEmail())) {
                LoggingUtil.logInfo(logger, "User already exists with the given username/email");
                return false;
            }
            user.setPassword(hashPassword(user.getPassword()));
            return userDAO.addUser(user);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in addUser()", e);
            return false;
        }
    }

    public Optional<User> getUser(String username) {
        try {
            return userDAO.getUser(username);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getUser()", e);
            return Optional.empty();
        }
    }
    public boolean authenticateUser(String username, String password) {
        try {
            Optional<User> user = getUser(username);
            if (user.isPresent() && BCrypt.checkpw(password, user.get().getPassword())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to authenticate user", e);
            return false;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getAllUsers()", e);
            return List.of();
        }
    }

    public boolean updateUser(User user) {
        try {
            user.setPassword(hashPassword(user.getPassword()));
            userDAO.updateUser(user);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in updateUser()", e);
            return false;
        }
    }

    public boolean deleteUser(String username) {
        try {
            userDAO.deleteUser(username);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in deleteUser()", e);
            return false;
        }
    }

    private boolean userExists(String username, String email) {
        return getAllUsers().stream().anyMatch(u -> u.getUsername().equals(username) || u.getEmail().equals(email));
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}