package Spaceboom.Screens;

import Spaceboom.Utility.Items;
import Spaceboom.Utility.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroScreen {
    private JPanel Jpanel_Game;

    public IntroScreen(JFrame Jframe_Game){

        SoundPlayer.playAsync("start.wav");

        this.Jpanel_Game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Resmi yükleyin
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/armoyu.png"));
                Image image = imageIcon.getImage();
                g.drawImage(image, (getWidth()/2)-150, (getHeight()/2)-150, 300, 300, this);
            }
        };

        Jpanel_Game.setBackground(Color.BLACK);

        JButton button1 = Items.Button("Oyuna Başlamak için Tıklayınız");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("INTRO BİTİYOR");
                //Login
                new LoginScreen(Jframe_Game);
                SoundPlayer.StopMusic();

            }
        });
        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(400, 0, 0, 0);
        Jpanel_Game.add(button1, gbc);

        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setVisible(true);
    }
}