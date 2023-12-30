package Spaceboom.sprite;

import Spaceboom.Commons;

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

    public Shot shot;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {

        String playerImg = "/images/spaceship.png";
        ImageIcon ii = new ImageIcon(getClass().getResource(playerImg));

         width = (ii.getIconWidth() / 7 );
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

        if (key == KeyEvent.VK_LEFT || key==KeyEvent.VK_A) {

            solHareket = true;
        }

        if (key == KeyEvent.VK_RIGHT || key==KeyEvent.VK_D) {

            sagHareket = true;
        }
        if (key == KeyEvent.VK_UP || key==KeyEvent.VK_W) {

            yukariHareket = true;

        }
        if (key == KeyEvent.VK_DOWN || key==KeyEvent.VK_S){

            asagiHareket = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key==KeyEvent.VK_A) {
            solHareket = false;
        }

        if (key == KeyEvent.VK_RIGHT || key==KeyEvent.VK_D) {
            sagHareket = false;
        }

        if (key == KeyEvent.VK_DOWN || key==KeyEvent.VK_S) {
           asagiHareket = false;
        }

        if (key == KeyEvent.VK_UP || key==KeyEvent.VK_W ) {
            yukariHareket = false;
        }

    }
}

