package com.zetcode.sprite;

import com.zetcode.Commons;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private int width;
    private int length;



    public Player() {

        initPlayer();
    }

    private void initPlayer() {

        var playerImg = "src/images/spaceship.png";
        var ii = new ImageIcon(playerImg);


         width = (ii.getIconWidth() / 7 );
         length = (ii.getIconHeight() / 7);
        Commons.PLAYER_WIDTH = width;
        Commons.PLAYER_HEIGHT = length;
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(width, length, java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        setImage(ii.getImage());


        int START_X = 900;
        setX(START_X);

        int START_Y = 800;
        setY(START_Y);
    }

    public void act() {


        x += dx;
        y += dy;

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }

        if (y <= 2) {

            y = 2;
        }
        if (y >= Commons.BOARD_HEIGHT - 2 * length){

            y = Commons.BOARD_HEIGHT - 2 * length;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -8;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 8;
        }
        if (key == KeyEvent.VK_UP) {

            dy = -6;

        }
        if (key == KeyEvent.VK_DOWN){

            dy = 6;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_DOWN) {

           dy = 0;
        }

        if (key == KeyEvent.VK_UP ) {

            dy = 0;
        }

    }





    }

