package Spaceboom;
import Spaceboom.API.API;
import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {


       initUI();
    }

    private void initUI() {

        add(new Board());

        setTitle("Space Invaders");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {


        JFrame frame = new JFrame("Space Invaders Login Screen");
        frame.setSize(900, 400);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Resmi yükleyin
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };


        JTextField tfield_username = new JTextField(20);
        JPasswordField tfield_password = new JPasswordField(20);

        JLabel label1 = new JLabel("USERNAME:");
        JLabel label2 = new JLabel("PASSWORD:");
        JLabel label_loginStatus = new JLabel("---------------");

        JButton button =  Utility.Button("START");
        JButton button1 = Utility.Button("GUEST START");

        ImageIcon imageIcon = new ImageIcon(SpaceInvaders.class.getResource("/gif/loading.gif"));

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

                            // Oyunu Aç
                            SpaceInvaders ex = new SpaceInvaders();
                            ex.setVisible(true);
                        });
                    });
                }
            });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SpaceInvaders ex = new SpaceInvaders();
                ex.setVisible(true);
            }
        });
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(button, gbc);



        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(tfield_username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(tfield_password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;

        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(label_loginStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(button1, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 0;
        panel.add(img_loading,gbc);

        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


