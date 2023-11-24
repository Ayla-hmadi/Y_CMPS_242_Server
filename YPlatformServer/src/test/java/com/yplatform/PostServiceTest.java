package com.yplatform;

import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.models.Post;
import com.yplatform.services.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostDAO postDAO;

    @InjectMocks
    private PostService postService;

    @Test
    void getPost_ExistingId_ReturnsPost() {
        int postId = 1;
        Post post = new Post(postId, "Content", new Date(), "user");
        when(postDAO.getPost(postId)).thenReturn(post);

        Optional<Post> result = postService.getPost(postId);

        assertTrue(result.isPresent());
        assertEquals(post, result.get());
    }
}
