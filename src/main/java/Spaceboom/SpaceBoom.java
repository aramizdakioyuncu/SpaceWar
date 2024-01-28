package Spaceboom;

import Spaceboom.API.APP_FUNCTION;
import Spaceboom.Screens.IntroScreen;

import javax.swing.*;

public class SpaceBoom extends JFrame  {
    public static JFrame Jframe_Game = new JFrame("SpaceBoom Games");

    public static void main(String[] args) {
        String playerImg = "/images/spaceship6.png";
        ImageIcon ii = new ImageIcon(SpaceBoom.class.getResource(playerImg));

        Jframe_Game.setIconImage(ii.getImage());

        APP_FUNCTION.setFullScreen(Jframe_Game);

        new IntroScreen(Jframe_Game);

    }
}






