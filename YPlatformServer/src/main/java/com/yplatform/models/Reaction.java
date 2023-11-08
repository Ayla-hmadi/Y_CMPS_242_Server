package com.yplatform.models;

public class Reaction {
    private int postId; // Foreign Key
    private String username; // Foreign Key
    private String type;

    public Reaction() {}

    public Reaction(int postId, String username, String type) {
        this.postId = postId;
        this.username = username;
        this.type = type;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "postId=" + postId +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
