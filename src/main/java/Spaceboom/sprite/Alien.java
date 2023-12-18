package Spaceboom.sprite;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

    private Bomb bomb;

    public Alien(int x, int y) {

        initAlien(x, y);
    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        String alienImg = "/images/alien.png";
        ImageIcon ii = new ImageIcon(getClass().getResource(alienImg));
        int newWidth = (ii.getIconWidth() / 11);
        int newHeight = (ii.getIconHeight() / 11);
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);

        setImage(ii.getImage());
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
