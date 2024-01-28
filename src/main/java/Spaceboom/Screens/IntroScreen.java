package Spaceboom.Screens;

import Spaceboom.Services.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntroScreen {
    private Timer delayTimer;
    SoundPlayer player = new SoundPlayer();

    public IntroScreen(JFrame Jframe_Game){

        JPanel Jpanel_Game = new JPanel() {
            private ImageIcon backgroundImageIcon;
            {
                backgroundImageIcon = new ImageIcon(getClass().getResource("/gif/introscreen.gif"));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Image backgroundImage = backgroundImageIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };


        player.RepeatMusic(true);
        player.playAsync("armoyuonetype.wav");


        Jframe_Game.addKeyListener(new KeyAdapter() {
            int sayac=0;
            @Override
            public void keyPressed(KeyEvent e) {
                if (sayac==0){
                    int key = e.getKeyCode();

                    if (key == KeyEvent.VK_SPACE || key==KeyEvent.VK_ENTER) {
                        player.StopMusic();
                        Jpanel_Game.setVisible(false);
                        new LoadingScreen(Jframe_Game);
                        delayTimer.stop();
                        sayac++;
                    }
                }
            }
        });



        GridBagConstraints gbc = new GridBagConstraints();




        ImageIcon scuIcon = new ImageIcon(getClass().getResource("/images/scü.png"));
        Image scuImage = scuIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        scuIcon = new ImageIcon(scuImage);
        JLabel scuLabel = new JLabel(scuIcon);

        ImageIcon armoyuIcon = new ImageIcon(getClass().getResource("/images/armoyu.png"));
        Image armoyuımage = armoyuIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        armoyuIcon = new ImageIcon(armoyuımage);
        JLabel armoyuIconLabel = new JLabel(armoyuIcon);

        ImageIcon spacelogo= new ImageIcon(getClass().getResource("/images/spaceship6.png"));
        Image spaceImage = spacelogo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        spacelogo = new ImageIcon(spaceImage);
        JLabel spacelogoLabel = new JLabel(spacelogo);

        Jpanel_Game.setLayout(new GridBagLayout());

        delayTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                player.StopMusic();
                Jpanel_Game.setVisible(false);
                new LoadingScreen(Jframe_Game);
                delayTimer.stop();

            }
        });
        delayTimer.start();


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.insets = new Insets(0, 550, 350, 0);
        Jpanel_Game.add(scuLabel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.insets = new Insets(0, 0, 350, 0);
        Jpanel_Game.add(armoyuIconLabel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.insets = new Insets(0, 0, 350, 550);
        Jpanel_Game.add(spacelogoLabel,gbc);


        JLabel gameLabel = new JLabel();
        gameLabel.setForeground(Color.white);

        String labelText = "<html>" +
                "Bu oyun, hızlı ve tekrarlayan ışık değişiklikleri içerebilir. Epilepsi hastaları için risk oluşturabilir.Eğer epilepsi veya benzeri bir sağlık sorununuz varsa, önce doktorunuza danışınız." +
                "<br>" + "oyuncuların Dikkatine:" +
                "<br>" +
                "<br>" +
                "(c) 2023 ARMOYU, Inc. SpaceWar logosu ve ARMOYU Software, Inc.'in ticari markaları ve/veya tescillenmiş ticari markalarıdır." +
                "<br>" +
                "<br>" +
                "Diğer tüm işaretler ve ticari markalar, ilgili sahiplerinin mülkiyetindedir. Tüm Hakları Saklıdır. Derecelendirme ikonu, ARMOYU Software ticari markasıdır." +
                "<br>" +
                "<br>" +
                "Not: Bu video oyunun içeriği tamamen kurgusaldır ve herhangi bir gerçek kişiyi, işletmeyi veya kuruluşu temsil etmeyi amaçlamamaktadır." +
                "<br>" +
                "<br>" +
                " Bu oyunun karakteri, diyalogu, olayı veya hikaye öğesi ile herhangi bir gerçek kişi, işletme veya kuruluş arasındaki benzerlik tamamen tesadüfidir." +
                "<br>" +
                "<br>" +
                " Bu video oyununun yapımcıları ve yayıncıları, bu tür davranışları herhangi bir şekilde desteklemez, onaylamaz veya teşvik etmez." +
                "<br>" +
                "SpaceWar Games CopyRight (c) " +
                "</html>";

        Font currentFont = gameLabel.getFont();


        Font biggerFont = currentFont.deriveFont(currentFont.getSize() * 1.5f);
        gameLabel.setFont(biggerFont);
        gameLabel.setText(labelText);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 10;
        gbc.insets = new Insets(0, 0, 200, 0);
        Jpanel_Game.add(gameLabel,gbc);

        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setVisible(true);
    }
}