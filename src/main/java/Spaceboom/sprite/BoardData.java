package Spaceboom.sprite;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoardData {
    public int level = 1;
    public boolean loading = true;
    public long loadingBaslangicZaman;
    public String format = "3,33";
    public Dimension d;
    public List<Alien> aliens;
    public Player player;
    public AttackSpeed attackSpeed;
    public SpeedUp speedUp;
    public int farklimiti = 40;
    public int direction = -5;
    public int deaths = 0;
    public double sayac = 0;
    public long ekZamanBaslangic = 0;
    public long ekZamanBitis = 0;
    public double ekZaman = 0;
    public long baslangicZaman = System.currentTimeMillis();
    public long speedUpBaslangicZaman = 0;
    public long attackSpeedBaslangicZaman = 0;
    public boolean inGame = true;
    public final String explImg = "/images/explosion.png";
    public final String backgroundImgPath = "/gif/backround.gif";
    public Image backgroundImage;
    public final String finishgraundImgPath = "/gif/finishgraund.gif";
    public Image finishgraundImage;
    public final String gameoverImgPath =  "/gif/gameover.gif";
    public Image gameoverImage;
    public String message = "YOU LOST";
    public Timer timer;
    public JFrame frameGame;
}
