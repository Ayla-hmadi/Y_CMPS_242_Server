package com.yplatform.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Post {
    private int id; // Primary Key
    private String content;
    private Date timestamp;
    private String username; // Foreign Key

    public Post() {}

    public Post(int id, String content, Date timestamp, String username) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", username='" + username + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
