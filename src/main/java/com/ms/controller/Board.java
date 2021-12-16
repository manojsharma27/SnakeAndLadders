package com.ms.controller;

import com.ms.model.AbstractBoardEntity;
import com.ms.validators.IBoardEntityValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    private final int size;
    private final Map<Integer, AbstractBoardEntity> entityMap;
    private final Set<Integer> endPositions;
    private final List<IBoardEntityValidator> boardEntityValidators;

    public Board(int size, List<IBoardEntityValidator> boardEntityValidators) {
        this.size = size;
        this.boardEntityValidators = boardEntityValidators;
        entityMap = new HashMap<>();
        endPositions = new HashSet<>();
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, AbstractBoardEntity> getEntityMap() {
        return entityMap;
    }

    public void addEntity(AbstractBoardEntity entity) {
        validate(entity);
        entityMap.put(entity.getStart(), entity);
        endPositions.add(entity.getEnd());
    }

    public boolean hasAnyBoardEntity(int position) {
        return entityMap.containsKey(position);
    }

    public AbstractBoardEntity getBoardEntity(int position) {
        return entityMap.get(position);
    }

    public Set<Integer> getEndPositions() {
        return endPositions;
    }

    private void validate(AbstractBoardEntity entity) {
        for (IBoardEntityValidator validator : boardEntityValidators) {
            validator.validate(this, entity);
        }
    }
}