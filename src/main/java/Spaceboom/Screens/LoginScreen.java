package Spaceboom.Screens;

import Spaceboom.API.API;
import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
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
        JLabel label_loginStatus = new JLabel("---------------");
        JButton button =  Items.Button("START");
        JButton button1 = Items.Button("GUEST START");

        ImageIcon imageIcon = new ImageIcon(SpaceBoom.class.getResource("/gif/loading.gif"));

        Image image = imageIcon.getImage();
        image = image.getScaledInstance(25, 25 ,Image.SCALE_DEFAULT);

        JLabel img_loading = new JLabel(new ImageIcon(image));
        img_loading.setVisible(false);

        label1.setForeground(Color.white);
        label2.setForeground(Color.WHITE);
        label_loginStatus.setForeground(Color.WHITE);

        tfield_username.addActionListener(e-> {  tfield_password.requestFocusInWindow(); });


        button.addActionListener(new ActionListener() {
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

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                splayer.StopMusic();
                new GameScreen();
                Jframe_Game.setVisible(false);

            }
        });
        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        Jpanel_Game.add(button, gbc);

        Jpanel_Game.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        Jpanel_Game.add(label1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        Jpanel_Game.add(tfield_username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        Jpanel_Game.add(label2, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        Jpanel_Game.add(tfield_password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;

        gbc.insets = new Insets(0, 0, 10, 0);
        Jpanel_Game.add(label_loginStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        Jpanel_Game.add(button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        Jpanel_Game.add(button1, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 0;
        Jpanel_Game.add(img_loading,gbc);
        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setLocationRelativeTo(null);
        Jframe_Game.setVisible(true);
    }
}
