package Spaceboom.sprite;

import javax.swing.*;
import java.awt.*;

public class SpeedUp extends Sprite{

    final String SpeedUpImgPath = "/images/SpeedUp.png";
    public Image SpeedUpImg;
    public int counter = 1;
    public boolean counterArttirilabilirlik = true;
    public boolean alinabilirlik = true;
    public boolean isTake = false;

    public SpeedUp(int x, int y){
        this.x = x;
        this.y = y;
        initSpeedUp();
    }

    private void initSpeedUp(){

        ImageIcon powerUpIcon2 = new ImageIcon(getClass().getResource(SpeedUpImgPath));
        SpeedUpImg = powerUpIcon2.getImage();

        ImageIcon ii1 = new ImageIcon(SpeedUpImg);
        int newWidth1 = (ii1.getIconWidth() / 25);
        int newHeight1 = (ii1.getIconHeight() / 25);
        Image scaledImage1 = ii1.getImage().getScaledInstance(newWidth1, newHeight1, Image.SCALE_SMOOTH);
        ii1 = new ImageIcon(scaledImage1);
        setImage(ii1.getImage());

    }













}
