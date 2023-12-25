package Spaceboom.Screens;

import Spaceboom.Utility.Items;
import Spaceboom.Utility.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroScreen {
    private JPanel Jpanel_Game;
    private Timer delayTimer;
    SoundPlayer player = new SoundPlayer();

    public IntroScreen(JFrame Jframe_Game){
        player.RepeatMusic(true);
        player.playAsync("armoyuonetype.wav");




        this.Jpanel_Game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Resmi yükleyin


            }
        };
        GridBagConstraints gbc = new GridBagConstraints();

        Jpanel_Game.setBackground(Color.BLACK);


        ImageIcon scuIcon = new ImageIcon(getClass().getResource("/images/scü.png"));
        Image scuImage = scuIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        scuIcon = new ImageIcon(scuImage);
        JLabel scuLabel = new JLabel(scuIcon);

        ImageIcon armoyuIcon = new ImageIcon(getClass().getResource("/images/armoyu.png"));
        Image armoyuımage = armoyuIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        armoyuIcon = new ImageIcon(armoyuımage);
        JLabel armoyuIconLabel = new JLabel(armoyuIcon);

        ImageIcon spacelogo= new ImageIcon(getClass().getResource("/images/spaceship.png"));
        Image spaceImage = spacelogo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        spacelogo = new ImageIcon(spaceImage);
        JLabel spacelogoLabel = new JLabel(spacelogo);

        Jpanel_Game.setLayout(new GridBagLayout());

        delayTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 5 saniye sonra bu kod çalışır
                player.StopMusic();
                new LoginScreen(Jframe_Game);
                delayTimer.stop();

            }
        });
        delayTimer.start();


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10; // Set the width of scuLabel to 1

        gbc.insets = new Insets(0, 550, 350, 0);
        Jpanel_Game.add(scuLabel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10; // Set the width of scuLabel to 1
        gbc.insets = new Insets(0, 0, 350, 0);
        Jpanel_Game.add(armoyuIconLabel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10; // Set the width of scuLabel to 1
        gbc.insets = new Insets(0, 0, 350, 550);
        Jpanel_Game.add(spacelogoLabel,gbc);


        JLabel gameLabel = new JLabel();
        gameLabel.setForeground(Color.white);

        String labelText = "<html>" +
                "SpaceWar Games CopyRight" +
                "Bu oyun, hızlı ve tekrarlayan ışık değişiklikleri içerebilir. Epilepsi hastaları için risk oluşturabilir." +
                "<br>"+
                "Eğer epilepsi veya benzeri bir sağlık sorununuz varsa, önce doktorunuza danışınız." +
                "<br>" +
                "çok satırlı<br>label örneğidir." +
                "<br>" +
                "Geliştiricilerin dikkatine" +
                "</html>";
        Font currentFont = gameLabel.getFont();

        // Yeni bir font oluştur ve boyutunu belirle
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