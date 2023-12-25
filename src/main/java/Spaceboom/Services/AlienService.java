package Spaceboom.Services;

import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.Commons;
import Spaceboom.DTOS.BoardDTO;
import Spaceboom.Utility.SoundPlayer;
import Spaceboom.sprite.Alien;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    public void alienActHandler(BoardDTO boardDTO){
        for (Alien alien : boardDTO.aliens) {
            double x = alien.getX();
            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && boardDTO.direction != -1) {
                boardDTO.direction = -5;
                for (Alien a2 : boardDTO.aliens) {
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }
            if (x <=  Commons.BORDER_LEFT&& boardDTO.direction != 1) {
                boardDTO.direction = 5;
                for (Alien a : boardDTO.aliens) {
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

    public void alienDeathsControl(BoardDTO boardDTO){
        if (boardDTO.deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            boardDTO.inGame = false;
            boardDTO.timer.stop();
            boardDTO.message = "YOU WIN!";
            boardDTO.sayac = Commons.MAX_GAME_TIME - boardDTO.sayac;
            String formData = "skor="+ boardDTO.sayac;
            if(USER.username != null){
                CompletableFuture<JSONObject> postRequestFuture = FUNCTION.SaveScore2(formData);
                postRequestFuture.thenAcceptAsync(response -> {
                    JSONObject cevap = response;
                    System.out.println(cevap.get("aciklama"));
                });
            }
        }
    }

    public void isAliensOutFrame(BoardDTO boardDTO){
        for (Alien alien : boardDTO.aliens) {
            if (alien.isVisible()) {
                double y = alien.getY();
                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    boardDTO.inGame = false;
                    boardDTO.message = "Invasion!";
                }
                alien.act(boardDTO.direction);
            }
        }
    }
}