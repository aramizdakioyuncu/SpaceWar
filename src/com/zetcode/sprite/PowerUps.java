package com.zetcode.sprite;

import java.awt.*;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class PowerUps extends Sprite {

    final String powerUpImg2Path = "src/images/powerup2.png";
    private  Image powerUpImg2;
    final String powerUpImg1Path = "src/images/powerup1.png";
    private Image powerUpImg1;

    public PowerUps() {
        initPowerUps();
    }

    private void initPowerUps() {
        ImageIcon powerUpIcon1 = new ImageIcon(powerUpImg1Path);
        powerUpImg1 = powerUpIcon1.getImage();

        var ii = new ImageIcon(powerUpImg1);
        int newWidth = (ii.getIconWidth() / 9);
        int newHeight = (ii.getIconHeight() / 9);
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);

        ImageIcon powerUpIcon2 = new ImageIcon(powerUpImg2Path);
        powerUpImg2 = powerUpIcon2.getImage();

        var ii1 = new ImageIcon(powerUpImg2);
        int newWidth1 = (ii1.getIconWidth() / 25);
        int newHeight1 = (ii1.getIconHeight() / 25);
        java.awt.Image scaledImage1 = ii1.getImage().getScaledInstance(newWidth1, newHeight1, java.awt.Image.SCALE_SMOOTH);
        ii1 = new ImageIcon(scaledImage1);
        setImage(ii1.getImage());





        }





















}
