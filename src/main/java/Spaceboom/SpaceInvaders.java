package Spaceboom;

import Spaceboom.API.API;
import Spaceboom.API.APP_FUNCTION;
import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.Utility.Items;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class SpaceInvaders extends JFrame  {
    private JPanel Jpanel_Game;

    public SpaceInvaders() {

        JFrame frame = new JFrame();
        frame.setTitle("Space Invaders || Game");
        frame.setUndecorated(true); // Tam ekran modunu etkinleştir
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ekran boyutuna göre ayarla
        frame.setLocation(0, 0); // Pencereyi sol üst köşeye yerleştir

        frame.add(new Board(frame));
        frame.setVisible(true);

    }
}


