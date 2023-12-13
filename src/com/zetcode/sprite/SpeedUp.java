package com.zetcode.sprite;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;

public class SpeedUp extends Sprite{

    final String SpeedUpImgPath = "src/images/SpeedUp.png";
    private Image SpeedUpImg;

    public SpeedUp(){
        initSpeedUp();
    }

    private void initSpeedUp(){
        ImageIcon powerUpIcon2 = new ImageIcon(SpeedUpImgPath);
        SpeedUpImg = powerUpIcon2.getImage();

        var ii1 = new ImageIcon(SpeedUpImg);
        int newWidth1 = (ii1.getIconWidth() / 25);
        int newHeight1 = (ii1.getIconHeight() / 25);
        java.awt.Image scaledImage1 = ii1.getImage().getScaledInstance(newWidth1, newHeight1, java.awt.Image.SCALE_SMOOTH);
        ii1 = new ImageIcon(scaledImage1);
        setImage(ii1.getImage());

    }













}
