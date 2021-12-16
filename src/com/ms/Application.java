package com.ms;

import com.ms.controller.Board;
import com.ms.controller.Game;
import com.ms.model.Ladder;
import com.ms.model.Player;
import com.ms.model.Snake;
import com.ms.service.DiceService;
import com.ms.service.MovesHandler;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        DiceService diceService = new DiceService();
        Board board = new Board(100);
        addSnakes(board);
        addLadders(board);
        MovesHandler movesHandler = new MovesHandler(board);
        List<Player> players = Arrays.asList(new Player("Manoj"), new Player("Avinash"), new Player("Sahaj"));
        Game game = new Game(100, players, movesHandler, diceService);
        game.start();
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
}
