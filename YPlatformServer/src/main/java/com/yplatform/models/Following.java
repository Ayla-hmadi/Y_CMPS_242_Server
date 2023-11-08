package com.yplatform.models;

public class Following {
    private String followerUsername; // Foreign Key
    private String followingUsername; // Foreign Key

    public Following() {}

    public Following(String followerUsername, String followingUsername) {
        this.followerUsername = followerUsername;
        this.followingUsername = followingUsername;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    @Override
    public String toString() {
        return "Following{" +
                "followerUsername='" + followerUsername + '\'' +
                ", followingUsername='" + followingUsername + '\'' +
                '}';
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }
}
