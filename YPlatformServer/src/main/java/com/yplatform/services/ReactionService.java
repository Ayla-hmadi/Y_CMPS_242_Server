package com.yplatform.services;

import com.google.inject.Inject;
import com.yplatform.database.dao.interfaces.ReactionDAO;
import com.yplatform.models.Reaction;
import com.yplatform.utils.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReactionService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ReactionDAO reactionDAO;

    @Inject
    public ReactionService(ReactionDAO reactionDAO) {
        this.reactionDAO = reactionDAO;
    }

    public boolean handleReaction(Reaction reaction) {
        try {
            List<Reaction> existingReactions = reactionDAO.getReactionsByUserAndPost(reaction.getUsername(), reaction.getPostId());
            if (!existingReactions.isEmpty()) {
                return updateOrRemoveReaction(existingReactions.get(0), reaction);
            } else {
                return addReaction(reaction);
            }
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to handle reaction", e);
            return false;
        }
    }

    private boolean updateOrRemoveReaction(Reaction existingReaction, Reaction newReaction) {
        if (existingReaction.getType().equals(newReaction.getType())) {
            return removeReaction(existingReaction);
        } else {
            return updateReaction(newReaction);
        }
    }

    private boolean updateReaction(Reaction reaction) {
        try {
            reactionDAO.updateReaction(reaction);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in addReaction()", e);
            return false;
        }
    }

    public boolean addReaction(Reaction reaction) {
        try {
            reactionDAO.addReaction(reaction);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in addReaction()", e);
            return false;
        }
    }

    public boolean removeReaction(Reaction reaction) {
        try {
            reactionDAO.removeReaction(reaction);
            return true;
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in removeReaction()", e);
            return false;
        }
    }

    public List<Reaction> getReactionsByPost(int postId) {
        try {
            return reactionDAO.getReactionsByPost(postId);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getReactionsByPost()", e);
            return List.of();
        }
    }

    public List<Reaction> getReactionsByUser(String username) {
        try {
            return reactionDAO.getReactionsByUser(username);
        } catch (Exception e) {
            LoggingUtil.logError(logger, "Failed to perform operation in getReactionsByUser()", e);
            return List.of();
        }
    }
}