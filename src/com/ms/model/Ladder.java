package com.ms.model;

public class Ladder extends AbstractBoardEntity {

    public Ladder(int start, int end) {
        super(start, end);
    }

    @Override
    public String getEncounteredMsg() {
        return "Yay! Got a Ladder #.";
    }
}
