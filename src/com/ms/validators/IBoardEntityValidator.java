package com.ms.validators;

import com.ms.controller.Board;
import com.ms.model.AbstractBoardEntity;

public interface IBoardEntityValidator {

    void validate(Board board, AbstractBoardEntity boardEntity);

}