package com.zetcode.sprite;

import java.awt.*;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class AttackSpeed extends Sprite {

    final String AttackSpeedImgPath = "src/images/AttackSpeed.png";
    private Image AttackSpeedImg;

    public AttackSpeed() {
        initAttackSpeed();
    }

    private void initAttackSpeed() {

        ImageIcon AttackSpeedIcon = new ImageIcon(AttackSpeedImgPath);
        AttackSpeedImg = AttackSpeedIcon.getImage();

        var ii = new ImageIcon(AttackSpeedImg);
        int newWidth = (ii.getIconWidth() / 9);
        int newHeight = (ii.getIconHeight() / 9);
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        setImage(ii.getImage());





        }

}
