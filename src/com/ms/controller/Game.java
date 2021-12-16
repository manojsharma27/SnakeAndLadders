package com.ms.controller;

import com.ms.model.Player;
import com.ms.service.DiceService;
import com.ms.service.MovesHandler;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.ms.util.Constants.DICE_LIMIT;

public class Game {

    private static final String WIN_MSG = "Congrats! %s. You won! '(^-^)'";

    private Deque<Player> players;
    private Queue<Player> winners;
    private MovesHandler movesHandler;
    private DiceService diceService;
    private final int winPosition;

    public Game(int size, List<Player> players, MovesHandler movesHandler, DiceService diceService) {
        this.players = new LinkedList<>();
        this.players.addAll(players);
        this.winners = new LinkedList<>();
        this.movesHandler = movesHandler;
        this.diceService = diceService;
        this.winPosition = size;
    }

    public void start() {
        while (players.size() > 1) {
            Player player = players.poll();
            int move = diceService.roll(DICE_LIMIT);
            movesHandler.makeMove(player, move);
            if (haveWon(player)) {
                System.out.printf(WIN_MSG + "%n", player.getName());
                winners.offer(player);
                break;
            }

            if (move == DICE_LIMIT) {
                players.offerFirst(player);
            } else {
                players.offer(player);
            }
        }
    }

    private boolean haveWon(Player player) {
        return winPosition == player.getPosition();
    }

}
