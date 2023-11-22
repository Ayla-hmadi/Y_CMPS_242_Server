package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Post;
import com.yplatform.utils.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostDAO postDAO;

    @Inject
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public boolean addPost(Post post) {
        try {
            postDAO.addPost(post);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in addPost()", e);
            return false;
        }
    }

    public Optional<Post> getPost(int id) {
        try {
            return Optional.ofNullable(postDAO.getPost(id));
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getPost()", e);
            return Optional.empty();
        }
    }

    public List<Post> getAllPosts() {
        try {
            return postDAO.getAllPosts();
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getAllPosts()", e);
            return List.of();
        }
    }

    public boolean updatePost(Post post) {
        try {
            postDAO.updatePost(post);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in updatePost()", e);
            return false;
        }
    }

    public boolean deletePost(int id) {
        try {
            postDAO.deletePost(id);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in deletePost()", e);
            return false;
        }
    }

    public List<Post> getAllPostsByUser(String username) {
        // TODO filter sql
        return getAllPosts()
                .stream()
                .filter(post -> post.getUsername().equals(username))
                .collect(Collectors.toList());
    }
}