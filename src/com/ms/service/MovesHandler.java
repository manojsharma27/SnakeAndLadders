package com.ms.service;

import com.ms.controller.Board;
import com.ms.model.AbstractBoardEntity;
import com.ms.model.Player;

import static com.ms.util.Constants.DICE_LIMIT;

public class MovesHandler {

    private Board board;

    public MovesHandler(Board board) {
        this.board = board;
    }

    public void makeMove(Player player, int move) {
        if (move == DICE_LIMIT) {
            System.out.printf("%s : Got %d!%n", player.getName(), move);
        }
        if (player.getPosition() + move > board.getSize()) {
            System.out.printf("%s : Invalid move!%n", player.getName());
            return;
        }

        int nextPosition = player.getPosition() + move;
        System.out.printf("%s : moved to %d!%n", player.getName(), nextPosition);
        if (board.hasAnyBoardEntity(nextPosition)) {
            final AbstractBoardEntity boardEntity = board.getBoardEntity(nextPosition);
            nextPosition = boardEntity.getEnd();
            System.out.printf("%s : %s%n", player.getName(), boardEntity.getEncounteredMsg());
            System.out.printf("%s : moved to %d!%n", player.getName(), nextPosition);
        }

        player.setPosition(nextPosition);
    }
}
