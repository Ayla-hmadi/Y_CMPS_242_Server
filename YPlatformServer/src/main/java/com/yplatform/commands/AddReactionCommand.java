package com.yplatform.commands;

import com.yplatform.models.Reaction;
import com.yplatform.models.enums.ReactionType;

public class AddReactionCommand {
    int postId;
    ReactionType reaction;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public ReactionType getReactionType() {
        return reaction;
    }

    public void setReactionType(ReactionType reaction) {
        this.reaction = reaction;
    }
}

