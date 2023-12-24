package Spaceboom.Screens;

import Spaceboom.Board;

import javax.swing.*;

public class GameScreen {

    public JFrame frame;
    public GameScreen() {

        frame = new JFrame();
        frame.setTitle("Space Invaders || Game");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocation(0, 0); // Pencereyi sol üst köşeye yerleştir

        frame.add(new Board(frame));
        frame.setVisible(true);
    }



}
