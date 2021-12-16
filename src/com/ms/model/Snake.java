package com.ms.model;

public class Snake extends AbstractBoardEntity {

    public Snake(int start, int end) {
        super(start, end);
    }

    @Override
    public String getEncounteredMsg() {
        return "Ohh shit! Its a snake ~~^";
    }
}
