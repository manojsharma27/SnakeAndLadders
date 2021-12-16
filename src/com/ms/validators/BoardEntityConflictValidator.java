package com.ms.validators;

import com.ms.controller.Board;
import com.ms.exception.BoardEntityConflictException;
import com.ms.model.AbstractBoardEntity;

public class BoardEntityConflictValidator implements IBoardEntityValidator {

    @Override
    public void validate(Board board, AbstractBoardEntity entity) {
        int position = entity.getStart();
        if (board.getEntityMap().containsKey(position) || board.getEndPositions().contains(position)) {
            throw new BoardEntityConflictException(entity, position);
        }

        position = entity.getEnd();
        if (board.getEntityMap().containsKey(position) || board.getEndPositions().contains(position)) {
            throw new BoardEntityConflictException(entity, position);
        }

    }
}
