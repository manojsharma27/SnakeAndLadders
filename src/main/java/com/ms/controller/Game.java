package com.ms.controller;

import com.ms.metrics.MetricsHandler;
import com.ms.model.Player;
import com.ms.service.DiceService;
import com.ms.service.MovesHandler;
import com.ms.util.LogUtil;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.ms.util.Constants.DICE_LIMIT;

public class Game {

    private static final String WIN_MSG = "Congrats! %s. You won! '(^-^)'";

    private Deque<Player> players;
    private MovesHandler movesHandler;
    private DiceService diceService;
    private MetricsHandler metricsHandler;
    private final int winPosition;

    public Game(int size, List<Player> players, MovesHandler movesHandler, DiceService diceService, MetricsHandler metricsHandler) {
        this.movesHandler = movesHandler;
        this.diceService = diceService;
        this.metricsHandler = metricsHandler;
        this.winPosition = size;
        initPlayers(players);
    }

    private void initPlayers(List<Player> inputPlayers) {
        inputPlayers.forEach(p -> p.setPosition(0));
        this.players = new LinkedList<>();
        this.players.addAll(inputPlayers);
    }

    public void start() {
        while (players.size() > 1) {
            Player player = players.poll();
            int move = diceService.roll(DICE_LIMIT);
            movesHandler.makeMove(player, move);
            if (haveWon(player)) {
                LogUtil.log(WIN_MSG + "%n", player.getName());
                metricsHandler.captureWin(player);
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
