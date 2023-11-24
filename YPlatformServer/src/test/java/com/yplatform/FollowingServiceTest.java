package com.yplatform;

import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.models.Following;
import com.yplatform.services.FollowingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FollowingServiceTest {

    @Mock
    private FollowingDAO followingDAO;

    @InjectMocks
    private FollowingService followingService;

    @Test
    void followUser_NotFollowingYet_ReturnsTrue() {
        Following follow = new Following("alice", "bob");

        doNothing().when(followingDAO).addFollowing(any(Following.class));

        boolean result = followingService.followUser(follow);

        assertTrue(result);
        verify(followingDAO).addFollowing(any(Following.class));
    }

}

