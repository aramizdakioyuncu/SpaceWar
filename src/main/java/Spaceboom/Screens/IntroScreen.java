package Spaceboom.Screens;

import Spaceboom.API.API;
import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.SpaceInvaders;
import Spaceboom.Utility.Items;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class IntroScreen {
    private JPanel Jpanel_Game;

    public IntroScreen(JFrame Jframe_Game){
        this.Jpanel_Game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Resmi yükleyin
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/armoyu.png"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };


        JButton button1 = Items.Button("Button");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("ıNTRO BİTİYOR");
                //Login
                new LoginScreen(Jframe_Game);


            }
        });
        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        Jpanel_Game.add(button1, gbc);

        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setVisible(true);
    }
}
