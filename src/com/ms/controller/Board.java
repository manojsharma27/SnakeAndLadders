package com.ms.controller;

import com.ms.exception.BoardEntityConflictException;
import com.ms.model.AbstractBoardEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private final int size;
    private Map<Integer, AbstractBoardEntity> entityMap;
    private Set<Integer> endPositions;

    public Board(int size) {
        this.size = size;
        entityMap = new HashMap<>();
        endPositions = new HashSet<>();
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, AbstractBoardEntity> getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map<Integer, AbstractBoardEntity> entityMap) {
        this.entityMap = entityMap;
        endPositions = new HashSet(entityMap.values());
    }

    public void addEntity(AbstractBoardEntity entity) {
        int position = entity.getStart();
        if (entityMap.containsKey(position) || endPositions.contains(position)) {
            throw new BoardEntityConflictException(entity, position);
        }

        position = entity.getEnd();
        if (entityMap.containsKey(position) || endPositions.contains(position)) {
            throw new BoardEntityConflictException(entity, position);
        }

        entityMap.put(entity.getStart(), entity);
        endPositions.add(entity.getEnd());
    }

    public boolean hasAnyBoardEntity(int position) {
        return entityMap.containsKey(position);
    }

    public AbstractBoardEntity getBoardEntity(int position) {
        return entityMap.get(position);
    }

    public void display() {
        // todo: implement
    }
}