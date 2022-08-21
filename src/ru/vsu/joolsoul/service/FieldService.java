package ru.vsu.joolsoul.service;

import ru.vsu.joolsoul.model.Checker;
import ru.vsu.joolsoul.model.Direction;
import ru.vsu.joolsoul.model.Game;
import ru.vsu.joolsoul.model.Player;
import ru.vsu.joolsoul.model.field.Cell;
import ru.vsu.joolsoul.model.field.CellLetter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FieldService
{
    public void createGameField(int width, Map<CellLetter, List<Cell>> gameField)
    {
        initCells(width, gameField);
        createCellsLinks(gameField);
    }
    
    private void initCells(int width, Map<CellLetter, List<Cell>> gameField){
        for(CellLetter cellLetter : CellLetter.values()){
            List<Cell> currentCellsList = new LinkedList<>();
            for (int i = 0; i < width / 2; i++) {
                currentCellsList.add(new Cell());
            }
            gameField.put(cellLetter, currentCellsList);
        }
    }

    private void createCellsLinks(Map<CellLetter, List<Cell>> gameField){
        boolean isDoubleNeighbors = true;
        for(int cellLetterNumber = 0; cellLetterNumber < CellLetter.values().length; cellLetterNumber++, isDoubleNeighbors = !isDoubleNeighbors) {
            List<Cell> currentCellsList = gameField.get(CellLetter.values()[cellLetterNumber]);

            for (int cellNumber = 0; cellNumber < currentCellsList.size(); cellNumber++) {
                Cell currentCell = currentCellsList.get(cellNumber);
                Cell upLeftNeighbor;
                Cell upRightNeighbor;
                Cell downLeftNeighbor;
                Cell downRightNeighbor;

                if(cellLetterNumber == 0) {
                    if (cellNumber != currentCellsList.size() - 1) {
                        upRightNeighbor = getUpNeighbor(cellLetterNumber, cellNumber + 1, gameField);
                        currentCell.setUpRightNeighbor(upRightNeighbor);
                    }
                    upLeftNeighbor = getUpNeighbor(cellLetterNumber, cellNumber, gameField);
                    currentCell.setUpLeftNeighbor(upLeftNeighbor);
                }
                if(cellLetterNumber == 7) {
                    if(cellNumber != 0) {
                        downLeftNeighbor = getDownNeighbor(cellLetterNumber, cellNumber - 1, gameField);
                        currentCell.setDownLeftNeighbor(downLeftNeighbor);
                    }
                    downRightNeighbor = getDownNeighbor(cellLetterNumber, cellNumber, gameField);
                    currentCell.setDownRightNeighbor(downRightNeighbor);
                }
                if(cellLetterNumber != 0 && cellLetterNumber != 7 ) {
                    if(!isDoubleNeighbors) {
                        if(cellNumber != 0) {
                            upLeftNeighbor = getUpNeighbor(cellLetterNumber, cellNumber - 1, gameField);
                            downLeftNeighbor = getDownNeighbor(cellLetterNumber, cellNumber - 1, gameField);
                            currentCell.setUpLeftNeighbor(upLeftNeighbor);
                            currentCell.setDownLeftNeighbor(downLeftNeighbor);
                        }
                        upRightNeighbor = getUpNeighbor(cellLetterNumber, cellNumber, gameField);
                        downRightNeighbor = getDownNeighbor(cellLetterNumber, cellNumber, gameField);
                        currentCell.setUpRightNeighbor(upRightNeighbor);
                        currentCell.setDownRightNeighbor(downRightNeighbor);
                    }
                    if(isDoubleNeighbors) {
                        if(cellNumber != currentCellsList.size() - 1) {
                            upRightNeighbor = getUpNeighbor(cellLetterNumber, cellNumber + 1, gameField);
                            downRightNeighbor = getDownNeighbor(cellLetterNumber, cellNumber + 1, gameField);
                            currentCell.setUpRightNeighbor(upRightNeighbor);
                            currentCell.setDownRightNeighbor(downRightNeighbor);
                        }
                        upLeftNeighbor = getUpNeighbor(cellLetterNumber, cellNumber, gameField);
                        downLeftNeighbor = getDownNeighbor(cellLetterNumber, cellNumber, gameField);
                        currentCell.setUpLeftNeighbor(upLeftNeighbor);
                        currentCell.setDownLeftNeighbor(downLeftNeighbor);
                    }
                }
            }
        }
    }

    private Cell getUpNeighbor(int currentCellLetterNumber, int index, Map<CellLetter, List<Cell>> gameField) {
        CellLetter upNeighborLetter = CellLetter.values()[currentCellLetterNumber + 1];
        return gameField.get(upNeighborLetter).get(index);
    }

    private Cell getDownNeighbor(int currentCellLetterNumber, int index, Map<CellLetter, List<Cell>> gameField) {
        CellLetter upNeighborLetter = CellLetter.values()[currentCellLetterNumber - 1];
        return gameField.get(upNeighborLetter).get(index);
    }

    public void addCheckerOnField(Checker checker, Cell cell, Game game) {
        game.getCheckerPositionMap().put(checker, cell);
        game.getCellWithCheckerMap().put(cell, checker);
    }

    public Cell getCell(Checker checker, Game game) {
        return game.getCheckerPositionMap().get(checker);
    }

    public Checker getChecker(Cell cell, Game game) {
        return game.getCellWithCheckerMap().get(cell);
    }

    public void removeChecker(Checker checker, Game game) {
        Cell cell = game.getCheckerPositionMap().get(checker);
        game.getCellWithCheckerMap().remove(cell);
        game.getCheckerPositionMap().remove(checker);
        Player opponent = game.getPlayersQueue().peek();
        game.getPlayerCheckersMap().get(opponent).remove(checker);
    }

    public void moveChecker(Checker checker, Cell cell, Game game) {
        Cell oldCell = game.getCheckerPositionMap().get(checker);
        game.getCellWithCheckerMap().remove(oldCell);
        game.getCellWithCheckerMap().put(cell, checker);
        game.getCheckerPositionMap().put(checker, cell);
        if(game.getCurrentDirection().equals(Direction.UP)) {
            List<Cell> cells = game.getGameField().get(CellLetter.H);
            for(Cell currentCell : cells) {
                if(currentCell.equals(cell))
                    checker.setKing();
            }
        }
        if(game.getCurrentDirection().equals(Direction.DOWN)) {
            List<Cell> cells = game.getGameField().get(CellLetter.A);
            for(Cell currentCell : cells) {
                if(currentCell.equals(cell))
                    checker.setKing();
            }
        }
    }

    public boolean isContainChecker(Cell cell, Game game) {
        for (Map.Entry<Cell, Checker> currentCell : game.getCellWithCheckerMap().entrySet()) {
            if (currentCell.getKey().equals(cell)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContainOppositeChecker(Cell cellWithOppositeChecker, Checker checker, Game game) {
        for (Map.Entry<Cell, Checker> currentCell : game.getCellWithCheckerMap().entrySet()) {
            if (currentCell.getKey().equals(cellWithOppositeChecker) && !checker.getColor().equals(currentCell.getValue().getColor())) {
                return true;
            }
        }
        return false;
    }
}
