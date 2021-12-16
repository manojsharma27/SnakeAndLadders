package com.ms.exception;

import com.ms.model.AbstractBoardEntity;

public class IllegalBoardEntityConfigException extends GameException {
    private static final String MSG = "Invalid entity configuration for %s. Strictly required: (For Ladder, start < end. For Snake, start > end)";

    public IllegalBoardEntityConfigException(AbstractBoardEntity entity) {
        super(String.format(MSG, entity));
    }
}
