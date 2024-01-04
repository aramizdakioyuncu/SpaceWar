package Spaceboom.sprite;

import Spaceboom.Commons;
import Spaceboom.Utility.ControlsSetting;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {
    public int width;
    public int length;
    public int speedX = 9;
    public int speedY = 6;

    public boolean yukariHareket = false;
    public boolean asagiHareket = false;
    public boolean sagHareket = false;
    public boolean solHareket = false;
    public static int totalShotCount = 0;
    public Shot shot;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {

        String playerImg = "/images/spaceship.png";
        ImageIcon ii = new ImageIcon(getClass().getResource(playerImg));

        width = (ii.getIconWidth() / 7);
        length = (ii.getIconHeight() / 7);
        Commons.PLAYER_WIDTH = width;
        Commons.PLAYER_HEIGHT = length;
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(width, length, java.awt.Image.SCALE_SMOOTH);


        ii = new ImageIcon(scaledImage);
        setImage(ii.getImage());


        int START_X = 900;
        setX(START_X);

        int START_Y = 900;
        setY(START_Y);
    }


    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A || key == ControlsSetting.left) {

            solHareket = true;
        }

        if (key == KeyEvent.VK_D || key == ControlsSetting.right) {

            sagHareket = true;
        }
        if (key == KeyEvent.VK_W || key == ControlsSetting.up) {

            yukariHareket = true;

        }
        if (key == KeyEvent.VK_S || key == ControlsSetting.down) {

            asagiHareket = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A || key == ControlsSetting.left) {

            solHareket = false;
        }

        if (key == KeyEvent.VK_D || key == ControlsSetting.right) {

            sagHareket = false;
        }
        if (key == KeyEvent.VK_W || key == ControlsSetting.up) {

            yukariHareket =false;

        }
        if (key == KeyEvent.VK_S || key == ControlsSetting.down) {

            asagiHareket = false;
        }
    }
}

