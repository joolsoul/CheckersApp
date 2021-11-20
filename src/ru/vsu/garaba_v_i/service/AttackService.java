package ru.vsu.garaba_v_i.service;

import ru.vsu.garaba_v_i.model.Checker;
import ru.vsu.garaba_v_i.model.Direction;
import ru.vsu.garaba_v_i.model.Game;
import ru.vsu.garaba_v_i.model.field.Cell;

import java.util.List;

public class AttackService {

    private final FieldService fieldService = new FieldService();

    public boolean doAttack(List<Checker> playerChecker, Game game) {
        Direction direction = game.getCurrentDirection();
        for (Checker checker : playerChecker) {
            if (checker.isCanIMove()) {

                if (direction.equals(Direction.UP)) {
                    if (checker.isKing() ? kingDownLeftAttack(checker, game) : downLeftAttack(checker, game)) {
                        game.setCurrentDirection(Direction.DOWN);
                        return true;
                    }
                    else if (checker.isKing() ? kingDownRightAttack(checker, game) : downRightAttack(checker, game)) {
                        game.setCurrentDirection(Direction.DOWN);
                        return true;
                    } else if (checker.isKing() ? kingUpLeftAttack(checker, game) : upLeftAttack(checker, game)) {
                        game.setCurrentDirection(Direction.DOWN);
                        return true;
                    } else if (checker.isKing() ? kingUpRightAttack(checker, game) : upRightAttack(checker, game)) {
                        game.setCurrentDirection(Direction.DOWN);
                        return true;
                    }
                }

                if (direction.equals(Direction.DOWN)) {
                    if (checker.isKing() ? kingUpLeftAttack(checker, game) : upLeftAttack(checker, game)) {
                        game.setCurrentDirection(Direction.UP);
                        return true;
                    } else if (checker.isKing() ? kingUpRightAttack(checker, game) : upRightAttack(checker, game)) {
                        game.setCurrentDirection(Direction.UP);
                        return true;
                    } else if (checker.isKing() ? kingDownLeftAttack(checker, game) : downLeftAttack(checker, game)) {
                        game.setCurrentDirection(Direction.UP);
                        return true;
                    } else if (checker.isKing() ? kingDownRightAttack(checker, game) : downRightAttack(checker, game)) {
                        game.setCurrentDirection(Direction.UP);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean kingDownRightAttack(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        while (currentCell.hasDownRightNeighbor()) {
            Cell downRightNeighbor = currentCell.getDownRightNeighbor();

            if (fieldService.isContainOppositeChecker(downRightNeighbor, checker, game) &&
                    canIAttack(fieldService.getChecker(downRightNeighbor, game), Direction.DOWN_RIGHT, game)) {
                fieldService.removeChecker(fieldService.getChecker(downRightNeighbor, game), game);
                Cell nextCell = downRightNeighbor.getDownRightNeighbor();
                fieldService.moveChecker(checker, nextCell, game);

                while (nextCell.hasDownRightNeighbor() &&
                        (nextCell.hasUpRightNeighbor() && fieldService.isContainOppositeChecker(nextCell.getUpRightNeighbor(), checker, game)
                                || nextCell.hasDownLeftNeighbor() && fieldService.isContainOppositeChecker(nextCell.getDownLeftNeighbor(), checker, game))) {
                    nextCell = nextCell.getDownRightNeighbor();
                    fieldService.moveChecker(checker, nextCell, game);
                }
                return true;
            }
            currentCell = currentCell.getDownRightNeighbor();
        }
        return false;
    }

    private boolean kingDownLeftAttack(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        while (currentCell.hasDownLeftNeighbor()) {
            Cell downLeftNeighbor = currentCell.getDownLeftNeighbor();

            if (fieldService.isContainOppositeChecker(downLeftNeighbor, checker, game) &&
                    canIAttack(fieldService.getChecker(downLeftNeighbor, game), Direction.DOWN_LEFT, game)) {
                fieldService.removeChecker(fieldService.getChecker(downLeftNeighbor, game), game);
                Cell nextCell = downLeftNeighbor.getDownLeftNeighbor();
                fieldService.moveChecker(checker, nextCell, game);

                while (nextCell.hasDownLeftNeighbor() &&
                        (nextCell.hasUpLeftNeighbor() && fieldService.isContainOppositeChecker(nextCell.getUpLeftNeighbor(), checker, game)
                                || nextCell.hasDownRightNeighbor() && fieldService.isContainOppositeChecker(nextCell.getDownRightNeighbor(), checker, game))) {
                    nextCell = nextCell.getDownLeftNeighbor();
                    fieldService.moveChecker(checker, nextCell, game);
                }
                return true;
            }
            currentCell = currentCell.getDownLeftNeighbor();
        }
        return false;
    }

    private boolean kingUpRightAttack(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        while (currentCell.hasUpRightNeighbor()) {
            Cell upRightNeighbor = currentCell.getUpRightNeighbor();

            if (fieldService.isContainOppositeChecker(upRightNeighbor, checker, game) &&
                    canIAttack(fieldService.getChecker(upRightNeighbor, game), Direction.UP_RIGHT, game)) {
                fieldService.removeChecker(fieldService.getChecker(upRightNeighbor, game), game);
                Cell nextCell = upRightNeighbor.getUpRightNeighbor();
                fieldService.moveChecker(checker, nextCell, game);

                while (nextCell.hasUpRightNeighbor() &&
                        (nextCell.hasUpLeftNeighbor() && fieldService.isContainOppositeChecker(nextCell.getUpLeftNeighbor(), checker, game)
                                || nextCell.hasDownRightNeighbor() && fieldService.isContainOppositeChecker(nextCell.getDownRightNeighbor(), checker, game))) {
                    nextCell = nextCell.getUpRightNeighbor();
                    fieldService.moveChecker(checker, nextCell, game);
                }
                return true;
            }
            currentCell = currentCell.getUpRightNeighbor();
        }
        return false;
    }

    private boolean kingUpLeftAttack(Checker checker, Game game) {
        Cell currentCell = fieldService.getCell(checker, game);
        while (currentCell.hasUpLeftNeighbor()) {
            Cell upLeftNeighbor = currentCell.getUpLeftNeighbor();

            if (fieldService.isContainOppositeChecker(upLeftNeighbor, checker, game) &&
                    canIAttack(fieldService.getChecker(upLeftNeighbor, game), Direction.UP_LEFT, game)) {
                fieldService.removeChecker(fieldService.getChecker(upLeftNeighbor, game), game);
                Cell nextCell = upLeftNeighbor.getUpLeftNeighbor();
                fieldService.moveChecker(checker, nextCell, game);

                while (nextCell.hasUpLeftNeighbor() &&
                        (nextCell.hasUpRightNeighbor() && fieldService.isContainOppositeChecker(nextCell.getUpRightNeighbor(), checker, game)
                                || nextCell.hasDownLeftNeighbor() && fieldService.isContainOppositeChecker(nextCell.getDownLeftNeighbor(), checker, game))) {
                    nextCell = nextCell.getUpLeftNeighbor();
                    fieldService.moveChecker(checker, nextCell, game);
                }
                return true;
            }
            currentCell = currentCell.getUpLeftNeighbor();
        }
        return false;
    }

    private boolean downLeftAttack(Checker checker, Game game) {
        boolean isAttack = false;
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasDownLeftNeighbor()) {
            Cell downLeftNeighbor = currentCell.getDownLeftNeighbor();

            if (fieldService.isContainOppositeChecker(downLeftNeighbor, checker, game)) {
                Checker oppositeChecker = fieldService.getChecker(downLeftNeighbor, game);

                if (canIAttack(oppositeChecker, Direction.DOWN_LEFT, game)) {
                    fieldService.removeChecker(oppositeChecker, game);
                    fieldService.moveChecker(checker, downLeftNeighbor.getDownLeftNeighbor(), game);
                    isAttack = true;
                    downLeftAttack(checker, game);
                    downRightAttack(checker, game);
                    upLeftAttack(checker, game);
                    upRightAttack(checker, game);
                }
            }
        }
        return isAttack;
    }

    private boolean downRightAttack(Checker checker, Game game) {
        boolean isAttack = false;
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasDownRightNeighbor()) {
            Cell downRightNeighbor = currentCell.getDownRightNeighbor();

            if (fieldService.isContainOppositeChecker(downRightNeighbor, checker, game)) {
                Checker oppositeChecker = fieldService.getChecker(downRightNeighbor, game);

                if (canIAttack(oppositeChecker, Direction.DOWN_RIGHT, game)) {
                    fieldService.removeChecker(oppositeChecker, game);
                    fieldService.moveChecker(checker, downRightNeighbor.getDownRightNeighbor(), game);
                    isAttack = true;
                    downLeftAttack(checker, game);
                    downRightAttack(checker, game);
                    upLeftAttack(checker, game);
                    upRightAttack(checker, game);
                }
            }
        }
        return isAttack;
    }

    private boolean upLeftAttack(Checker checker, Game game) {
        boolean isAttack = false;
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasUpLeftNeighbor()) {
            Cell upLeftNeighbor = currentCell.getUpLeftNeighbor();

            if (fieldService.isContainOppositeChecker(upLeftNeighbor, checker, game)) {
                Checker oppositeChecker = fieldService.getChecker(upLeftNeighbor, game);

                if (canIAttack(oppositeChecker, Direction.UP_LEFT, game)) {
                    fieldService.removeChecker(oppositeChecker, game);
                    fieldService.moveChecker(checker, upLeftNeighbor.getUpLeftNeighbor(), game);
                    isAttack = true;
                    upLeftAttack(checker, game);
                    upRightAttack(checker, game);
                    downLeftAttack(checker, game);
                    downRightAttack(checker, game);
                }
            }
        }
        return isAttack;
    }

    private boolean upRightAttack(Checker checker, Game game) {
        boolean isAttack = false;
        Cell currentCell = fieldService.getCell(checker, game);
        if (currentCell.hasUpRightNeighbor()) {
            Cell upRightNeighbor = currentCell.getUpRightNeighbor();

            if (fieldService.isContainOppositeChecker(upRightNeighbor, checker, game)) {
                Checker oppositeChecker = fieldService.getChecker(upRightNeighbor, game);

                if (canIAttack(oppositeChecker, Direction.UP_RIGHT, game)) {
                    fieldService.removeChecker(oppositeChecker, game);
                    fieldService.moveChecker(checker, upRightNeighbor.getUpRightNeighbor(), game);
                    isAttack = true;
                    upLeftAttack(checker, game);
                    upRightAttack(checker, game);
                    downLeftAttack(checker, game);
                    downRightAttack(checker, game);
                }
            }
        }
        return isAttack;
    }

    public boolean canIAttack(Checker oppositeChecker, Direction direction, Game game) {
        Cell cellWithOppositeChecker = fieldService.getCell(oppositeChecker, game);
        return cellWithOppositeChecker.hasNeighbor(direction) && cellWithOppositeChecker.hasNeighbor(direction) &&
                !fieldService.isContainChecker(cellWithOppositeChecker.getNeighbor(direction), game);
    }

}
