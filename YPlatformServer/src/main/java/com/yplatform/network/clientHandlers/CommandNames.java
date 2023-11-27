package com.yplatform.network.clientHandlers;

public class CommandNames {
    public static final String Login = "login";
    public static final String Register = "register";
    public static final String Exit = "exit";
    // Posts

    public static final String GetFollowedUsersPosts = "getPostsByFollowedUsers";
    public static final String AddPost = "add post";
    public static final String MyPosts = "my posts";
    public static final String React = "react";
    public static final String MyInterests = "my interests";

    // Following
    public static final String Follow = "follow";
    public static final String Unfollow = "unfollow";
    public static final String GetFollowingByUsername = "getFollowingByUsername";
    public static final String GetFollowersByUsername = "getFollowersByUsername";
    public static final String GetRandomUsersToFollow = "GetRandomUsersToFollow";
}
