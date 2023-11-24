package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Following;
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
    private final FollowingService followingService;

    @Inject
    public PostService(PostDAO postDAO, FollowingService followingService) {
        this.postDAO = postDAO;
        this.followingService = followingService; // Initialize in constructor
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
        try {
            return postDAO.getPostsByUser(username);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getAllPostsByUser()", e);
            return List.of();
        }
    }

    public List<Post> getPostsByFollowedUsers(String username) {
        try {
            List<String> followedUsernames = followingService.getFollowingByUsername(username)
                    .stream()
                    .map(Following::getFollowingUsername)
                    .collect(Collectors.toList());

            return postDAO.getAllPosts().stream()
                    .filter(post -> followedUsernames.contains(post.getUsername()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getPostsByFollowedUsers()", e);
            return List.of();
        }
    }
}
