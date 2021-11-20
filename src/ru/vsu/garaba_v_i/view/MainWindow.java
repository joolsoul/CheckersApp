package ru.vsu.garaba_v_i.view;

import javax.swing.*;

public class MainWindow extends JFrame {

    private final int WIDTH = 1010;
    private final int HEIGHT = 1033;

    public MainWindow() {
        setTitle("Checkers");
        setSize(WIDTH, HEIGHT);
        ImageIcon icon = new ImageIcon("resources/applicationImage.png");
        setIconImage(icon.getImage());
        add(new MainPanel());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
