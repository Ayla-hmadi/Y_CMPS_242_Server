package com.yplatform;

import com.yplatform.database.dao.interfaces.ReactionDAO;
import com.yplatform.models.Reaction;
import com.yplatform.models.enums.ReactionType;
import com.yplatform.services.ReactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReactionServiceTest {

    @Mock
    private ReactionDAO reactionDAO;

    @InjectMocks
    private ReactionService reactionService;

    @Test
    void addReaction_NewReaction_ReturnsTrue() {
        Reaction reaction = new Reaction(1, "user", ReactionType.LIKE);

        doNothing().when(reactionDAO).addReaction(any(Reaction.class));

        boolean result = reactionService.addReaction(reaction);

        assertTrue(result);
        verify(reactionDAO).addReaction(any(Reaction.class));
    }

}
