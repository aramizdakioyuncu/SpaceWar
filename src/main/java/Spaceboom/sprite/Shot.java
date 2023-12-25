package Spaceboom.sprite;

import Spaceboom.Utility.SoundPlayer;

import javax.swing.*;

public class Shot extends Sprite {

    SoundPlayer splayer = new SoundPlayer();

    private double width2;
    private double length2;
    public static int speed = 10;
    public Shot(){}
    public Shot(double x, double y) {
        initShot(x, y);
    }

    private void initShot(double x, double y) {
        splayer.playAsync("laserpiuv.wav");

        String shotImg = "/images/bluelaser.png";

        ImageIcon ii = new ImageIcon(getClass().getResource(shotImg));

        setImage(ii.getImage());
        width2 = (ii.getIconWidth() / 20);
        length2 = (ii.getIconHeight() / 35);
        java.awt.Image scaledImage = ii.getImage().getScaledInstance((int) width2, (int) length2, java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        setImage(ii.getImage());
        int H_SPACE = 3;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
