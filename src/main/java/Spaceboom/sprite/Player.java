package Spaceboom.sprite;

import Spaceboom.Board;
import Spaceboom.Commons;
import Spaceboom.Services.PlayerService;
import Spaceboom.Utility.ControlsSetting;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {
    private BoardData boardData = new BoardData();
    public int width;
    public int length;
    public int speedX = 11;
    public int speedY = 7;

    public boolean yukariHareket = false;
    public boolean asagiHareket = false;
    public boolean sagHareket = false;
    public boolean solHareket = false;
    public static int totalShotCount = 0;
    public Shot shot;

    public Player( PlayerService playerService) {
        initPlayer(boardData,playerService);
    }

    private void initPlayer(BoardData boardData, PlayerService playerService) {
        playerService.setPlayerImage(boardData.level,this);
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

