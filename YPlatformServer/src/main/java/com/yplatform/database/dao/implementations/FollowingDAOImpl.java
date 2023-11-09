package com.yplatform.database.dao.implementations;

import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.models.Following;

import java.util.ArrayList;
import java.util.List;

public class FollowingDAOImpl implements FollowingDAO {

    public void addFollowing(Following following) {
    }

    public void removeFollowing(Following following) {
    }

    public List<Following> getFollowers(String username) {
        return new ArrayList<>();
    }

    public List<Following> getFollowing(String username) {
        return new ArrayList<>();
    }

}