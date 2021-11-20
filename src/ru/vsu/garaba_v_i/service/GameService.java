package ru.vsu.garaba_v_i.service;

import ru.vsu.garaba_v_i.model.Color;
import ru.vsu.garaba_v_i.model.*;
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

public class GameService {

    public static int GAME_FIELD_WIDTH = 8;
    private final FieldService fieldService = new FieldService();
    private final AttackService attackService = new AttackService();
    private final MoveService moveService = new MoveService();

    public GameService() {

    }

    public Game createGame(Queue<Player> playersQueue) {
        Game game = new Game(playersQueue, Direction.UP);
        initPlayerCheckersMap(game.getPlayerCheckersMap(), game.getPlayersQueue());
        fieldService.createGameField(GAME_FIELD_WIDTH, game.getGameField());
        startInitCheckers(game);

        return game;
    }

    public boolean playGame(Game game) {
        Player currentPlayer = game.getPlayersQueue().poll();
        game.getPlayersQueue().add(currentPlayer);
        List<Checker> playerChecker = game.getPlayerCheckersMap().get(currentPlayer);
        recalculationCanIMove(playerChecker, game);

        if(attackService.doAttack(playerChecker, game)) {
            if (game.getPlayerCheckersMap().get(game.getPlayersQueue().peek()).isEmpty()) {
                game.setWinner(currentPlayer);
                game.setNotInGame();
                return false;
            }
            return true;
        }
        return moveService.doMove(playerChecker, game);
    }

    private void initPlayerCheckersMap
            (Map<Player, List<Checker>> playerCheckersMap, Queue<Player> playersQueue) {
        for (Player player : playersQueue) {
            playerCheckersMap.put(player, new LinkedList<>());
        }
    }

    private void startInitCheckers(Game game) {
        Direction currentDirection = Direction.UP;
        CellLetter currentCellLetter = CellLetter.A;
        Color checkerColor = Color.WHITE;
        for (Player player : game.getPlayersQueue()) {
            int iteration = 0;

            while (iteration < 3) {
                List<Cell> currentCells = game.getGameField().get(currentCellLetter);

                for (Cell cell : currentCells) {
                    String imageName = "whiteChecker.png";
                    if (checkerColor.equals(Color.BLACK)) {
                        imageName = "blackChecker.png";
                    }
                    Image image = null;
                    try {
                        image = ImageIO.read(new File("resources/" + imageName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Checker checker = new Checker(image, checkerColor);

                    if (iteration == 2) {
                        checker.setCanMove();
                    }

                    addPlayerChecker(checker, player, game.getPlayerCheckersMap());
                    fieldService.addCheckerOnField(checker, cell, game);
                }

                if (currentDirection.equals(Direction.UP)) {
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

    private void recalculationCanIMove(List<Checker> playerCheckers, Game game) {
        for (Checker checker : playerCheckers) {
            Cell cell = fieldService.getCell(checker, game);
            boolean canIMove = false;
            if (cell.hasUpRightNeighbor()) {
                Cell cellNeighbor = cell.getUpRightNeighbor();
                if (checkCanCheckerMove(cellNeighbor, checker, Direction.UP_RIGHT, game))
                    canIMove = true;
            }
            if (cell.hasUpLeftNeighbor()) {
                Cell cellNeighbor = cell.getUpLeftNeighbor();
                if (checkCanCheckerMove(cellNeighbor, checker, Direction.UP_LEFT, game))
                    canIMove = true;
            }
            if (cell.hasDownRightNeighbor()) {
                Cell cellNeighbor = cell.getDownRightNeighbor();
                if (checkCanCheckerMove(cellNeighbor, checker, Direction.DOWN_RIGHT, game))
                    canIMove = true;
            }
            if (cell.hasDownLeftNeighbor()) {
                Cell cellNeighbor = cell.getDownLeftNeighbor();
                if (checkCanCheckerMove(cellNeighbor, checker, Direction.DOWN_LEFT, game))
                    canIMove = true;
            }

            if (!canIMove)
                checker.setCanNotMove();
        }
    }

    private boolean checkCanCheckerMove(Cell cellNeighbor, Checker checker, Direction direction, Game game) {

        if (!fieldService.isContainChecker(cellNeighbor, game)) {
            checker.setCanMove();
            return true;
        } else if (fieldService.isContainOppositeChecker(cellNeighbor, checker, game)) {
            if (attackService.canIAttack(fieldService.getChecker(cellNeighbor, game), direction, game)) {
                checker.setCanMove();
                return true;
            }
        }
        return false;
    }
}




