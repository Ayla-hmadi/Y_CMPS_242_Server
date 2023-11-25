package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.models.Following;
import com.yplatform.utils.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FollowingService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final FollowingDAO followingDAO;

    @Inject
    public FollowingService(FollowingDAO followingDAO) {
        this.followingDAO = followingDAO;
    }

    public boolean followUser(Following following) {
        try {
            // Additional logic will be added to make sure the person is not already following this other person
            followingDAO.addFollowing(following);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in followUser()", e);
            return false;
        }
    }

    public boolean unfollowUser(Following following) {
        try {
            followingDAO.removeFollowing(following);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in unfollowUser()", e);
            return false;
        }
    }

    public List<Following> getFollowersByUsername(String username) {
        try {
            return followingDAO.getFollowers(username);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getFollowersByUsername()", e);
            return List.of();
        }
    }

    public List<Following> getFollowingByUsername(String username) {
        try {
            return followingDAO.getFollowing(username);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getFollowingByUsername()", e);
            return List.of();
        }
    }
}
