package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Following;
import com.yplatform.models.Post;
import com.yplatform.network.OnlineUsers;
import com.yplatform.utils.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostDAO postDAO;
    private final FollowingService followingService;
    private final FollowingDAO followingDAO;

    @Inject
    public PostService(PostDAO postDAO, FollowingService followingService, FollowingDAO followingDAO) {
        this.postDAO = postDAO;
        this.followingService = followingService;
        this.followingDAO = followingDAO;
    }

    public boolean addPost(Post post) {
        try {
            postDAO.addPost(post);
            notifyFollowers(post);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in addPost()", e);
            return false;
        }
    }

    private void notifyFollowers(Post post) {
        List<Following> followers = followingDAO.getFollowers(post.getUsername());
        for (Following follower : followers) {
            sendNotification(follower.getFollowerUsername(), post);
        }
    }

    private void sendNotification(String followerUsername, Post post) {
        Socket followerSocket = OnlineUsers.getUserSocket(followerUsername);
        if (followerSocket != null) {
            try {
                PrintWriter out = new PrintWriter(followerSocket.getOutputStream(), true);
                String notificationMessage = createNotificationMessage(post);
                out.println(notificationMessage);
            } catch (IOException e) {
                System.err.println("Error sending notification to " + followerUsername);
            }
        }
    }

    private String createNotificationMessage(Post post) {
        return "New post from " + post.getUsername() + ": " + post.getContent();
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

            return postDAO.getPostsByUsers(followedUsernames);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getPostsByFollowedUsers()", e);
            return List.of();
        }
    }

    public List<Post> getRandomPostsFromNonFollowedUsers(String username, int limit) {
        try {
            List<String> followedUsernames = followingDAO.getFollowing(username)
                    .stream()
                    .map(Following::getFollowingUsername)
                    .collect(Collectors.toList());

            return postDAO.getRandomPostsFromUsers(followedUsernames, limit);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }
    }

    public Post addPost(String content, String username) {
        var post = new Post();
        post.setContent(content);
        post.setUsername(username);
        post.setTimestamp(new Date());
        if (addPost(post)) {
            return post;
        } else {
            return null;
        }
    }
}
