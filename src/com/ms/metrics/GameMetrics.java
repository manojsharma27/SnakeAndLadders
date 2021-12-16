package com.ms.metrics;

import com.ms.model.Player;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class GameMetrics {

    private Map<String, Map<Player, PlayerMetrics>> gameMetricsMap;
    private List<Player> players;

    ToIntFunction<PlayerMetrics> winRollFunction = PlayerMetrics::getWinRoll;
    Function<PlayerMetrics, List<Integer>> climbDistFunction = PlayerMetrics::getClimbDistance;
    Function<PlayerMetrics, List<Integer>> slideDistFunction = PlayerMetrics::getSlideDistance;
    Function<PlayerMetrics, Integer> biggestClimbFunc = PlayerMetrics::getBiggestClimb;
    Function<PlayerMetrics, Integer> biggestSlideFunc = PlayerMetrics::getBiggestSlide;
    Function<PlayerMetrics, Integer> luckyRollsFunc = PlayerMetrics::getLuckyRolls;
    Function<PlayerMetrics, Integer> unluckyRollsFunc = PlayerMetrics::getUnluckyRolls;

    public GameMetrics(List<Player> players) {
        this.players = players;
        gameMetricsMap = new HashMap<>();
    }

    public void addMetricsMap(String game, Map<Player, PlayerMetrics> metricsMap) {
        gameMetricsMap.put(game, metricsMap);
    }

    public Map<Player, MinAvgMaxMetric> getRollsToWinMetrics() {
        Map<Player, MinAvgMaxMetric> map = new HashMap<>();
        for (Player player : players) {
            Stream<PlayerMetrics> playerMetricsStream = gameMetricsMap.values().stream().map(entry -> entry.get(player));
            IntSummaryStatistics stats = playerMetricsStream.mapToInt(winRollFunction).filter(x -> x != Integer.MAX_VALUE).summaryStatistics();
            MinAvgMaxMetric minAvgMaxMetric = new MinAvgMaxMetric(stats.getMin(), stats.getAverage(), stats.getMax());
            map.put(player, minAvgMaxMetric);
        }
        return map;
    }

    public Map<Player, Integer> getWinsMetric() {
        Map<Player, Integer> map = new HashMap<>();
        for (Player player : players) {
            Stream<PlayerMetrics> playerMetricsStream = gameMetricsMap.values().stream().map(entry -> entry.get(player));
            int wins = (int) playerMetricsStream.mapToInt(winRollFunction).filter(x -> x != Integer.MAX_VALUE).count();
            map.put(player, wins);
        }
        return map;
    }

    private Map<Player, MinAvgMaxMetric> extractMinAvgMaxMetric(Function<PlayerMetrics, List<Integer>> distFunction) {
        Map<Player, MinAvgMaxMetric> map = new HashMap<>();
        for (Player player : players) {
            Stream<PlayerMetrics> playerMetricsStream = gameMetricsMap.values().stream().map(entry -> entry.get(player));
            IntSummaryStatistics stats = playerMetricsStream.map(distFunction).flatMap(Collection::stream).mapToInt(x -> x).summaryStatistics();
            MinAvgMaxMetric minAvgMaxMetric = new MinAvgMaxMetric(stats.getMin(), stats.getAverage(), stats.getMax());
            map.put(player, minAvgMaxMetric);
        }
        return map;
    }

    public Map<Player, MinAvgMaxMetric> getClimbsMetrics() {
        return extractMinAvgMaxMetric(climbDistFunction);
    }

    public Map<Player, MinAvgMaxMetric> getSlidesMetrics() {
        return extractMinAvgMaxMetric(slideDistFunction);
    }

    private Map<Player, Integer> extractIndividualMetric(Function<PlayerMetrics, Integer> metricFunc) {
        Map<Player, Integer> map = new HashMap<>();
        for (Player player : players) {
            Stream<PlayerMetrics> playerMetricsStream = gameMetricsMap.values().stream().map(entry -> entry.get(player));
            int max = playerMetricsStream.map(metricFunc).mapToInt(x -> x).max().getAsInt();
            map.put(player, max);
        }
        return map;
    }

    public Map<Player, Integer> getBiggestClimbMetric() {
        return extractIndividualMetric(biggestClimbFunc);
    }

    public Map<Player, Integer> getBiggestSlideMetric() {
        return extractIndividualMetric(biggestSlideFunc);
    }

    public Map<Player, Integer> getUnluckyRollsMetric() {
        return extractIndividualMetric(unluckyRollsFunc);
    }

    public Map<Player, Integer> getLuckyRollsMetric() {
        return extractIndividualMetric(luckyRollsFunc);
    }

    public Map<Player, List<Integer>> getLongestTurnMetric() {
        Map<Player, List<Integer>> map = new HashMap<>();
        for (Player player : players) {
            Stream<PlayerMetrics> playerMetricsStream = gameMetricsMap.values().stream().map(entry -> entry.get(player));
            List<Integer> longestTurn = playerMetricsStream.map(PlayerMetrics::getLongestTurn).max(Comparator.comparingInt(List::size)).get();
            map.put(player, longestTurn);
        }
        return map;
    }
}
