package com.yplatform.models;

import com.yplatform.models.enums.ReactionType;

public class Reaction {
    private int postId; // Foreign Key
    private String username; // Foreign Key
    private ReactionType type;

    public Reaction() {}

    public Reaction(int postId, String username, ReactionType type) {
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

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
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
