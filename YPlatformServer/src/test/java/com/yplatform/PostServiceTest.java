package com.yplatform;

import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Following;
import com.yplatform.models.Post;
import com.yplatform.network.OnlineUsers;
import com.yplatform.services.FollowingService;
import com.yplatform.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {

    @Mock
    private PostDAO postDAO;
    @Mock
    private FollowingDAO followingDAO;
    @Mock
    private FollowingService followingService;
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postDAO, followingService, followingDAO);
    }

    @AfterEach
    public void tearDown() {
        OnlineUsers.removeUser("follower");
        OnlineUsers.removeUser("nonFollower");
    }

    @Test
    public void testAddPostAndNotifyFollowers() throws Exception {
        String username = "user";
        String followerUsername = "follower";
        Post post = new Post();
        post.setUsername(username);
        post.setContent("Test post");

        Following following = new Following();
        following.setFollowerUsername(followerUsername);
        following.setFollowingUsername(username);

        when(followingDAO.getFollowers(username)).thenReturn(List.of(following));
        doNothing().when(postDAO).addPost(any(Post.class));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getOutputStream()).thenReturn(outputStream);
        when(mockSocket.isConnected()).thenReturn(true);

        OnlineUsers.addUser(followerUsername, mockSocket);

        postService.addPost(post);

        printWriter.flush();
        String output = outputStream.toString();
        assertTrue(output.contains("New post from " + username + ": " + post.getContent()),
                "Notification message should be present in output");

        OnlineUsers.removeUser(followerUsername);
    }

    @Test
    public void testNonFollowerOnlineUserDoesNotReceiveNotification() throws Exception {
        String username = "user";
        String nonFollowerUsername = "nonFollower";
        Post post = new Post();
        post.setUsername(username);
        post.setContent("Test post");

        when(followingDAO.getFollowers(username)).thenReturn(List.of());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getOutputStream()).thenReturn(outputStream);
        when(mockSocket.isConnected()).thenReturn(true);

        OnlineUsers.addUser(nonFollowerUsername, mockSocket);

        postService.addPost(post);

        printWriter.flush();
        String output = outputStream.toString();
        assertFalse(output.contains("New post from " + username + ": " + post.getContent()),
                "Non-follower should not receive notification");
    }
}
