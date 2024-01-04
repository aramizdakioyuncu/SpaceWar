package Spaceboom.Services;

import Spaceboom.Commons;
import Spaceboom.sprite.Alien;
import Spaceboom.sprite.BoardData;
import Spaceboom.sprite.Player;
import Spaceboom.sprite.Shot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class PlayerService {

    SoundPlayer splayer = new SoundPlayer();


    public boolean drawPlayer(Graphics g, Player player, ImageObserver observer){
        if (player.isVisible()) {
            g.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(), observer);
        }
        if (player.isDying()) {
            splayer.playAsync("explosion-80108.wav");

            player.die();
            return false;
        }
        return true;
    }

    public void drawPlayerBullet(Shot shot, Graphics g, ImageObserver observer){
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), (int) shot.getX(), (int) shot.getY(), observer);
        }
    }

    public void playerBulletHandler(BoardData boardData){
        if (boardData.player.shot.isVisible()) {
            for (Alien alien : boardData.aliens) {
                if (alien.isVisible() && boardData.player.shot.isVisible()) {
                    if (boardData.player.shot.getX() >= (alien.getX())
                            && boardData.player.shot.getX() <= (alien.getX() + Commons.ALIEN_WIDTH)
                            && boardData.player.shot.getY() >= (alien.getY())
                            && boardData.player.shot.getY() <= (alien.getY() + Commons.ALIEN_HEIGHT)) {

                        ImageIcon ii = new ImageIcon(getClass().getResource(boardData.explImg));

                        alien.setImage(ii.getImage());
                        double width4 = ((double) ii.getIconWidth() / 12);
                        double length4 = ((double) ii.getIconHeight() / 8);
                        Image scaledImage = ii.getImage().getScaledInstance((int) width4, (int) length4, Image.SCALE_SMOOTH);
                        ii = new ImageIcon(scaledImage);

                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        boardData.deaths++;

                        System.out.println("Uzaylı Vuruldu");

                        boardData.player.shot.die();
                    }
                }
            }

            double y = boardData.player.shot.getY();
            y -= Shot.speed;

            if (y < 0) {
                boardData.player.shot.die();
            } else {
                boardData.player.shot.setY(y);
            }
        }

    }


    public void playerDeathControl(BoardData boardData, Alien.Bomb bomb){
        if (boardData.player.isVisible() && !bomb.isDestroyed()) {
            if (bomb.getX() >= (boardData.player.getX())
                    && bomb.getX() <= (boardData.player.getX() + Commons.PLAYER_WIDTH)
                    && bomb.getY() >= (boardData.player.getY())
                    && bomb.getY() <= (boardData.player.getY() + Commons.PLAYER_HEIGHT)) {

                ImageIcon ii = new ImageIcon(getClass().getResource(boardData.explImg));
                boardData.player.setImage(ii.getImage());
                double width4 = ((double) ii.getIconWidth() / 5);
                double length4 = ((double) ii.getIconHeight() / 4);
                Image scaledImage = ii.getImage().getScaledInstance((int) width4, (int) length4, Image.SCALE_SMOOTH);
                ii = new ImageIcon(scaledImage);
                boardData.player.setImage(ii.getImage());
                boardData.player.setDying(true);
                bomb.setDestroyed(true);


            }
        }
    }

    public void act(BoardData boardData) {

        if (boardData.player.yukariHareket){
            boardData.player.setY(boardData.player.getY()- boardData.player.speedY);
        }
        if (boardData.player.asagiHareket){
            boardData.player.setY(boardData.player.getY()+ boardData.player.speedY);
        }
        if (boardData.player.solHareket){
            boardData.player.setX(boardData.player.getX()- boardData.player.speedX);
        }
        if (boardData.player.sagHareket){
            boardData.player.setX(boardData.player.getX()+ boardData.player.speedX);
        }

        if (boardData.player.getX() <= 2) {

            boardData.player.setX(2);
        }

        if (boardData.player.getX() >= Commons.BOARD_WIDTH - 2 * boardData.player.width) {

            boardData.player.setX(Commons.BOARD_WIDTH - 2 * boardData.player.width);
        }

        if (boardData.player.getY() <= 2) {

            boardData.player.setY(2);
        }
        if (boardData.player.getY() >= Commons.BOARD_HEIGHT - 2 * boardData.player.length){
            boardData.player.setY(Commons.BOARD_HEIGHT - 2 * boardData.player.length);
        }
    }

    public void resetLocation(Player player){
        player.setX(900);
        player.setY(900);
    }
}
