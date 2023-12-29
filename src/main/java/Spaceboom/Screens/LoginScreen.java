package Spaceboom.Screens;

import Spaceboom.API.API;
import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.Commons;
import Spaceboom.SpaceBoom;
import Spaceboom.Utility.Items;
import Spaceboom.Utility.SoundPlayer;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class LoginScreen {

    private JPanel Jpanel_Game;
    SoundPlayer splayer = new SoundPlayer();

    public  LoginScreen(JFrame Jframe_Game){

        this.Jpanel_Game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Resmi yükleyin
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        splayer.RepeatMusic(true);
        splayer.playAsync("startmonkey.wav");


        JTextField tfield_username = Items.TextField(10);
        JPasswordField tfield_password = Items.PasswordTextField(10);

        JLabel label1 = new JLabel("USERNAME:");
        JLabel label2 = new JLabel("PASSWORD:");
        JLabel label_loginStatus = new JLabel("***********");
        JButton loginbutton =  Items.Button("Login");
        JButton startbutton = Items.Button("Start");
        JButton quitbutton =  Items.Button("Quit The Destkop");
        JButton settingsbutton =  Items.Button("Control Settings");

        ImageIcon imageIcon = new ImageIcon(SpaceBoom.class.getResource("/gif/loading.gif"));

        Image image = imageIcon.getImage();
        image = image.getScaledInstance(25, 25 ,Image.SCALE_DEFAULT);

        JLabel img_loading = new JLabel(new ImageIcon(image));
        img_loading.setVisible(false);

        label1.setForeground(Color.white);
        label2.setForeground(Color.WHITE);
        label_loginStatus.setForeground(Color.WHITE);

        tfield_username.addActionListener(e-> {  tfield_password.requestFocusInWindow(); });


        loginbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = tfield_username.getText();
                String password = API.convertToMD5(tfield_password.getText());

                label_loginStatus.setText("Giriş bilgileri kontrol ediliyor.");
                img_loading.setVisible(true);


                //Async
                CompletableFuture<JSONObject> postRequestFuture = FUNCTION.Login(username, password);
                postRequestFuture.thenAcceptAsync(response -> {
                    SwingUtilities.invokeLater(() -> {

                        JSONObject cevap = response;

                        if (cevap.get("durum").toString().equals("0")) {
                            System.out.println("Sunucuya bağlanılamadı!");
                            label_loginStatus.setText("Sunucuya bağlanılamadı!");
                            img_loading.setVisible(false);
                            return;
                        }

                        if (cevap.get("kontrol").toString().equals("0")) {
                            System.out.println("Kullanıcı Bulunamadı");
                            label_loginStatus.setText("Kullanıcı Bulunamadı");
                            img_loading.setVisible(false);
                            return;
                        }

                        // Başarılı ile giriş yapıldı
                        USER.username = username;
                        USER.password = password;

                        //Giriş ekranını gizle
                        //kod yazılacak....

                        // Oyunu Aç
                        splayer.StopMusic();

                        new GameScreen();
                        Jframe_Game.setVisible(false);

                    });
                });
            }
        });

        startbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                splayer.StopMusic();
                new GameScreen();
                Jframe_Game.setVisible(false);

            }
        });
        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        Jpanel_Game.add(loginbutton, gbc);

        Jpanel_Game.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        Jpanel_Game.add(label1, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        Jpanel_Game.add(tfield_username, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Game.add(label2, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Game.add(tfield_password, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        Jpanel_Game.add(label_loginStatus, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        Jpanel_Game.add(loginbutton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        Jpanel_Game.add(startbutton, gbc);


        gbc.gridx = 1;
        gbc.gridy = 7;
        Jpanel_Game.add(img_loading,gbc);


        gbc.gridx = 1;
        gbc.gridy = 8;
        Jpanel_Game.add(settingsbutton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        Jpanel_Game.add(quitbutton, gbc);


        JPanel updateNotesPanel = new JPanel(new BorderLayout());
        JTextArea updateNotesArea = new JTextArea(Commons.UPDATELIST);
        updateNotesArea.setEditable(false);
        updateNotesPanel.add(updateNotesArea, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 10;
        Jpanel_Game.add(updateNotesPanel,gbc);

        JPanel scoreNotesPanel = new JPanel(new BorderLayout());
        JTextArea scoreNotesArea = new JTextArea(Commons.SCORELIST);
        scoreNotesArea.setEditable(false);
        scoreNotesPanel.add(scoreNotesArea, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 11;
        Jpanel_Game.add(scoreNotesPanel,gbc);



        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setLocationRelativeTo(null);
        Jframe_Game.setVisible(true);
    }
}
