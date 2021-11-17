package ru.vsu.garaba_v_i;

import ru.vsu.garaba_v_i.model.Game;
import ru.vsu.garaba_v_i.model.Player;
import ru.vsu.garaba_v_i.model.field.Cell;
import ru.vsu.garaba_v_i.model.field.CellLetter;
import ru.vsu.garaba_v_i.service.FieldService;
import ru.vsu.garaba_v_i.service.GameService;
import ru.vsu.garaba_v_i.view.MainWindow;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        /*
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Queue<Player> playerQueue = new LinkedList<>();
        playerQueue.add(p1);
        playerQueue.add(p2);

        GameService gameService = new GameService();

        Game game = gameService.createGame(playerQueue);
        System.out.println();

         */

        new MainWindow();
    }
}
