package Spaceboom.Services;

import Spaceboom.Commons;
import Spaceboom.sprite.AttackSpeed;
import Spaceboom.sprite.Player;
import Spaceboom.sprite.Shot;
import Spaceboom.sprite.SpeedUp;

import java.awt.*;
import java.awt.image.ImageObserver;

import static Spaceboom.Commons.MAX_GAME_TIME;



//        if (MAX_GAME_TIME*0.1<sayac && sayac < MAX_GAME_TIME*0.2) {
//            if (speedUp.counter==1&&!speedUp.isTake){
//                if ((Math.abs(player.getX() - speedUp.getX()) <= farkLimiti) && (Math.abs(player.getY() - speedUp.getY()) <= farkLimiti)){
//                    speedUp.isTake=true;
//                    speedUpBaslangicZaman = System.currentTimeMillis();
//                    player.speedX *=2;
//                    player.speedY *=2;
//                    speedUp.counter++;
//                }
//                g.drawImage(speedUp.getImage(), (int)speedUp.getX(), (int) speedUp.getY(), observer);
//            }
//        } else if (sayac>MAX_GAME_TIME*0.2 && speedUp.counter==1) {
//            speedUp.counter++;
//        } else if (MAX_GAME_TIME*0.3<sayac && sayac < MAX_GAME_TIME*0.4) {
//            if (speedUp.counter==2){
//                speedUp.setX((Math.random() * 1830));
//                speedUp.setY((Math.random() * 400) + 200);
//                speedUp.counter++;
//            }
//            if (speedUp.counter==3&&!speedUp.isTake){
//                if ((Math.abs(player.getX() - speedUp.getX()) <= farkLimiti) && (Math.abs(player.getY() - speedUp.getY()) <= farkLimiti)){
//                    speedUp.isTake=true;
//                    speedUpBaslangicZaman = System.currentTimeMillis();
//                    player.speedX *=2;
//                    player.speedY *=2;
//                    speedUp.counter++;
//                }
//                g.drawImage(speedUp.getImage(), (int)speedUp.getX(), (int)speedUp.getY(), observer);
//            }
//        }
//


//        if (MAX_GAME_TIME*0.1<sayac && sayac < MAX_GAME_TIME*0.2) {
//            if (attackSpeed.counter==1&&!attackSpeed.isTake){
//                if ((Math.abs(player.getX() - attackSpeed.getX()) <= farkLimiti) && (Math.abs(player.getY() - attackSpeed.getY()) <= farkLimiti)){
//                    attackSpeed.isTake=true;
//                    attackSpeed.counter++;
//                    attackSpeedBaslangicZaman = System.currentTimeMillis();
//                }
//                g.drawImage(attackSpeed.getImage(), (int)attackSpeed.getX(), (int)attackSpeed.getY(), observer);
//            }
//        } else if (sayac>MAX_GAME_TIME*0.2 && attackSpeed.counter==1) {
//            attackSpeed.counter++;
//        } else if (MAX_GAME_TIME*0.3<sayac && sayac < MAX_GAME_TIME*0.4) {
//            if (attackSpeed.counter==2){
//                attackSpeed.setX((Math.random() * 1830));
//                attackSpeed.setY((Math.random() * 400) + 200);
//                attackSpeed.counter++;
//            }
//            if (attackSpeed.counter==3&&!attackSpeed.isTake){
//                if ((Math.abs(player.getX() - attackSpeed.getX()) <= farkLimiti) && (Math.abs(player.getY() - attackSpeed.getY()) <= farkLimiti)){
//                    attackSpeed.isTake=true;
//                    attackSpeed.counter++;
//                    attackSpeedBaslangicZaman = System.currentTimeMillis();
//                }
//                g.drawImage(attackSpeed.getImage(), (int)attackSpeed.getX(), (int)attackSpeed.getY(), observer);
//            }
//        }


public class SpecialAbilityService {
    public long speedUpHandler(Graphics g, ImageObserver observer, double sayac, SpeedUp speedUp, Player player, int farkLimiti, long speedUpBaslangicZaman){
        if (!speedUp.isTake){
            int kacinciParcadayiz = kacinciParcadayiz(sayac);
            if (MAX_GAME_TIME-sayac < Commons.ozelGucKacSaniyedeBirCiksin*kacinciParcadayiz && MAX_GAME_TIME-sayac >Commons.ozelGucKacSaniyedeBirCiksin*(kacinciParcadayiz-1)){
                if (Commons.ozelGucKacSaniyedeBirCiksin*kacinciParcadayiz-Commons.ozelGucKacSaniyeDursun<MAX_GAME_TIME-sayac){
                    g.drawImage(speedUp.getImage(), (int)speedUp.getX(), (int) speedUp.getY(), observer);
                    if ((Math.abs(player.getX() - speedUp.getX()) <= farkLimiti) && (Math.abs(player.getY() - speedUp.getY()) <= farkLimiti)){
                        speedUp.isTake=true;
                        speedUpBaslangicZaman = System.currentTimeMillis();
                        player.speedX *=2;
                        player.speedY *=2;
                        speedUp.setX((Math.random() * 1400) +200);
                        speedUp.setY((Math.random() * 1000) +400);
                    }
                }
            }
        }
        if (speedUp.isTake && speedUpBaslangicZaman != 0){
            if (((System.currentTimeMillis()-speedUpBaslangicZaman)/1000) >= Commons.ozelGucKacSaniyeKullanilsin){
                player.speedY /=2;
                player.speedX /=2;
                speedUp.isTake = false;
            }
        }
        return speedUpBaslangicZaman;
    }


    public long attackSpeedHandler(Graphics g, ImageObserver observer, double sayac, AttackSpeed attackSpeed, Player player, int farkLimiti, long attackSpeedBaslangicZaman, Shot shot){
        if (!attackSpeed.isTake){
            int kacinciParcadayiz = kacinciParcadayiz(sayac);

            if (MAX_GAME_TIME-sayac < Commons.ozelGucKacSaniyedeBirCiksin*kacinciParcadayiz && MAX_GAME_TIME-sayac >Commons.ozelGucKacSaniyedeBirCiksin*(kacinciParcadayiz-1)){
                if (Commons.ozelGucKacSaniyedeBirCiksin*kacinciParcadayiz-Commons.ozelGucKacSaniyeDursun<MAX_GAME_TIME-sayac){
                    g.drawImage(attackSpeed.getImage(), (int)attackSpeed.getX(), (int) attackSpeed.getY(), observer);
                    if ((Math.abs(player.getX() - attackSpeed.getX()) <= farkLimiti) && (Math.abs(player.getY() - attackSpeed.getY()) <= farkLimiti)){
                        attackSpeed.isTake=true;
                        attackSpeedBaslangicZaman = System.currentTimeMillis();
                        Shot.speed = 80;
                        attackSpeed.setX((Math.random() * 1400) + 200);
                        attackSpeed.setY((Math.random() * 1000) + 400);
                    }
                }
            }
        }
        if (attackSpeed.isTake && attackSpeedBaslangicZaman != 0){
            if (((System.currentTimeMillis()-attackSpeedBaslangicZaman)/1000) >= Commons.ozelGucKacSaniyeKullanilsin){
                attackSpeed.isTake = false;
                Shot.speed = Commons.shotSpeed;
            }
        }
        return attackSpeedBaslangicZaman;
    }

    private int kacinciParcadayiz(double sayac){
        for (int j = 1; j <= Commons.parca; j++) {
            if (Commons.ozelGucKacSaniyedeBirCiksin*j>MAX_GAME_TIME-sayac){
                return j;
            }
        }
        return 0;
    }
}
