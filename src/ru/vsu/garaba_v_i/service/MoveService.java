package ru.vsu.garaba_v_i.service;

import ru.vsu.garaba_v_i.model.Checker;
import ru.vsu.garaba_v_i.model.Direction;
import ru.vsu.garaba_v_i.model.Game;
import ru.vsu.garaba_v_i.model.field.Cell;

import java.util.List;

public class MoveService {

    private final FieldService fieldService = new FieldService();

    public boolean doMove(List<Checker> playerChecker, Game game) {
        Direction direction = game.getCurrentDirection();
        for (Checker checker : playerChecker) {
            if (checker.isCanIMove() && !checker.isKing()) {

                if (direction.equals(Direction.UP)) {
                    if (doUpLeftMove(checker, game)) {
                        game.setCurrentDirection(Direction.DOWN);
                        return true;
                    }
                    else if (doUpRightMove(checker, game)) {
                        game.setCurrentDirection(Direction.DOWN);
                        return true;
                    }
                }

                if (direction.equals(Direction.DOWN)) {
                    if (doDownLeftMove(checker, game)) {
                        game.setCurrentDirection(Direction.UP);
                        return true;
                    }
                    else if (doDownRightMove(checker, game)) {
                        game.setCurrentDirection(Direction.UP);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean doUpLeftMove(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasUpLeftNeighbor()) {
            Cell upLeftNeighbor = currentCell.getUpLeftNeighbor();

            if (!fieldService.isContainChecker(upLeftNeighbor, game)) {
                fieldService.moveChecker(checker, upLeftNeighbor, game);
                return true;
            }
        }
        return false;
    }

    private boolean doUpRightMove(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasUpRightNeighbor()) {
            Cell upRightNeighbor = currentCell.getUpRightNeighbor();

            if (!fieldService.isContainChecker(upRightNeighbor, game)) {
                fieldService.moveChecker(checker, upRightNeighbor, game);
                return true;
            }
        }
        return false;
    }

    private boolean doDownLeftMove(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasDownLeftNeighbor()) {
            Cell downLeftNeighbor = currentCell.getDownLeftNeighbor();

            if (!fieldService.isContainChecker(downLeftNeighbor, game)) {
                fieldService.moveChecker(checker, downLeftNeighbor, game);
                return true;
            }
        }
        return false;
    }

    private boolean doDownRightMove(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasDownRightNeighbor()) {
            Cell downRightNeighbor = currentCell.getDownRightNeighbor();

            if (!fieldService.isContainChecker(downRightNeighbor, game)) {
                fieldService.moveChecker(checker, downRightNeighbor, game);
                return true;
            }
        }
        return false;
    }

}
