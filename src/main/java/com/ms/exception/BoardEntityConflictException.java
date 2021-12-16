package com.ms.exception;

import com.ms.model.AbstractBoardEntity;

public class BoardEntityConflictException extends GameException {
    private static final String MSG = "Failed to add %s as it conflicts with another entity at position %d";

    public BoardEntityConflictException(AbstractBoardEntity entity, int position) {
        super(String.format(MSG, entity, position));
    }
}
