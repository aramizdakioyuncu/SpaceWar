package Spaceboom;
import Spaceboom.API.APP_FUNCTION;
import Spaceboom.Screens.IntroScreen;
import Spaceboom.Utility.SoundPlayer;
import javax.swing.*;


public class SpaceBoom extends JFrame  {


    private static JFrame Jframe_Game = new JFrame("SpaceBoom Games");

    public static void main(String[] args) {
        String playerImg = "/images/spaceship.png";
        ImageIcon ii = new ImageIcon(SpaceBoom.class.getResource(playerImg));

        //setGameIcon
        Jframe_Game.setIconImage(ii.getImage());

        //setFullScreen
        APP_FUNCTION.setFullScreen(Jframe_Game);
        APP_FUNCTION.setBorderScreen(Jframe_Game);

        //Music Starting
        SoundPlayer.playAsync("start.wav");

        //Intro
        new IntroScreen(Jframe_Game);


    }
}


