package Spaceboom.sprite;

import Spaceboom.Commons;
import Spaceboom.Services.AlienService;

import javax.swing.*;

public class Alien extends Sprite {
    private BoardData boardData = new BoardData();
    private Bomb bomb;
    public static int bombSpeed;

    public Alien(int x, int y,AlienService alienService) {
        bombSpeed = Commons.bulletSpeed;
        initAlien(x, y, boardData,alienService);
    }

    private void initAlien(int x, int y, BoardData boardData, AlienService alienService) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        alienService.setAlienImage(boardData.level,this);
    }


    public void act(int direction) {

        this.x = (x + direction);
    }

    public Bomb getBomb() {

        return bomb;
    }

    public class Bomb extends Sprite {

        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            String bombImg = "/images/alienlaser.png";
            ImageIcon ii = new ImageIcon(getClass().getResource(bombImg));

            setImage(ii.getImage());
            double width1 = ((double) ii.getIconWidth() / 30);
            double length1 = ((double) ii.getIconHeight() / 60);
            java.awt.Image scaledImage = ii.getImage().getScaledInstance((int) width1, (int) length1, java.awt.Image.SCALE_SMOOTH);
            ii = new ImageIcon(scaledImage);
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }


    }
}
