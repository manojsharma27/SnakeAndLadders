package com.ms.service;

import com.ms.controller.Board;
import com.ms.metrics.MetricsHandler;
import com.ms.model.AbstractBoardEntity;
import com.ms.model.Player;
import com.ms.model.Snake;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovesHandlerTest {

    @InjectMocks
    private MovesHandler movesHandler;

    @Mock
    private MetricsHandler metricsHandler;

    @Mock
    private Board board;

    @Mock
    private Player player;

    private Snake snake;

    @Before
    public void setup() {
        doNothing().when(metricsHandler).incrementRolls(any(Player.class));
        doNothing().when(metricsHandler).trackLongestTurn(any(Player.class), anyInt());
        doNothing().when(metricsHandler).addDistance(any(Player.class), any(AbstractBoardEntity.class));
        doNothing().when(metricsHandler).incrementLuckyOrUnluckyRoll(any(Player.class), any(AbstractBoardEntity.class));
        doNothing().when(metricsHandler).incrementLuckyRoll(any(Player.class));
        when(board.getSize()).thenReturn(100);
        snake = new Snake(30, 10);
    }

    @Test
    public void shouldNotMakeAnyMoveWhenNextPositionIsOutOfBoard() {
        when(player.getPosition()).thenReturn(97);
        movesHandler.makeMove(player, 5);
        verify(metricsHandler, times(1)).incrementRolls(any(Player.class));
        verify(metricsHandler, times(1)).trackLongestTurn(any(Player.class), eq(5));
        verify(player, never()).setPosition(anyInt());
    }

    @Test
    public void shouldUpdatePositionIfNoAnyEntityCrossed() {
        when(board.hasAnyBoardEntity(anyInt())).thenReturn(false);
        when(player.getPosition()).thenReturn(9);
        movesHandler.makeMove(player, 5);
        verify(metricsHandler, times(1)).incrementRolls(any(Player.class));
        verify(metricsHandler, times(1)).trackLongestTurn(any(Player.class), eq(5));
        verify(player).setPosition(eq(9+5));
        verify(metricsHandler, never()).addDistance(any(Player.class), any(AbstractBoardEntity.class));
    }

    @Test
    public void shouldUpdatePositionToEntityEndWhenAnyEntityEncountered() {
        when(board.hasAnyBoardEntity(anyInt())).thenAnswer((Answer<Boolean>) invocationOnMock -> true);
        when(board.getBoardEntity(anyInt())).thenAnswer((Answer<Snake>) invocationOnMock -> snake);
        when(player.getPosition()).thenReturn(25);
        movesHandler.makeMove(player, 5);
        verify(metricsHandler, times(1)).incrementRolls(any(Player.class));
        verify(metricsHandler, times(1)).trackLongestTurn(any(Player.class), eq(5));
        verify(player).setPosition(eq(10));
        verify(metricsHandler).addDistance(any(Player.class), any(AbstractBoardEntity.class));
    }

    @Test
    public void shouldUpdatePositionAndIncrementLuckyRollWhenSnakeEntityMissed() {
        when(board.hasAnyBoardEntity(anyInt())).thenReturn(false).thenReturn(false).thenReturn(true);
        when(board.getBoardEntity(anyInt())).thenReturn(snake);
        when(player.getPosition()).thenReturn(25);
        movesHandler.makeMove(player, 4);
        verify(metricsHandler, times(1)).incrementRolls(any(Player.class));
        verify(metricsHandler, times(1)).trackLongestTurn(any(Player.class), eq(4));
        verify(player).setPosition(eq(29));
        verify(metricsHandler, never()).addDistance(any(Player.class), any(AbstractBoardEntity.class));
        verify(metricsHandler).incrementLuckyRoll(any(Player.class));
    }

}