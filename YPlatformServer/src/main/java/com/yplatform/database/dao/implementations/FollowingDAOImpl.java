package com.yplatform.database.dao.implementations;

import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.models.Following;

import java.util.ArrayList;
import java.util.List;

public class FollowingDAOImpl implements FollowingDAO {

    public void addFollowing(Following following) {
        // Implement the logic to add a following using JDBC
    }

    public void removeFollowing(Following following) {
        // Implement the logic to remove a following using JDBC
    }

    public List<Following> getFollowers(String username) {
        // Implement the logic to get all followers of a user using JDBC
        return new ArrayList<>();
    }

    public List<Following> getFollowing(String username) {
        // Implement the logic to get all users a given user is following using JDBC
        return new ArrayList<>();
    }

}