package com.yplatform.database.dao.interfaces;

import com.yplatform.models.Post;

import java.util.List;

public interface PostDAO {
    void addPost(Post post);
    Post getPost(int id);
    List<Post> getAllPosts();
    void updatePost(Post post);
    void deletePost(int id);
    List<Post> getPostsByUser(String username);

    List<Post> getPostsByUsers(List<String> usernames);

    List<Post> getRandomPostsFromUsers(List<String> usernames, int limit);
}
