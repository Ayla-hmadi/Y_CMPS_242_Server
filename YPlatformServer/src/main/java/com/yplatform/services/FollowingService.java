package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.models.Following;
import com.yplatform.models.User;
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
            if (!isAlreadyFollowing(following.getFollowerUsername(), following.getFollowingUsername())) {
                followingDAO.addFollowing(following);
                LoggingUtil.logInfo(logger, following.getFollowerUsername() + " followed " + following.getFollowingUsername());
                return true;
            }
            return false;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in followUser()", e);
            return false;
        }
    }

    private boolean isAlreadyFollowing(String followerUsername, String followingUsername) {
        List<Following> existingFollowings = followingDAO.getFollowing(followerUsername);
        for (Following f : existingFollowings) {
            if (f.getFollowingUsername().equals(followingUsername)) {
                return true;
            }
        }
        return false;
    }
    public boolean unfollowUser(Following following) {
        try {
            if (isAlreadyFollowing(following.getFollowerUsername(), following.getFollowingUsername())) {
                followingDAO.removeFollowing(following);
                LoggingUtil.logInfo(logger, following.getFollowerUsername() + " unfollowed " + following.getFollowingUsername());
                return true;
            } else {
                LoggingUtil.logInfo(logger, following.getFollowerUsername() + " was not following " + following.getFollowingUsername());
                return false;
            }
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

    public List<User> getRandomUsersToFollow(String currentUsername, int limit) {
        try{
            List<User> randomUsers =  followingDAO.getRandomUsersToFollow(currentUsername, limit);
            LoggingUtil.logInfo(logger, "Sent random users to follow");
            return  randomUsers;
        } catch (Exception e){
            LoggingUtil.logError(logger, "Failed to perform operation in getRandomUsersToFollow()", e);
            return List.of();
        }
    }

}
