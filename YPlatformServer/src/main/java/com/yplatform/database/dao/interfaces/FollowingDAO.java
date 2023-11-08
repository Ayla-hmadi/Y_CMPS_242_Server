package com.yplatform.database.dao.interfaces;

import com.yplatform.models.Following;

import java.util.List;

public interface FollowingDAO {
    void addFollowing(Following following);
    void removeFollowing(Following following);
    List<Following> getFollowers(String username);
    List<Following> getFollowing(String username);
}
