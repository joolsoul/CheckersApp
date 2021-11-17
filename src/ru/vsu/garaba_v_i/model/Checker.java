package ru.vsu.garaba_v_i.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Checker
{
    private boolean canIMove = false;

    private final Color color;

    private boolean isKing = false;

    private final Image image;

    public Checker(Image image, Color color)
    {
        this.image = image;
        this.color = color;
    }

    public boolean isCanIMove()
    {
        return canIMove;
    }

    public void setCanIMove()
    {
        this.canIMove = true;
    }

    public boolean isKing()
    {
        return isKing;
    }

    public void setKing(boolean king)
    {
        isKing = king;
    }

    public Color getColor() {
        return color;
    }

    public Image getImage() {
        if(this.isKing) {
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
