package ru.vsu.garaba_v_i.model.field;


import ru.vsu.garaba_v_i.model.Direction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cell
{
    private final Map<Direction, List<Cell>> neighboringCells = new LinkedHashMap<>();

    public Cell()
    {

    }

    public void setUpCells(List<Cell> upNeighbors) {
        this.neighboringCells.put(Direction.UP, upNeighbors);
    }

    public void setDownCells(List<Cell> downNeighbors) {
        this.neighboringCells.put(Direction.DOWN, downNeighbors);
    }

    public List<Cell> getUpNeighboringCells() {
        return neighboringCells.get(Direction.UP);
    }

    public List<Cell> getDownNeighboringCells() {
        if(neighboringCells.get(Direction.DOWN) == null) {
            return null;
        }
        return neighboringCells.get(Direction.DOWN);
    }
}
