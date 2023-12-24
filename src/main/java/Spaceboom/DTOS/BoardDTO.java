package Spaceboom.DTOS;

import Spaceboom.sprite.Alien;
import Spaceboom.sprite.AttackSpeed;
import Spaceboom.sprite.Player;
import Spaceboom.sprite.SpeedUp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoardDTO {
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
    public final String backgroundImgPath = "/images/background.jpg";
    public Image backgroundImage;
    public final String finishgraundImgPath = "/images/finishgraund.jpg";
    public Image finishgraundImage;
    public final String gameoverImgPath = "/images/gameover.jpg";
    public Image gameoverImage;
    public String message = "YOU LOST";
    public Timer timer;
    public JFrame frameGame;
}
