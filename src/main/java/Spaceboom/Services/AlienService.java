package Spaceboom.Services;

import Spaceboom.Commons;
import Spaceboom.sprite.Alien;
import Spaceboom.sprite.BoardData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.List;

public class AlienService {

    public void drawAliens(Graphics g, List<Alien> aliens, ImageObserver observer){
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), (int) alien.getX(), (int) alien.getY(), observer);
            }
            if (alien.isDying()) {

                alien.die();

            }
        }
    }

    public void alienBulletHandler(List<Alien> aliens, Graphics g, ImageObserver observer){
        for (Alien a : aliens) {
            Alien.Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), (int) b.getX(), (int) b.getY(), observer);
            }
        }
    }

    public void alienActHandler(BoardData boardData){
        for (Alien alien : boardData.aliens) {
            double x = alien.getX();
            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && boardData.direction != -1) {
                boardData.direction = -5;
                for (Alien a2 : boardData.aliens) {
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }
            if (x <=  Commons.BORDER_LEFT&& boardData.direction != 1) {
                boardData.direction = 5;
                for (Alien a : boardData.aliens) {
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }
    }

    public void alienBombHandler(Alien.Bomb bomb){
        if (!bomb.isDestroyed()) {
            bomb.setY(bomb.getY() + Alien.bombSpeed);
            if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
                bomb.setDestroyed(true);
            }
        }
    }

    public void alienDeathsControl(BoardData boardData,GameService gameService,PlayerService playerService){
        if (boardData.deaths == boardData.level *Commons.NUMBER_OF_ALIENS_TO_DESTROY && Commons.levelCount > boardData.level){
            boardData.level++;
            gameService.levelUp(boardData,this,playerService);
        }else if (boardData.level == Commons.levelCount && Commons.NUMBER_OF_ALIENS_TO_DESTROY*Commons.levelCount == boardData.deaths){
            boardData.inGame = false;
            boardData.timer.stop();
            boardData.message = "YOU WIN!";
            boardData.sayac = Commons.MAX_GAME_TIME - boardData.sayac;

        }
    }

    public void isAliensOutFrame(BoardData boardData){
        for (Alien alien : boardData.aliens) {
            if (alien.isVisible()) {
                double y = alien.getY();
                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    boardData.inGame = false;
                    boardData.message = "Invasion!";
                }
                alien.act(boardData.direction);
            }
        }
    }

    public void resetLocation(List<Alien> aliens, BoardData boardData){
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = aliens.get(k);
                alien.setX(Commons.ALIEN_INIT_X + 80 * j);
                alien.setY(Commons.ALIEN_INIT_Y + 45 * i);
                alien.getBomb().setX(alien.getX());
                alien.getBomb().setY(alien.getY());
                alien.setDying(false);
                alien.setVisible(true);

                setAlienImage(boardData.level,alien);

                k++;
            }
        }

    }

    public void setAlienImage(int level, Alien alien){
        String alienImg = "/images/alien"+level+".png";
        ImageIcon ii = new ImageIcon(getClass().getResource(alienImg));
        int newWidth = (ii.getIconWidth() / 11);
        int newHeight = (ii.getIconHeight() / 11);
        Image scaledImage = ii.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ii = new ImageIcon(scaledImage);
        alien.setImage(ii.getImage());
    }

    public void setBulletSpeed(int speed){
        Alien.bombSpeed = speed;
    }


}