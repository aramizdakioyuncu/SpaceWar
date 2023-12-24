package Spaceboom.sprite;

import javax.swing.*;
import java.awt.*;


public class AttackSpeed extends Sprite {

    final String AttackSpeedImgPath = "/images/AttackSpeed.png";
    private Image AttackSpeedImg;
    public int counter = 1;
    public boolean isTake = false;

    public AttackSpeed(int x, int y) {
        this.x = x;
        this.y = y;
        initAttackSpeed();

    }

    private void initAttackSpeed() {

        ImageIcon AttackSpeedIcon = new ImageIcon(getClass().getResource(AttackSpeedImgPath));
        AttackSpeedImg = AttackSpeedIcon.getImage();

        ImageIcon ii = new ImageIcon(AttackSpeedImg);
        int newWidth = (ii.getIconWidth() / 9);
        int newHeight = (ii.getIconHeight() / 9);
        Image scaledImage = ii.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        setImage(ii.getImage());

    }


}
