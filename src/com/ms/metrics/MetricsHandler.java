package com.ms.metrics;

import com.ms.model.AbstractBoardEntity;
import com.ms.model.Ladder;
import com.ms.model.Player;
import com.ms.model.Snake;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetricsHandler {

    private Map<Player, PlayerMetrics> playerMetricsMap;

    public MetricsHandler(List<Player> players) {
        init(players);
    }

    private void init(List<Player> players) {
        playerMetricsMap = new HashMap<>();
        players.forEach(p -> playerMetricsMap.put(p, new PlayerMetrics()));
    }

    public Map<Player, PlayerMetrics> getPlayerMetricsMap() {
        return playerMetricsMap;
    }

    public void reset() {
        List<Player> players = new LinkedList<>(playerMetricsMap.keySet());
        init(players);
    }

    public void incrementRolls(Player player) {
        playerMetricsMap.get(player).incrementRolls();
    }

    public void addDistance(Player player, AbstractBoardEntity entity) {
        if (entity instanceof Snake) {
            playerMetricsMap.get(player).addSlideDistance(entity);
        }
        if (entity instanceof Ladder) {
            playerMetricsMap.get(player).addClimbDistance(entity);
        }
    }

    public void captureWin(Player player) {
        playerMetricsMap.get(player).configureWinRoll();
    }


    public void incrementLuckyOrUnluckyRoll(Player player, AbstractBoardEntity entity) {
        if (entity instanceof Snake) {
            playerMetricsMap.get(player).incrementUnluckyRoll();
        }
        if (entity instanceof Ladder) {
            playerMetricsMap.get(player).incrementLuckyRoll();
        }
    }

    public void incrementLuckyRoll(Player player) {
        playerMetricsMap.get(player).incrementLuckyRoll();
    }

    public void trackLongestTurn(Player player, int move) {
        playerMetricsMap.get(player).trackLongestTurn(move);
    }
}
