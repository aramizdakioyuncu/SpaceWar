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
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static Spaceboom.SpaceBoom.Jframe_Game;

public class LoginScreen {
    private Map<String, JButton> buttonMap = new LinkedHashMap<>();
    private int mapindis;
    private JPanel Jpanel_Game;
    private JButton loginbutton;
    private JButton startbutton;
    private JButton quitbutton;
    private JButton settingsbutton;
    private JButton selectedButton;


    SoundPlayer splayer = new SoundPlayer();


    public LoginScreen(JFrame Jframe_Game) {

        buttonMap.put("Login",Items.Button("Login"));
        buttonMap.put("Start",Items.Button("Start"));
        buttonMap.put("Control Settings",Items.Button("Control Settings"));
        buttonMap.put("Quit The Desktop",Items.Button("Quit The Desktop"));

        for (JButton allbutton : buttonMap.values()) {
           System.out.println(allbutton.getText());

        }
        this.selectedButton = null;

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

        loginbutton = buttonMap.get("Login");
        startbutton = buttonMap.get("Start");
        quitbutton = buttonMap.get("Quit The Desktop");
        settingsbutton = buttonMap.get("Control Settings");

        // Başlangıçta butonunu seçili yap
        MeneSelectButton(loginbutton);

        // Her bir buton için fare dinleyicisi ekleyerek üzerine gelindiğinde rengi değiştir
        addButtonHoverListener(loginbutton);
        addButtonHoverListener(startbutton);
        addButtonHoverListener(quitbutton);
        addButtonHoverListener(settingsbutton);

        quitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kullanıcıya onay mesajı göster
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);

                // Eğer kullanıcı "YES" derse, uygulamayı kapat
                if (confirm == JOptionPane.YES_OPTION) {
                    splayer.StopMusic();
                    Jframe_Game.dispose();  // JFrame'i kapat
                    System.exit(0);  // Uygulamayı tamamen kapat
                }
            }
        });

        ImageIcon imageIcon = new ImageIcon(SpaceBoom.class.getResource("/gif/loading.gif"));
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(25, 25, Image.SCALE_DEFAULT);

        JLabel img_loading = new JLabel(new ImageIcon(image));
        img_loading.setVisible(false);

        label1.setForeground(Color.white);
        label2.setForeground(Color.WHITE);
        label_loginStatus.setForeground(Color.WHITE);

        tfield_username.addActionListener(e -> {
            tfield_password.requestFocusInWindow();
        });

        loginbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfield_username.getText();
                String password = API.convertToMD5(tfield_password.getText());

                label_loginStatus.setText("Giriş bilgileri kontrol ediliyor.");
                img_loading.setVisible(true);

                // Async
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

                        // Giriş ekranını gizle
                        // kod yazılacak....

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
        Jpanel_Game.add(img_loading, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        Jpanel_Game.add(settingsbutton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        Jpanel_Game.add(quitbutton, gbc);

        JPanel updateNotesPanel = Items.Panel();
        JTextArea updateNotesArea = Items.TextArea(Commons.UPDATELIST);

        updateNotesPanel.add(updateNotesArea, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 10;
        Jpanel_Game.add(updateNotesPanel, gbc);

        JPanel scoreNotesPanel = Items.Panel();
        JTextArea scoreNotesArea = Items.TextArea(Commons.SCORELIST);

        scoreNotesPanel.add(scoreNotesArea, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 11;
        Jpanel_Game.add(scoreNotesPanel, gbc);


        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setLocationRelativeTo(null);
        Jframe_Game.setVisible(true);


        Jframe_Game.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                addArrowKeyListener(e);
            }
        });

    }

    private void addButtonHoverListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {


            public void mouseEntered(MouseEvent evt) {
                if (button != selectedButton) {
                    button.setForeground(Color.green); // Üzerine gelindiğinde rengi değiştir
                }
            }

            public void mouseExited(MouseEvent evt) {
                if (button != selectedButton) {
                    button.setForeground(Color.white); // Fare çekildiğinde orijinal rengine dön
                }
            }
            public void mouseClicked(MouseEvent evt) {
//                selectButton(button);
            }
        });
    }

    // Oyunu başlatan metod
    private void startGame() {
        splayer.StopMusic();
        new GameScreen();
        Jframe_Game.setVisible(false);
    }


    private void MeneButtonEnter() {
        int sayac = buttonMap.size();
        for (JButton allbutton : buttonMap.values()) {
            if(sayac == mapindis){
                System.out.println("---" + allbutton.getText());

                allbutton.doClick();
            }
            sayac--;
        }
    }


    private void MeneSelectButton(JButton button) {
        int sayac = buttonMap.size();
        for (JButton allbutton : buttonMap.values()) {
            allbutton.setForeground(Color.WHITE);
            if(allbutton == button){
                mapindis = sayac;

            }
            sayac--;
        }
        button.setForeground(Color.BLUE);
    }

    private void MeneSelectupperButton() {
        int sayac = buttonMap.size();
        int istenilenindis = mapindis + 1;

        if (istenilenindis == buttonMap.size() + 1){
             istenilenindis = 1;
        }
        System.out.println(istenilenindis);

        for (JButton allbutton : buttonMap.values()) {

            if(sayac == istenilenindis){
                System.out.println(allbutton.getText() + " Seçili");
                MeneSelectButton(allbutton);
                return;
            }
            sayac--;

        }

    }
    private void MeneSelectdownerButton() {
        int sayac = buttonMap.size();
        int istenilenindis = mapindis-1;

        if (istenilenindis == 0){
             istenilenindis = buttonMap.size();
        }
        System.out.println(istenilenindis);

        for (JButton allbutton : buttonMap.values()) {

            if(sayac == istenilenindis){
                System.out.println(allbutton.getText() + " Seçili");
                MeneSelectButton(allbutton);
                return;
            }
            sayac--;

        }

    }
    private void addArrowKeyListener(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP || key==KeyEvent.VK_W) {
            MeneSelectupperButton();
            return;
        }
        if (key == KeyEvent.VK_DOWN || key==KeyEvent.VK_S){
            MeneSelectdownerButton();
            return;
        }
        if (key == KeyEvent.VK_ENTER || key==KeyEvent.VK_F){
            MeneButtonEnter();
            return;
        }
    }
}
