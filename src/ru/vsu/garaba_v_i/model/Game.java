package ru.vsu.garaba_v_i.model;

import ru.vsu.garaba_v_i.model.field.Cell;
import ru.vsu.garaba_v_i.model.field.CellLetter;
import ru.vsu.garaba_v_i.service.FieldService;

import java.util.*;

public class Game
{
    private Queue<Player> playersQueue;

    private Map<Player, List<Checker>> playerCheckersMap = new LinkedHashMap<>();

    private Map<CellLetter, List<Cell>> gameField = new LinkedHashMap<>();

    private Map<Checker, Cell> checkerPositionMap = new LinkedHashMap<>();

    private Map<Cell, Checker> cellWithCheckerMap = new LinkedHashMap<>();

    private final FieldService fieldService = new FieldService();

    private Direction currentDirection;


    public Game(Queue<Player> playersQueue, Direction direction)
    {
        this.playersQueue = playersQueue;
        this.currentDirection = direction;
    }

    public Queue<Player> getPlayersQueue()
    {
        return playersQueue;
    }

    public void setPlayersQueue(Queue<Player> playersQueue)
    {
        this.playersQueue = playersQueue;
    }

    public Map<Player, List<Checker>> getPlayerCheckersMap()
    {
        return playerCheckersMap;
    }

    public void setPlayerCheckersMap(Map<Player, List<Checker>> playerCheckersMap)
    {
        this.playerCheckersMap = playerCheckersMap;
    }

    public Map<Checker, Cell> getCheckerPositionMap()
    {
        return checkerPositionMap;
    }

    public void setCheckerPositionMap(Map<Checker, Cell> checkerPositionMap)
    {
        this.checkerPositionMap = checkerPositionMap;
    }

    public Map<CellLetter, List<Cell>> getGameField() {
        return gameField;
    }

    public void setGameField(Map<CellLetter, List<Cell>> gameField) {
        this.gameField = gameField;
    }

    public Map<Cell, Checker> getCellWithCheckerMap() {
        return cellWithCheckerMap;
    }

    public void setCellWithCheckerMap(Map<Cell, Checker> cellWithCheckerMap) {
        this.cellWithCheckerMap = cellWithCheckerMap;
    }

    public FieldService getFieldService()
    {
        return fieldService;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
}
