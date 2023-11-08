package com.yplatform.database.dao.interfaces;

import com.yplatform.models.Reaction;

import java.util.List;

public interface ReactionDAO {
    void addReaction(Reaction reaction);
    void removeReaction(Reaction reaction);
    List<Reaction> getReactionsByPost(int postId);
    List<Reaction> getReactionsByUser(String username);
}
