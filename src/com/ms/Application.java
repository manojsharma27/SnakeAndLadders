package com.ms;

import com.ms.controller.Board;
import com.ms.controller.Game;
import com.ms.metrics.GameMetrics;
import com.ms.metrics.MetricsHandler;
import com.ms.model.Ladder;
import com.ms.model.Player;
import com.ms.model.Snake;
import com.ms.service.DiceService;
import com.ms.service.MovesHandler;
import com.ms.util.LogUtil;
import com.ms.validators.BoardEntityConflictValidator;
import com.ms.validators.BoardEntityStartEndPositionValidator;
import com.ms.validators.IBoardEntityValidator;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        int totalGames = 10;
        int boardSize = 100;

        List<IBoardEntityValidator> boardEntityValidators = Arrays.asList(
                new BoardEntityStartEndPositionValidator(),
                new BoardEntityConflictValidator()
        );

        Board board = new Board(boardSize, boardEntityValidators);
        addSnakes(board);
        addLadders(board);

        DiceService diceService = new DiceService();
        List<Player> players = Arrays.asList(new Player("Manoj"), new Player("Avinash"), new Player("Sahaj"));
        MetricsHandler metricsHandler = new MetricsHandler(players);
        MovesHandler movesHandler = new MovesHandler(board, metricsHandler);
        GameMetrics gameMetrics = new GameMetrics(players);

        for (int i = 0; i < totalGames; i++) {
            System.out.println(">>>> Starting game : " + (i+1));
            Game game = new Game(boardSize, players, movesHandler, diceService, metricsHandler);
            game.start();
            gameMetrics.addMetricsMap("Game" + i, metricsHandler.getPlayerMetricsMap());
            metricsHandler.reset();
        }

        logMetrics(gameMetrics);
    }


    private static void addLadders(Board board) {
        board.addEntity(new Ladder(4, 25));
        board.addEntity(new Ladder(13, 46));
        board.addEntity(new Ladder(33, 49));
        board.addEntity(new Ladder(42, 63));
        board.addEntity(new Ladder(50, 69));
        board.addEntity(new Ladder(62, 81));
        board.addEntity(new Ladder(74, 92));
    }

    private static void addSnakes(Board board) {
        board.addEntity(new Snake(99, 41));
        board.addEntity(new Snake(89, 53));
        board.addEntity(new Snake(76, 58));
        board.addEntity(new Snake(66, 45));
        board.addEntity(new Snake(54, 31));
        board.addEntity(new Snake(43, 18));
        board.addEntity(new Snake(40, 3));
        board.addEntity(new Snake(27, 5));
    }

    private static void logMetrics(GameMetrics gameMetrics) {
        LogUtil.log("%nMetrics%n-------%n");
        LogUtil.log("Total Wins: %n%s%n%n", gameMetrics.getWinsMetric());
        LogUtil.log("Rolls to win: %n%s%n%n", gameMetrics.getRollsToWinMetrics());
        LogUtil.log("Climbs metric: %n%s%n%n", gameMetrics.getClimbsMetrics());
        LogUtil.log("Slides metric: %n%s%n%n", gameMetrics.getSlidesMetrics());
        LogUtil.log("Biggest Climb metric: %n%s%n%n", gameMetrics.getBiggestClimbMetric());
        LogUtil.log("Biggest Slide metric: %n%s%n%n", gameMetrics.getBiggestSlideMetric());
        LogUtil.log("Longest Turn metric: %n%s%n%n", gameMetrics.getLongestTurnMetric());
        LogUtil.log("Total Unlucky Rolls metric: %n%s%n%n", gameMetrics.getUnluckyRollsMetric());
        LogUtil.log("Total Lucky Rolls metric: %n%s%n%n", gameMetrics.getLuckyRollsMetric());
    }
}
