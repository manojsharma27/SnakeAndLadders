package com.ms.model;

public class Snake extends AbstractBoardEntity {

    public Snake(int start, int end) {
        super(start, end);
    }

    @Override
    public String getEncounteredMsg() {
        return "Ohh shit! Its a snake ~~^";
    }

    @Override
    public boolean isPositionValid() {
        return getStart() > getEnd();
    }

    @Override
    public String toString() {
        return "Snake{" + getStart() + "," + getEnd() + "}";
    }
}
