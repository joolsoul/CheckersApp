package ru.vsu.joolsoul.model.field;


import ru.vsu.joolsoul.model.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cell {

    private final Map<Direction, Cell> neighboringCells = new LinkedHashMap<>();

    public Cell() {

    }

    public Cell getUpLeftNeighbor() {
        return this.neighboringCells.get(Direction.UP_LEFT);
    }

    public void setUpLeftNeighbor(Cell upLeftNeighbor) {
        this.neighboringCells.put(Direction.UP_LEFT, upLeftNeighbor);
    }

    public Cell getUpRightNeighbor() {
        return this.neighboringCells.get(Direction.UP_RIGHT);
    }

    public void setUpRightNeighbor(Cell upRightNeighbor) {
        this.neighboringCells.put(Direction.UP_RIGHT, upRightNeighbor);
    }

    public Cell getDownLeftNeighbor() {
        return this.neighboringCells.get(Direction.DOWN_LEFT);
    }

    public void setDownLeftNeighbor(Cell downLeftNeighbor) {
        this.neighboringCells.put(Direction.DOWN_LEFT, downLeftNeighbor);
    }

    public Cell getDownRightNeighbor() {
        return this.neighboringCells.get(Direction.DOWN_RIGHT);
    }

    public void setDownRightNeighbor(Cell downRightNeighbor) {
        this.neighboringCells.put(Direction.DOWN_RIGHT, downRightNeighbor);
    }

    public boolean hasUpLeftNeighbor() {
        return neighboringCells.containsKey(Direction.UP_LEFT);
    }

    public boolean hasUpRightNeighbor() {
        return neighboringCells.containsKey(Direction.UP_RIGHT);
    }

    public boolean hasDownLeftNeighbor() {
        return neighboringCells.containsKey(Direction.DOWN_LEFT);
    }

    public boolean hasDownRightNeighbor() {
        return neighboringCells.containsKey(Direction.DOWN_RIGHT);
    }

    public boolean hasNeighbor(Direction direction) {
        return neighboringCells.containsKey(direction);
    }

    public Cell getNeighbor(Direction direction) {
        return neighboringCells.get(direction);
    }

}
