package ru.vsu.joolsoul.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Checker {

    private final Color color;
    private final Image image;
    private boolean canIMove = false;
    private boolean isKing = false;

    public Checker(Image image, Color color) {
        this.image = image;
        this.color = color;
    }

    public boolean isCanIMove() {
        return canIMove;
    }

    public void setCanMove() {
        this.canIMove = true;
    }

    public void setCanNotMove() {
        this.canIMove = false;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing() {
        isKing = true;
    }

    public Color getColor() {
        return color;
    }

    public Image getImage() {
        if (this.isKing) {
            try {
                if (this.color.equals(Color.WHITE)) {
                    return ImageIO.read(new File("resources/whiteKing.png"));
                }
                return ImageIO.read(new File("resources/blackKing.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
}
