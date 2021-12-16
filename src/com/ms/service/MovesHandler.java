package com.ms.service;

import com.ms.controller.Board;
import com.ms.metrics.MetricsHandler;
import com.ms.model.AbstractBoardEntity;
import com.ms.model.Player;
import com.ms.model.Snake;
import com.ms.util.LogUtil;

import static com.ms.util.Constants.DICE_LIMIT;

public class MovesHandler {

    private static final int[] SNAKE_MISS_STEPS = new int[]{-2, -1, 1, 2};
    private final Board board;
    private final MetricsHandler metricsHandler;

    public MovesHandler(Board board, MetricsHandler metricsHandler) {
        this.board = board;
        this.metricsHandler = metricsHandler;
    }

    public void makeMove(Player player, int move) {
        metricsHandler.incrementRolls(player);
        metricsHandler.trackLongestTurn(player, move);
        if (move == DICE_LIMIT) {
            LogUtil.logConditional("%s : Got %d!%n", player.getName(), move);
        }
        if (player.getPosition() + move > board.getSize()) {
            LogUtil.logConditional("%s : Invalid move!%n", player.getName());
            return;
        }

        int nextPosition = player.getPosition() + move;
        LogUtil.logConditional("%s : moved to %d!%n", player.getName(), nextPosition);

        if (board.hasAnyBoardEntity(nextPosition)) {
            final AbstractBoardEntity boardEntity = board.getBoardEntity(nextPosition);
            nextPosition = boardEntity.getEnd();
            LogUtil.logConditional("%s : %s%n", player.getName(), boardEntity.getEncounteredMsg());
            LogUtil.logConditional("%s : moved to %d!%n", player.getName(), nextPosition);
            metricsHandler.addDistance(player, boardEntity);
            metricsHandler.incrementLuckyOrUnluckyRoll(player, boardEntity);
        }
        populateLuckyRollMetric(player, move);
        player.setPosition(nextPosition);
    }

    private void populateLuckyRollMetric(Player player, int move) {
        int nextPos = player.getPosition() + move;
        if (board.hasAnyBoardEntity(nextPos)) {
            return;
        }
        if (nextPos == board.getSize() || checkCloseSnakeEncounter(nextPos)) {
            metricsHandler.incrementLuckyRoll(player);
        }
    }

    private boolean checkCloseSnakeEncounter(int position) {
        for (int step : SNAKE_MISS_STEPS) {
            int x = position + step;
            if (board.hasAnyBoardEntity(x) && board.getBoardEntity(x) instanceof Snake) {
                return true;
            }
        }
        return false;
    }
}
