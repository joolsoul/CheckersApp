package ru.vsu.garaba_v_i.model.field;


import ru.vsu.garaba_v_i.model.Direction;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Cell
{
    private final Map<Direction, Cell> neighboringCells = new LinkedHashMap<>();
    private int numbersUpNeighbors = 0;
    private int numbersDownNeighbors = 0;

    public Cell()
    {

    }

    public void setUpLeftNeighbor(Cell upLeftNeighbor) {
        this.neighboringCells.put(Direction.UP_LEFT, upLeftNeighbor);
        numbersUpNeighbors++;
    }

    public void setUpRightNeighbor(Cell upRightNeighbor) {
        this.neighboringCells.put(Direction.UP_RIGHT, upRightNeighbor);
        numbersUpNeighbors++;
    }

    public void setDownLeftNeighbor(Cell downLeftNeighbor) {
        this.neighboringCells.put(Direction.DOWN_LEFT, downLeftNeighbor);
        numbersDownNeighbors++;
    }

    public void setDownRightNeighbor(Cell downRightNeighbor) {
        this.neighboringCells.put(Direction.DOWN_RIGHT, downRightNeighbor);
        numbersDownNeighbors++;
    }


    public Cell getUpLeftNeighbor() {
        return this.neighboringCells.get(Direction.UP_LEFT);
    }

    public Cell getUpRightNeighbor() {
        return this.neighboringCells.get(Direction.UP_RIGHT);
    }

    public Cell getDownLeftNeighbor() {
        return this.neighboringCells.get(Direction.DOWN_LEFT);
    }

    public Cell getDownRightNeighbor() {
        return this.neighboringCells.get(Direction.DOWN_RIGHT);
    }

    public List<Cell> getUpNeighboringCells() {
        List<Cell> upNeighboringCells = new LinkedList<>();
        if(hasUpLeftNeighbor())
        upNeighboringCells.add(getUpLeftNeighbor());
        if(hasUpRightNeighbor())
        upNeighboringCells.add(getUpRightNeighbor());
        return upNeighboringCells;
    }

    public List<Cell> getDownNeighboringCells() {
        List<Cell> downNeighboringCells = new LinkedList<>();
        if(hasDownLeftNeighbor())
        downNeighboringCells.add(getDownLeftNeighbor());
        if(hasDownRightNeighbor())
        downNeighboringCells.add(getDownRightNeighbor());
        return downNeighboringCells;
    }

    public int getNumbersUpNeighbors() {
        return numbersUpNeighbors;
    }

    public int getNumbersDownNeighbors() {
        return numbersDownNeighbors;
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
