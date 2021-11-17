package ru.vsu.garaba_v_i.service;

import ru.vsu.garaba_v_i.model.*;
import ru.vsu.garaba_v_i.model.Color;
import ru.vsu.garaba_v_i.model.field.Cell;
import ru.vsu.garaba_v_i.model.field.CellLetter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GameService
{
    private final FieldService fieldService = new FieldService();
    public static int GAME_FIELD_WIDTH = 8;

    public GameService()
    {

    }

    public Game createGame(Queue<Player> playersQueue)
    {
        Game game = new Game(playersQueue, Direction.UP);
        initPlayerCheckersMap(game.getPlayerCheckersMap(), game.getPlayersQueue());
        fieldService.createGameField(GAME_FIELD_WIDTH, game.getGameField());
        startInitCheckers(game);

        return game;
    }

    public void playGame(Game game)
    {

    }

    public boolean doStep(Game game) {
        Player currentPlayer = game.getPlayersQueue().poll();
        game.getPlayersQueue().add(currentPlayer);
        List<Checker> playerChecker = game.getPlayerCheckersMap().get(currentPlayer);

        return doAttack(playerChecker, game.getCurrentDirection(), game);
    }

    private boolean doAttack(List<Checker> playerChecker, Direction direction, Game game) {
        for(Checker checker : playerChecker) {
            if(checker.isCanIMove()) {
                Cell currentCell = fieldService.getCell(checker, game.getCheckerPositionMap());

                if(direction.equals(Direction.UP)) {
                    if(downAttack(currentCell, checker, game)) {
                        return true;
                    }
                    if(upAttack(currentCell, checker, game)) {
                        return true;
                    }
                    game.setCurrentDirection(Direction.DOWN);
                }
                if(direction.equals(Direction.DOWN)) {
                    if(upAttack(currentCell, checker, game)) {
                        return true;
                    }
                    if(downAttack(currentCell, checker, game)) {
                        return true;
                    }
                    game.setCurrentDirection(Direction.UP);
                }
            }
        }
        for(Checker checker : playerChecker) {
            if(checker.isCanIMove()) {
                Cell currentCell = fieldService.getCell(checker, game.getCheckerPositionMap());
                if(direction.equals(Direction.UP)) {
                    if (upStep(currentCell, checker, game)) {
                        return true;
                    }
                    game.setCurrentDirection(Direction.DOWN);
                }
                if(direction.equals(Direction.DOWN)) {
                    if (downStep(currentCell, checker, game)) {
                        return true;
                    }
                    game.setCurrentDirection(Direction.UP);
                }
            }
        }
        return false;
    }

    private boolean downAttack(Cell currentCell, Checker checker, Game game) {
        boolean isAttack = false;
        if(currentCell.getDownNeighboringCells() != null) {

            List<Cell> downNeighboringCells = currentCell.getDownNeighboringCells();
            for(int i = 0; i < downNeighboringCells.size(); i++) {
                Cell downNeighborCell = downNeighboringCells.get(i);

                if(isContainOppositeChecker(downNeighborCell, checker, game.getCellWithCheckerMap())) {
                    Checker oppositeChecker = fieldService.getChecker(downNeighborCell, game.getCellWithCheckerMap());

                    if(downNeighborCell.getDownNeighboringCells() != null)
                    {
                        Cell downCell = downNeighborCell.getDownNeighboringCells().get(i);
                        if(!isContainChecker(downCell, game.getCellWithCheckerMap())) {
                            fieldService.removeChecker(oppositeChecker, game);
                            fieldService.moveChecker(checker, downCell, game);
                            isAttack = true;
                            downAttack(downCell, checker, game);
                        }
                    }
                }
            }
        }
        return isAttack;
    }

    private boolean upAttack(Cell currentCell, Checker checker, Game game) {
        boolean isAttack = false;
        if(currentCell.getUpNeighboringCells() != null) {

            List<Cell> upNeighboringCells = currentCell.getUpNeighboringCells();
            for(int i = 0; i < upNeighboringCells.size(); i++) {
                Cell upNeighborCell = upNeighboringCells.get(i);

                if(isContainOppositeChecker(upNeighborCell, checker, game.getCellWithCheckerMap())) {
                    Checker oppositeChecker = fieldService.getChecker(upNeighborCell, game.getCellWithCheckerMap());

                    if(upNeighborCell.getUpNeighboringCells() != null)
                    {
                        Cell upCell = upNeighborCell.getUpNeighboringCells().get(i);
                        if(!isContainChecker(upCell, game.getCellWithCheckerMap())) {
                            fieldService.removeChecker(oppositeChecker, game);
                            fieldService.moveChecker(checker, upCell, game);
                            isAttack = true;
                            upAttack(upCell, checker, game);
                        }
                    }
                }
            }
        }
        return isAttack;
    }

    private boolean upStep(Cell currentCell, Checker checker, Game game) {
        if(currentCell.getUpNeighboringCells() != null) {
            List<Cell> upNeighboringCells = currentCell.getUpNeighboringCells();

            for (Cell upNeighborCell : upNeighboringCells) {
                if (!isContainChecker(upNeighborCell, game.getCellWithCheckerMap())) {
                    fieldService.moveChecker(checker, upNeighborCell, game);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean downStep(Cell currentCell, Checker checker, Game game) {
        if(currentCell.getDownNeighboringCells() != null) {
            List<Cell> downNeighboringCells = currentCell.getDownNeighboringCells();

            for (Cell downNeighborCell : downNeighboringCells) {
                if (!isContainChecker(downNeighborCell, game.getCellWithCheckerMap())) {
                    fieldService.moveChecker(checker, downNeighborCell, game);
                    return true;
                }
            }
        }
        return false;
    }

    private void initPlayerCheckersMap(Map<Player, List<Checker>> playerCheckersMap, Queue<Player> playersQueue) {
        for(Player player : playersQueue) {
            playerCheckersMap.put(player, new LinkedList<>());
        }
    }

    private void startInitCheckers(Game game) {
        Direction currentDirection = Direction.UP;
        CellLetter currentCellLetter = CellLetter.A;
        Color checkerColor = Color.WHITE;
        for(Player player : game.getPlayersQueue()) {
            int iteration = 0;

           while (iteration < 3) {
               List<Cell> currentCells = game.getGameField().get(currentCellLetter);

               for(Cell cell : currentCells) {
                   String imageName = "whiteChecker.png";
                   if(checkerColor.equals(Color.BLACK)) {
                       imageName = "blackChecker.png";
                   }
                   Image image = null;
                   try {
                       image = ImageIO.read(new File("resources/" + imageName));
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   Checker checker = new Checker(image, checkerColor);

                   if(iteration == 2) {
                       checker.setCanIMove();
                   }

                   addPlayerChecker(checker, player, game.getPlayerCheckersMap());
                   fieldService.addCheckerOnField(checker, cell, game.getCheckerPositionMap(), game.getCellWithCheckerMap());
               }

               if(currentDirection.equals(Direction.UP)) {
                   currentCellLetter = CellLetter.values()[currentCellLetter.ordinal() + 1];
               } else {
                   currentCellLetter = CellLetter.values()[currentCellLetter.ordinal() - 1];
               }
               iteration++;
           }
           currentDirection = Direction.DOWN;
           currentCellLetter = CellLetter.H;
           checkerColor = Color.BLACK;
        }
    }

    private void addPlayerChecker(Checker checker, Player player, Map<Player, List<Checker>> playerCheckersMap) {
        playerCheckersMap.get(player).add(checker);
    }

    public boolean isContainChecker(Cell cell, Map<Cell, Checker> cellWithCheckerMap) {
        for(Map.Entry<Cell, Checker> currentCell : cellWithCheckerMap.entrySet()) {
            if(currentCell.getKey().equals(cell)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContainOppositeChecker(Cell cell, Checker checker, Map<Cell, Checker> cellWithCheckerMap) {
        for(Map.Entry<Cell, Checker> currentCell : cellWithCheckerMap.entrySet()) {
            if(currentCell.getKey().equals(cell) && !checker.getColor().equals(currentCell.getValue().getColor())) {
                return true;
            }
        }
        return false;
    }

}
