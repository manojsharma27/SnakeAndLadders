package com.ms.metrics;

import com.ms.model.AbstractBoardEntity;
import com.ms.util.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerMetrics {
    private List<Integer> climbDistance;
    private List<Integer> slideDistance;
    private List<Integer> longestTurn;
    private List<Integer> currentTurn;
    private int biggestClimb;
    private int biggestSlide;
    private int rolls;
    private int luckyRolls;
    private int unluckyRolls;
    private int winRoll;

    public PlayerMetrics() {
        climbDistance = new LinkedList<>();
        slideDistance = new LinkedList<>();
        longestTurn = new ArrayList<>();
        currentTurn = new ArrayList<>();
        rolls = 0;
        biggestClimb = 0;
        biggestSlide = 0;
        luckyRolls = 0;
        unluckyRolls = 0;
        winRoll = Integer.MAX_VALUE;
    }

    public void incrementRolls() {
        rolls++;
    }

    public void configureWinRoll() {
        winRoll = rolls;
    }

    public void addClimbDistance(AbstractBoardEntity entity) {
        int dist = Math.abs(entity.getStart() - entity.getEnd());
        climbDistance.add(dist);
        biggestClimb = Math.max(biggestClimb, dist);
    }

    public void addSlideDistance(AbstractBoardEntity entity) {
        int dist = Math.abs(entity.getStart() - entity.getEnd());
        slideDistance.add(dist);
        biggestSlide = Math.max(biggestSlide, dist);
    }

    public void incrementUnluckyRoll() {
        unluckyRolls++;
    }

    public void incrementLuckyRoll() {
        luckyRolls++;
    }

    public void trackLongestTurn(int move) {
        if (currentTurn.isEmpty()) {
            currentTurn.add(move);
            longestTurn = currentTurn;
            return;
        }

        if (currentTurn.get(currentTurn.size()-1) == Constants.DICE_LIMIT) {
            currentTurn.add(move);
            if (currentTurn.size() > longestTurn.size()) {
                longestTurn = currentTurn;
            }
            return;
        }

        if(currentTurn.size() > longestTurn.size()) {
            longestTurn = currentTurn;
            currentTurn = new LinkedList<>();
        } else if (longestTurn != currentTurn){
            currentTurn.clear();
        } else {
            currentTurn = new LinkedList<>();
        }
        currentTurn.add(move);
    }

    public int getWinRoll() {
        return winRoll;
    }

    public List<Integer> getClimbDistance() {
        return climbDistance;
    }

    public List<Integer> getSlideDistance() {
        return slideDistance;
    }

    public int getBiggestClimb() {
        return biggestClimb;
    }

    public int getBiggestSlide() {
        return biggestSlide;
    }

    public int getLuckyRolls() {
        return luckyRolls;
    }

    public int getUnluckyRolls() {
        return unluckyRolls;
    }

    public List<Integer> getLongestTurn() {
        return longestTurn;
    }
}