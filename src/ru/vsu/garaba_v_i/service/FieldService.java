package ru.vsu.garaba_v_i.service;

import ru.vsu.garaba_v_i.model.Checker;
import ru.vsu.garaba_v_i.model.Direction;
import ru.vsu.garaba_v_i.model.Game;
import ru.vsu.garaba_v_i.model.Player;
import ru.vsu.garaba_v_i.model.field.Cell;
import ru.vsu.garaba_v_i.model.field.CellLetter;

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
                List<Cell> upNeighborsCells = new LinkedList<>();
                List<Cell> downNeighborsCells = new LinkedList<>();
                Cell currentCell = currentCellsList.get(cellNumber);

                if(cellLetterNumber == 0) {
                    if (cellNumber != 0) {
                        upNeighborsCells.add(getUpNeighbor(cellLetterNumber, cellNumber - 1, gameField));
                    }
                    upNeighborsCells.add(getUpNeighbor(cellLetterNumber, cellNumber, gameField));
                    currentCell.setUpCells(upNeighborsCells);
                }
                if(cellLetterNumber == 7) {
                    downNeighborsCells.add(getDownNeighbor(cellLetterNumber, cellNumber, gameField));
                    if(cellNumber != currentCellsList.size() - 1) {
                        downNeighborsCells.add(getDownNeighbor(cellLetterNumber, cellNumber + 1, gameField));
                    }
                    currentCell.setDownCells(downNeighborsCells);
                }
                if(cellLetterNumber != 0 && cellLetterNumber != 7 ) {
                    if(!isDoubleNeighbors) {
                        if(cellNumber != 0) {
                            upNeighborsCells.add(getUpNeighbor(cellLetterNumber, cellNumber - 1, gameField));
                            downNeighborsCells.add(getDownNeighbor(cellLetterNumber, cellNumber - 1, gameField));
                        }
                    }
                    upNeighborsCells.add(getUpNeighbor(cellLetterNumber, cellNumber, gameField));
                    downNeighborsCells.add(getDownNeighbor(cellLetterNumber, cellNumber, gameField));
                    if(isDoubleNeighbors) {
                        if(cellNumber != currentCellsList.size() - 1) {
                            upNeighborsCells.add(getUpNeighbor(cellLetterNumber, cellNumber + 1, gameField));
                            downNeighborsCells.add(getDownNeighbor(cellLetterNumber, cellNumber + 1, gameField));
                        }
                    }
                    currentCell.setUpCells(upNeighborsCells);
                    currentCell.setDownCells(downNeighborsCells);
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

    public void addCheckerOnField(Checker checker, Cell cell, Map<Checker, Cell> checkerPositionMap, Map<Cell, Checker> cellWithCheckerMap) {
        checkerPositionMap.put(checker, cell);
        cellWithCheckerMap.put(cell, checker);
    }

    public Cell getCell(Checker checker, Map<Checker, Cell> checkerPositionMap) {
        return checkerPositionMap.get(checker);
    }

    public Checker getChecker(Cell cell, Map<Cell, Checker> cellWithCheckerMap) {
        return cellWithCheckerMap.get(cell);
    }

    public void removeChecker(Checker checker, Game game) {
        Cell cell = game.getCheckerPositionMap().get(checker);
        game.getCellWithCheckerMap().remove(cell);
        game.getCheckerPositionMap().remove(checker);
    }

    public void moveChecker(Checker checker, Cell cell, Game game) {
        Cell oldCell = game.getCheckerPositionMap().get(checker);
        game.getCellWithCheckerMap().remove(oldCell);
        game.getCellWithCheckerMap().put(cell, checker);
        game.getCheckerPositionMap().put(checker, cell);
    }
}
