package Spaceboom.Screens;

import Spaceboom.Board;

import javax.swing.*;
import java.awt.*;

public class GameScreen {

    public GameScreen() {

        JFrame frame = new JFrame();
        frame.setTitle("Space Invaders || Game");
        frame.setUndecorated(true); // Tam ekran modunu etkinleştir
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ekran boyutuna göre ayarla
        frame.setLocation(0, 0); // Pencereyi sol üst köşeye yerleştir


        frame.add(new Board(frame));
        frame.setVisible(true);
    }



}
