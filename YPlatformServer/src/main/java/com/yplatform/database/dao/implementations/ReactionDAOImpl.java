package com.yplatform.database.dao.implementations;

import com.yplatform.database.dao.interfaces.ReactionDAO;
import com.yplatform.models.Reaction;

import java.util.ArrayList;
import java.util.List;

public class ReactionDAOImpl implements ReactionDAO {

    public void addReaction(Reaction reaction) {
    }

    public void removeReaction(Reaction reaction) {
    }

    public List<Reaction> getReactionsByPost(int postId) {
        return new ArrayList<>();
    }

    public List<Reaction> getReactionsByUser(String username) {
        return new ArrayList<>();
    }

}