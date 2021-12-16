package com.ms.controller;

import com.ms.metrics.MetricsHandler;
import com.ms.model.Player;
import com.ms.service.DiceService;
import com.ms.service.MovesHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    private static final int SIZE = 10;

    @Mock
    private MovesHandler movesHandler;

    @Mock
    private DiceService diceService;

    @Mock
    private MetricsHandler metricsHandler;

    @Mock
    private Player playerA;

    @Mock
    private Player playerB;

    private Game game;
    private List<Player> players;

    @Before
    public void setup() {
        when(playerA.getName()).thenReturn("PlayerA");
        when(playerA.getName()).thenReturn("PlayerB");
        players = Arrays.asList(playerA, playerB);
        doNothing().when(movesHandler).makeMove(any(Player.class), anyInt());
        when(diceService.roll(anyInt())).thenReturn(1);
        doNothing().when(metricsHandler).captureWin(any(Player.class));
        game = new Game(SIZE, players, movesHandler, diceService, metricsHandler);
    }

    @Test
    public void shouldSetAllPlayersInitialPositionToZero() {
        for (Player p : players) {
            verify(p).setPosition(eq(0));
        }
    }

    @Test
    public void playerShouldBeAbleToRollDice() {
        when(diceService.roll(anyInt())).thenReturn(3);
        when(playerA.getPosition()).thenReturn(SIZE);
        game.start();
        verify(diceService).roll(anyInt());
    }

    @Test
    public void playerShouldBeAbleToWin() {
        when(diceService.roll(anyInt())).thenReturn(3);
        when(playerA.getPosition()).thenReturn(5).thenReturn(7).thenReturn(9);
        when(playerB.getPosition()).thenReturn(4).thenReturn(SIZE);
        game.start();
        verify(diceService, atLeastOnce()).roll(anyInt());
        verify(metricsHandler).captureWin(eq(playerB));
    }

    @Test
    public void playerShouldGetAlternateTurns() {
        when(diceService.roll(anyInt())).thenReturn(3);
        when(playerA.getPosition()).thenReturn(5).thenReturn(7).thenReturn(9);
        when(playerB.getPosition()).thenReturn(4).thenReturn(8).thenReturn(SIZE);
        game.start();
        verify(diceService, atLeastOnce()).roll(anyInt());
        verify(movesHandler, times(3)).makeMove(eq(playerA), anyInt());
        verify(movesHandler, times(3)).makeMove(eq(playerB), anyInt());
        verify(metricsHandler).captureWin(eq(playerB));
    }

    @Test
    public void playerShouldGetAnotherChanceIfDiceRollsItsLimit() {
        when(diceService.roll(anyInt())).thenReturn(6);
        when(playerA.getPosition()).thenReturn(5).thenReturn(7).thenReturn(SIZE);
        when(playerB.getPosition()).thenReturn(4);
        game.start();
        verify(diceService, atLeastOnce()).roll(anyInt());
        verify(movesHandler, times(3)).makeMove(eq(playerA), anyInt());
        verify(movesHandler, never()).makeMove(eq(playerB), anyInt());
        verify(metricsHandler).captureWin(eq(playerA));
    }

}