package com.yplatform.services;

import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.models.User;
import com.yplatform.utils.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean addUser(User user) {
        try {
            // Logic will be added here to make sure that the User is not already registerd
            userDAO.addUser(user);
            return true;
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
}