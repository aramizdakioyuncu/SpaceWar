package Spaceboom.Services;

import Spaceboom.sprite.AttackSpeed;
import Spaceboom.sprite.Player;
import Spaceboom.sprite.Shot;
import Spaceboom.sprite.SpeedUp;

import java.awt.*;
import java.awt.image.ImageObserver;

import static Spaceboom.Commons.MAX_GAME_TIME;

public class SpecialAbilityService {
    public long speedUpHandler(Graphics g, ImageObserver observer, double sayac, SpeedUp speedUp, Player player, int farkLimiti, long speedUpBaslangicZaman){
        if (MAX_GAME_TIME*0.03<sayac && sayac < MAX_GAME_TIME*0.09) {
            if (speedUp.counter==1&&!speedUp.isTake){
                if ((Math.abs(player.getX() - speedUp.getX()) <= farkLimiti) && (Math.abs(player.getY() - speedUp.getY()) <= farkLimiti)){
                    speedUp.isTake=true;
                    speedUpBaslangicZaman = System.currentTimeMillis();
                    player.speedX *=2;
                    player.speedY *=2;
                    speedUp.counter++;
                }
                g.drawImage(speedUp.getImage(), (int)speedUp.getX(), (int) speedUp.getY(), observer);
            }
        } else if (sayac>MAX_GAME_TIME*0.09 && speedUp.counter==1) {
            speedUp.counter++;
        } else if (MAX_GAME_TIME*0.12<sayac && sayac < MAX_GAME_TIME*0.3) {
            if (speedUp.counter==2){
                speedUp.setX((Math.random() * 1830));
                speedUp.setY((Math.random() * 400) + 200);
                speedUp.counter++;
            }
            if (speedUp.counter==3&&!speedUp.isTake){
                if ((Math.abs(player.getX() - speedUp.getX()) <= farkLimiti) && (Math.abs(player.getY() - speedUp.getY()) <= farkLimiti)){
                    speedUp.isTake=true;
                    speedUpBaslangicZaman = System.currentTimeMillis();
                    player.speedX *=2;
                    player.speedY *=2;
                    speedUp.counter++;
                }
                g.drawImage(speedUp.getImage(), (int)speedUp.getX(), (int)speedUp.getY(), observer);
            }
        }

        if (speedUp.isTake && speedUpBaslangicZaman != 0){
            if ((int) ((System.currentTimeMillis()-speedUpBaslangicZaman)/1000) > 3){
                player.speedY /=2;
                player.speedX /=2;
                speedUp.isTake = false;
            }
        }

        return speedUpBaslangicZaman;
    }


    public long attackSpeedHandler(Graphics g, ImageObserver observer, double sayac, AttackSpeed attackSpeed, Player player, int farkLimiti, long attackSpeedBaslangicZaman, Shot shot){

        if (MAX_GAME_TIME*0.03<sayac && sayac < MAX_GAME_TIME*0.09) {
            if (attackSpeed.counter==1&&!attackSpeed.isTake){
                if ((Math.abs(player.getX() - attackSpeed.getX()) <= farkLimiti) && (Math.abs(player.getY() - attackSpeed.getY()) <= farkLimiti)){
                    attackSpeed.isTake=true;
                    attackSpeed.counter++;
                    attackSpeedBaslangicZaman = System.currentTimeMillis();
                }
                g.drawImage(attackSpeed.getImage(), (int)attackSpeed.getX(), (int)attackSpeed.getY(), observer);
            }
        } else if (sayac>MAX_GAME_TIME*0.09 && attackSpeed.counter==1) {
            attackSpeed.counter++;
        } else if (MAX_GAME_TIME*0.12<sayac && sayac < MAX_GAME_TIME*0.3) {
            if (attackSpeed.counter==2){
                attackSpeed.setX((Math.random() * 1830));
                attackSpeed.setY((Math.random() * 400) + 200);
                attackSpeed.counter++;
            }
            if (attackSpeed.counter==3&&!attackSpeed.isTake){
                if ((Math.abs(player.getX() - attackSpeed.getX()) <= farkLimiti) && (Math.abs(player.getY() - attackSpeed.getY()) <= farkLimiti)){
                    attackSpeed.isTake=true;
                    attackSpeed.counter++;
                    attackSpeedBaslangicZaman = System.currentTimeMillis();
                }
                g.drawImage(attackSpeed.getImage(), (int)attackSpeed.getX(), (int)attackSpeed.getY(), observer);
            }
        }
        if (attackSpeed.isTake && attackSpeedBaslangicZaman != 0){
            if ((int) ((System.currentTimeMillis()-attackSpeedBaslangicZaman)/1000) > 3){
                attackSpeed.isTake = false;
            }
        }

        if (attackSpeed.isTake){
            Shot.speed = 20;
        }else {
            shot.speed = 10;
        }
        return attackSpeedBaslangicZaman;
    }
}
