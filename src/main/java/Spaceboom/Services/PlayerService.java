package Spaceboom.Services;

import Spaceboom.Commons;
import Spaceboom.DTOS.BoardDTO;
import Spaceboom.Utility.SoundPlayer;
import Spaceboom.sprite.Alien;
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

    public void playerBulletHandler(BoardDTO boardDTO){
        if (boardDTO.player.shot.isVisible()) {
            for (Alien alien : boardDTO.aliens) {
                if (alien.isVisible() && boardDTO.player.shot.isVisible()) {
                    if (boardDTO.player.shot.getX() >= (alien.getX())
                            && boardDTO.player.shot.getX() <= (alien.getX() + Commons.ALIEN_WIDTH)
                            && boardDTO.player.shot.getY() >= (alien.getY())
                            && boardDTO.player.shot.getY() <= (alien.getY() + Commons.ALIEN_HEIGHT)) {

                        ImageIcon ii = new ImageIcon(getClass().getResource(boardDTO.explImg));

                        alien.setImage(ii.getImage());
                        double width4 = ((double) ii.getIconWidth() / 12);
                        double length4 = ((double) ii.getIconHeight() / 8);
                        Image scaledImage = ii.getImage().getScaledInstance((int) width4, (int) length4, Image.SCALE_SMOOTH);
                        ii = new ImageIcon(scaledImage);

                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        boardDTO.deaths++;
                        boardDTO.player.shot.die();
                    }
                }
            }

            double y = boardDTO.player.shot.getY();
            y -= Shot.speed;

            if (y < 0) {
                boardDTO.player.shot.die();
            } else {
                boardDTO.player.shot.setY(y);
            }
        }

    }


    public void playerDeathControl(BoardDTO boardDTO, Alien.Bomb bomb){
        if (boardDTO.player.isVisible() && !bomb.isDestroyed()) {
            if (bomb.getX() >= (boardDTO.player.getX())
                    && bomb.getX() <= (boardDTO.player.getX() + Commons.PLAYER_WIDTH)
                    && bomb.getY() >= (boardDTO.player.getY())
                    && bomb.getY() <= (boardDTO.player.getY() + Commons.PLAYER_HEIGHT)) {

                ImageIcon ii = new ImageIcon(getClass().getResource(boardDTO.explImg));
                boardDTO.player.setImage(ii.getImage());
                double width4 = ((double) ii.getIconWidth() / 5);
                double length4 = ((double) ii.getIconHeight() / 4);
                Image scaledImage = ii.getImage().getScaledInstance((int) width4, (int) length4, Image.SCALE_SMOOTH);
                ii = new ImageIcon(scaledImage);
                boardDTO.player.setImage(ii.getImage());
                boardDTO.player.setDying(true);
                bomb.setDestroyed(true);
            }
        }
    }

    public void act(BoardDTO boardDTO) {


        if (boardDTO.player.yukariHareket){
            boardDTO.player.setY(boardDTO.player.getY()-boardDTO.player.speedY);
        }
        if (boardDTO.player.asagiHareket){
            boardDTO.player.setY(boardDTO.player.getY()+boardDTO.player.speedY);
        }
        if (boardDTO.player.solHareket){
            boardDTO.player.setX(boardDTO.player.getX()-boardDTO.player.speedX);
        }
        if (boardDTO.player.sagHareket){
            boardDTO.player.setX(boardDTO.player.getX()+boardDTO.player.speedX);
        }

        if (boardDTO.player.getX() <= 2) {

            boardDTO.player.setX(2);
        }

        if (boardDTO.player.getX() >= Commons.BOARD_WIDTH - 2 * boardDTO.player.width) {

            boardDTO.player.setX(Commons.BOARD_WIDTH - 2 * boardDTO.player.width);
        }

        if (boardDTO.player.getY() <= 2) {

            boardDTO.player.setY(2);
        }
        if (boardDTO.player.getY() >= Commons.BOARD_HEIGHT - 2 * boardDTO.player.length){
            boardDTO.player.setY(Commons.BOARD_HEIGHT - 2 * boardDTO.player.length);
        }
    }
}
