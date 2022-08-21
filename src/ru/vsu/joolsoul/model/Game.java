package ru.vsu.joolsoul.model;

import ru.vsu.joolsoul.model.field.Cell;
import ru.vsu.joolsoul.model.field.CellLetter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Game {

    private Queue<Player> playersQueue;

    private Map<Player, List<Checker>> playerCheckersMap = new LinkedHashMap<>();

    private Map<CellLetter, List<Cell>> gameField = new LinkedHashMap<>();

    private Map<Checker, Cell> checkerPositionMap = new LinkedHashMap<>();

    private Map<Cell, Checker> cellWithCheckerMap = new LinkedHashMap<>();

    private Direction currentDirection;

    private boolean isInGame = true;

    private Player winner;


    public Game(Queue<Player> playersQueue, Direction direction) {
        this.playersQueue = playersQueue;
        this.currentDirection = direction;
    }

    public Queue<Player> getPlayersQueue() {
        return playersQueue;
    }

    public void setPlayersQueue(Queue<Player> playersQueue) {
        this.playersQueue = playersQueue;
    }

    public Map<Player, List<Checker>> getPlayerCheckersMap() {
        return playerCheckersMap;
    }

    public void setPlayerCheckersMap(Map<Player, List<Checker>> playerCheckersMap) {
        this.playerCheckersMap = playerCheckersMap;
    }

    public Map<Checker, Cell> getCheckerPositionMap() {
        return checkerPositionMap;
    }

    public void setCheckerPositionMap(Map<Checker, Cell> checkerPositionMap) {
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

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setNotInGame() {
        isInGame = false;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
