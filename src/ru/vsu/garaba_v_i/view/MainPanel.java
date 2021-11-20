package ru.vsu.garaba_v_i.view;

import ru.vsu.garaba_v_i.model.Checker;
import ru.vsu.garaba_v_i.model.Game;
import ru.vsu.garaba_v_i.model.Player;
import ru.vsu.garaba_v_i.model.field.Cell;
import ru.vsu.garaba_v_i.model.field.CellLetter;
import ru.vsu.garaba_v_i.service.FieldService;
import ru.vsu.garaba_v_i.service.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MainPanel extends JPanel implements MouseListener {

    private final Game game;
    private final GameService gameService = new GameService();
    private final FieldService fieldService = new FieldService();

    private final int CELL_SIZE = 112;

    public MainPanel() {
        Player p1 = new Player("German");
        Player p2 = new Player("Vlada");
        Queue<Player> playerQueue = new LinkedList<>();
        playerQueue.add(p1);
        playerQueue.add(p2);

        this.game = gameService.createGame(playerQueue);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;

        paintBackground(gr);

        paintGameField(gr);

        paintCheckers(gr);

        if (!game.isInGame()) {
            paintWinner(gr);
        }
    }

    private void paintBackground(Graphics2D gr) {
        Color oldColor = gr.getColor();
        int height = this.getHeight() - 100;
        int width = this.getWidth() - 100;

        gr.setColor(new Color(0xE1CFA1));
        gr.fillRect(0, 0, this.getWidth(), this.getHeight());

        gr.setColor(new Color(0x745D55));
        gr.setStroke(new BasicStroke(3));
        gr.drawRect(50, 50, width, height);

        gr.setColor(oldColor);
    }

    private void paintGameField(Graphics2D gr) {
        boolean flag = true;
        int startY = 50;
        for (int i = 0; i < CellLetter.values().length; i++, startY += CELL_SIZE, flag = !flag) {
            int startX = 50;
            if (!flag) {
                startX += CELL_SIZE;
            }
            for (int j = 0; j < GameService.GAME_FIELD_WIDTH / 2; j++, startX += CELL_SIZE * 2) {
                paintCell(gr, startX, startY);
            }
        }
        paintSymbols(gr);
    }

    private void paintCell(Graphics2D gr, int x, int y) {
        gr.setColor(new Color(0x745D55));
        gr.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    private void paintSymbols(Graphics2D gr) {
        Font myFont = new Font("Courier New", Font.BOLD, 20);
        gr.setFont(myFont);
        int startX = 25;
        int startY = 106;
        int lettersCount = 0;
        for (char letter = 'H'; lettersCount < CellLetter.values().length; lettersCount++, letter--, startY += CELL_SIZE) {
            gr.drawString(String.valueOf(letter), startX, startY);
        }
        startX = 106;
        startY = this.getWidth() - 25;
        for (int j = 0, number = 1; j < GameService.GAME_FIELD_WIDTH; j++, startX += CELL_SIZE, number++) {
            gr.drawString(String.valueOf(number), startX, startY);
        }
    }

    private void paintCheckers(Graphics2D gr) {
        boolean flag = true;
        for (Map.Entry<CellLetter, List<Cell>> entry : game.getGameField().entrySet()) {
            List<Cell> currentCells = entry.getValue();
            for (int i = 0; i < currentCells.size(); i++) {
                Cell currentCell = currentCells.get(i);
                if (fieldService.isContainChecker(currentCell, game)) {
                    Checker checker = fieldService.getChecker(currentCell, game);
                    int x = 50 + CELL_SIZE * i * 2;
                    if (flag) {
                        x += CELL_SIZE;
                    }
                    int y = this.getHeight() - 48 - CELL_SIZE - CELL_SIZE * entry.getKey().ordinal();
                    paintChecker(gr, x, y, checker);
                }
            }
            flag = !flag;
        }
    }

    private void paintChecker(Graphics2D gr, int x, int y, Checker checker) {
        gr.drawImage(checker.getImage(), x, y, CELL_SIZE, CELL_SIZE, this);
    }

    private void paintWinner(Graphics2D gr) {
        paintWithFont(gr, new Font("Serif", Font.BOLD, 70), new Color(0x00526E), () ->
        {
            gr.drawString("Winner", (int) (this.getWidth() / 2.6), (int) (this.getHeight() / 2.1));
        });

        paintWithFont(gr, new Font("Serif", Font.BOLD, 50), new Color(0x0093C5), () ->
        {
            Player player = game.getWinner();
            gr.drawString(player.getName(), (int) (this.getWidth() / 2.4), (int) (this.getHeight() / 1.8));
        });
    }

    private void paintWithFont(Graphics2D g, Font font, Color color, Runnable drawAction) {
        Font oldF = g.getFont();
        Color oldC = g.getColor();
        g.setFont(font);
        g.setColor(color);
        drawAction.run();
        g.setFont(oldF);
        g.setColor(oldC);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (game.isInGame()) {
            gameService.playGame(game);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
