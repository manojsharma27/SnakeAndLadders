package com.ms.validators;

import com.ms.controller.Board;
import com.ms.exception.IllegalBoardEntityConfigException;
import com.ms.model.AbstractBoardEntity;

public class BoardEntityStartEndPositionValidator implements IBoardEntityValidator {

    @Override
    public void validate(Board board, AbstractBoardEntity entity) {
        if (entity.getStart() < 1 || entity.getStart() >= 100 ||
                entity.getEnd() < 1 || entity.getEnd() >= 100 ||
                entity.getStart() == entity.getEnd()) {
            throw new IllegalBoardEntityConfigException(entity);
        }

        if (!entity.isPositionValid()) {
            throw new IllegalBoardEntityConfigException(entity);
        }
    }
}
