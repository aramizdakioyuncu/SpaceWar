package Spaceboom.Screens;

import Spaceboom.API.API;
import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.Commons;
import Spaceboom.Services.GameService;
import Spaceboom.Services.SoundPlayer;
import Spaceboom.SpaceBoom;
import Spaceboom.Utility.ControlsSetting;
import Spaceboom.Utility.Items;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LoginScreen {
    private Map<String, JButton> buttonMap = new LinkedHashMap<>();
    private int mapindis;
    private JPanel Jpanel_Game;
    private JButton loginbutton;
    private JButton startbutton;
    private JButton quitbutton;
    private JButton settingsbutton;
    private JButton selectedButton;
    private JButton backbutton;

    SoundPlayer splayer = new SoundPlayer();
    SoundPlayer s1player = new SoundPlayer();


    public LoginScreen(JFrame Jframe_Game) {

        buttonMap.put("Login",Items.Button("Login"));
        buttonMap.put("Start",Items.Button("Start"));
        buttonMap.put("Control Settings",Items.Button("Control Settings"));
        buttonMap.put("Quit The Desktop",Items.Button("Quit The Desktop"));

        this.selectedButton = null;

        this.Jpanel_Game = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        splayer.RepeatMusic(true);
        splayer.playAsync("startmonkey.wav");

        JTextField tfield_username = Items.TextField(10);
        JPasswordField tfield_password = Items.PasswordTextField(10);
        tfield_username.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tfield_username.setBorder(BorderFactory.createLineBorder(Color.green));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tfield_username.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            }
        });

        tfield_password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tfield_password.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tfield_password.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            }
        });

        JLabel label_username = new JLabel("USERNAME:");
        JLabel label_password = new JLabel("PASSWORD:");
        JLabel label_loginStatus = new JLabel("***********");

        JButton showPasswordButton=Items.Button("");



        JPanel scoreNotesPanel = Items.Panel();
        JTextArea scoreNotesArea = Items.TextArea(Commons.SCORELIST);
        scoreNotesPanel.add(scoreNotesArea, BorderLayout.CENTER);
        JButton scoreReoladButton = Items.Button("RELOAD");

        ImageIcon imageIcon = new ImageIcon(SpaceBoom.class.getResource("/gif/loading.gif"));
        Image reloadimage = imageIcon.getImage();
        reloadimage = reloadimage.getScaledInstance(25, 25, Image.SCALE_DEFAULT);

        JLabel label_reloadicon = new JLabel(new ImageIcon(reloadimage));
        label_reloadicon.setVisible(false);

        JButton button_logout = Items.Button("LOG OUT");
        button_logout.setVisible(false);

        ImageIcon eyeicon = new ImageIcon(getClass().getResource("/images/goz.png"));
        Image scaledImage = eyeicon.getImage().getScaledInstance(50, 50, Image.SCALE_FAST);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        showPasswordButton.setIcon(scaledIcon);

        JLabel label_userdisplayname = Items.Label(USER.displayName);

        JLabel label_useravatar = Items.Label("");

        loginbutton = buttonMap.get("Login");
        startbutton = buttonMap.get("Start");
        quitbutton = buttonMap.get("Quit The Desktop");
        settingsbutton = buttonMap.get("Control Settings");


        MeneSelectButton(loginbutton);


        addButtonHoverListener(loginbutton);
        addButtonHoverListener(startbutton);
        addButtonHoverListener(quitbutton);
        addButtonHoverListener(settingsbutton);

        quitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    splayer.StopMusic();
                    Jframe_Game.dispose();
                    System.exit(0);
                }
        });

        JLabel img_loading = new JLabel(new ImageIcon(reloadimage));
        img_loading.setVisible(false);

        label_username.setForeground(Color.white);
        label_password.setForeground(Color.WHITE);
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


                        USER.username = username;
                        USER.password = password;

                        USER.avatar = cevap.get("presimminnak").toString();
                        USER.displayName = cevap.get("adim").toString();

                        label_userdisplayname.setText(USER.displayName);

                        img_loading.setVisible(false);

                        label_username.setVisible(false);
                        label_password.setVisible(false);
                        label_loginStatus.setVisible(false);

                        tfield_username.setVisible(false);
                        tfield_password.setVisible(false);
                        showPasswordButton.setVisible(false);

                        loginbutton.setVisible(false);




                        button_logout.setVisible(true);
                        label_useravatar.setVisible(true);
                        label_userdisplayname.setVisible(true);

                        tfield_username.setText("");
                        tfield_password.setText("");

                        label_loginStatus.setText("-----");

                        MeneSelectButton(startbutton);

                        try {
                            URL url = new URL(USER.avatar);
                            ImageIcon icon = new ImageIcon(url);
                            Image image = icon.getImage();

                            ImageIcon userAvatar =new ImageIcon(image);

                            Image scaledImage = userAvatar.getImage().getScaledInstance(150, 150, Image.SCALE_FAST);
                            ImageIcon scaledIcon = new ImageIcon(scaledImage);

                            label_useravatar.setIcon(scaledIcon);
                            label_useravatar.setPreferredSize(new Dimension(150, 150));
                            label_useravatar.setHorizontalAlignment(SwingConstants.CENTER);
                            label_useravatar.setVerticalAlignment(SwingConstants.CENTER);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

//                        splayer.StopMusic();
//                        new GameScreen();
//                        Jframe_Game.setVisible(false);
                    });
                });
            }
        });


        button_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                USER.avatar = null;
                USER.displayName = null;


                label_username.setVisible(true);
                label_password.setVisible(true);


                tfield_username.setVisible(true);
                tfield_password.setVisible(true);
                showPasswordButton.setVisible(true);

                loginbutton.setVisible(true);


                label_loginStatus.setVisible(true);
                label_userdisplayname.setVisible(false);
                label_useravatar.setVisible(false);
                button_logout.setVisible(false);


            }
        });

        startbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splayer.StopMusic();
                GameService.splayer.StopMusic();
                Jframe_Game.setVisible(false);
                new GameScreen();
            }
        });


        JPanel Jpanel_Settings =new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        Jpanel_Settings.setLayout(new GridBagLayout());
        JButton WButton = Items.Button("W");
        JButton AButton = Items.Button("A");
        JButton SButton = Items.Button("S");
        JButton DButton = Items.Button("D");
        JButton PAUSEButton = Items.Button("P");
        JButton SPACEButton = Items.Button("SPACE");


        JButton UP = Items.Button("UP");
        JButton DOWN = Items.Button("DOWN");
        JButton RIGHT = Items.Button("RIGHT");
        JButton LEFT = Items.Button("LEFT");
        JButton PAUSE = Items.Button("PAUSE");
        JButton SHOT = Items.Button("SHOT");
        backbutton = Items.Button("BACK");

        WButton.setBackground(Color.BLACK);
        AButton.setBackground(Color.BLACK);
        SButton.setBackground(Color.BLACK);
        DButton.setBackground(Color.BLACK);
        PAUSEButton.setBackground(Color.BLACK);
        SPACEButton.setBackground(Color.BLACK);
        UP.setBackground(Color.BLACK);
        DOWN.setBackground(Color.BLACK);
        RIGHT.setBackground(Color.BLACK);
        LEFT.setBackground(Color.BLACK);
        PAUSE.setBackground(Color.BLACK);
        SHOT.setBackground(Color.BLACK);
        backbutton.setBackground(Color.BLACK);


        WButton.setOpaque(true);  // Bu satırı eklemeyi unutma
        WButton.setBackground(Color.BLACK);
        WButton.setForeground(Color.WHITE);  // Yazı rengini belirle

        AButton.setOpaque(true);
        AButton.setBackground(Color.BLACK);
        AButton.setForeground(Color.WHITE);

        SButton.setOpaque(true);
        SButton.setBackground(Color.BLACK);
        SButton.setForeground(Color.WHITE);

        DButton.setOpaque(true);
        DButton.setBackground(Color.BLACK);
        DButton.setForeground(Color.WHITE);

        PAUSEButton.setOpaque(true);
        PAUSEButton.setBackground(Color.BLACK);
        PAUSEButton.setForeground(Color.WHITE);

        SPACEButton.setOpaque(true);
        SPACEButton.setBackground(Color.BLACK);
        SPACEButton.setForeground(Color.WHITE);

        UP.setOpaque(true);
        UP.setBackground(Color.BLACK);
        UP.setForeground(Color.WHITE);

        DOWN.setOpaque(true);
        DOWN.setBackground(Color.BLACK);
        DOWN.setForeground(Color.WHITE);

        RIGHT.setOpaque(true);
        RIGHT.setBackground(Color.BLACK);
        RIGHT.setForeground(Color.WHITE);

        LEFT.setOpaque(true);
        LEFT.setBackground(Color.BLACK);
        LEFT.setForeground(Color.WHITE);

        PAUSE.setOpaque(true);
        PAUSE.setBackground(Color.BLACK);
        PAUSE.setForeground(Color.WHITE);


        SHOT.setOpaque(true);
        SHOT.setBackground(Color.BLACK);
        SHOT.setForeground(Color.WHITE);

        backbutton.setOpaque(true);
        backbutton.setBackground(Color.BLACK);
        backbutton.setForeground(Color.WHITE);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 1);
        Jpanel_Settings.add(WButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 1);
        Jpanel_Settings.add(SButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(DButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(AButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(PAUSEButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(SPACEButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(UP, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(DOWN, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(RIGHT, gbc);
        gbc.gridx =1 ;
        gbc.gridy = 5;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(LEFT, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(PAUSE, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(SHOT, gbc);
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.insets = new Insets(8, 0, 10, 1);
        Jpanel_Settings.add(backbutton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.insets = new Insets(8,0,10,1);


        Jpanel_Settings.setVisible(false);

        settingsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jframe_Game.setLocationRelativeTo(null);
                Jframe_Game.setContentPane(Jpanel_Settings);
                Jpanel_Game.setVisible(false);
                Jpanel_Settings.setVisible(true);
            }

        });
        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jframe_Game.setLocationRelativeTo(null);
                Jframe_Game.setContentPane(Jpanel_Game);
                Jpanel_Settings.setVisible(false);
                Jpanel_Game.setVisible(true);

            }

        });
        showPasswordButton.addActionListener(new ActionListener() {
            private boolean passwordVisible = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordVisible) {

                    tfield_password.setEchoChar('*');
                } else {

                    tfield_password.setEchoChar((char) 0);
                }

                passwordVisible = !passwordVisible;
            }
            });


        scoreReoladButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                label_reloadicon.setVisible(true);
                //Async
                CompletableFuture<JSONObject> POST_FetchScoreList = FUNCTION.FetchScoreList();

                POST_FetchScoreList.thenAcceptAsync(response -> {
                    SwingUtilities.invokeLater(() -> {

                        JSONObject cevap = response;

                        if (cevap.get("durum").toString().equals("0")) {
                            System.out.println("Sunucuya bağlanılamadı!");
                            return;
                        }
                        if (cevap.get("icerik").toString().equals("null")) {
                            return;
                        }

                        JSONArray recs = cevap.getJSONArray("icerik");

                        int sayac = 1;
                        Commons.SCORELIST = "";
                        for (int i = 0; i < recs.length(); ++i) {
                            JSONObject rec = recs.getJSONObject(i);

                            if(i == 0){
                                Commons.SCORELIST += sayac + "- " + rec.get("proje_oyunuc").toString();
                                Commons.SCORELIST +=  ": " + rec.get("proje_skor").toString();
                            }else{
                                Commons.SCORELIST += "\n" + sayac + "- " + rec.get("proje_oyunuc").toString();
                                Commons.SCORELIST += ": " + rec.get("proje_skor").toString();
                            }

                            sayac++;
                        }

                        scoreNotesArea.setText(Commons.SCORELIST);
                        label_reloadicon.setVisible(false);

                    });
                });
            }
        });

        WButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenInput(WButton,"UP");
            }
        });
        DButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenInput(DButton,"RIGHT");
            }
        }); AButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenInput(AButton,"LEFT");
            }
        }); SButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenInput(SButton,"DOWN");
            }
        }); SPACEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenInput(SPACEButton,"SHOT");
            }
        }); PAUSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenInput(PAUSEButton,"PAUSE");
            }
        });


        JTextArea Bosluk = Items.TextArea("");


        gbc.insets = new Insets(0,0,0,0);
        gbc.gridwidth = 4;

        gbc.gridx = 1;
        gbc.gridy = 1;
        Jpanel_Game.add(Bosluk, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        Jpanel_Game.add(startbutton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        Jpanel_Game.add(settingsbutton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        Jpanel_Game.add(quitbutton, gbc);

        JPanel updateNotesPanel = Items.Panel();
        JTextArea updateNotesArea = Items.TextArea(Commons.UPDATELIST);
        updateNotesPanel.add(updateNotesArea, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        Jpanel_Game.add(updateNotesPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        Jpanel_Game.add(Bosluk, gbc);



        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        Jpanel_Game.add(scoreNotesPanel, gbc);


        scoreReoladButton.setVisible(false);

        if(Commons.SCORELIST != null){
            scoreReoladButton.setVisible(true);
        }


        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        Jpanel_Game.add(label_reloadicon, gbc);


        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        Jpanel_Game.add(scoreReoladButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        Jpanel_Game.add(Bosluk, gbc);


        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0,-260,0,0);
        Jpanel_Game.add(label_username, gbc);

        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.insets = new Insets(0,0,0,0);
        Jpanel_Game.add(tfield_username, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.insets = new Insets(0,-260,0,0);
        Jpanel_Game.add(label_password, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 2;
        gbc.gridy = 8;
        Jpanel_Game.add(tfield_password,gbc);

        gbc.insets = new Insets(0,220,0,0);
        gbc.gridx = 2;
        gbc.gridy = 8;
        Jpanel_Game.add(showPasswordButton, gbc);



        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        Jpanel_Game.add(label_loginStatus, gbc);

        gbc.gridx = 1;
        gbc.gridy = 14;
        Jpanel_Game.add(loginbutton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        Jpanel_Game.add(label_userdisplayname, gbc);

        gbc.gridx = 2;
        gbc.gridy = 12;
        Jpanel_Game.add(label_useravatar, gbc);

        gbc.gridx = 2;
        gbc.gridy = 13;
        Jpanel_Game.add(img_loading, gbc);



        gbc.gridx = 1;
        gbc.gridy = 14;
        Jpanel_Game.add(button_logout, gbc);

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
    public void screenInput(JButton button,String keyBinding){
        String str = JOptionPane.showInputDialog("Please enter a key:");
        if (str == null) {
            System.out.println("User canceled input.");
            return;
        }
        if (str.length() != 1) {
            System.out.println("Please enter only one character.");
            return;
        }
        char charValue = str.charAt(0);

        button.setText(String.valueOf(charValue));
        ControlsSetting.setButton(keyBinding, str);
    }

    private void addButtonHoverListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {


            public void mouseEntered(MouseEvent evt) {

                MeneSelectButton(button);

            }

            public void mouseExited(MouseEvent evt) {
//                if (button != selectedButton) {
//                    button.setForeground(Color.white);
//                }
            }
            public void mouseClicked(MouseEvent evt) {
//                selectButton(button);
            }
        });
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
        button.setForeground(Color.green);
    }

    private void MeneSelectupperButton() {

        s1player.playAsync("laserpiuv2.wav");

        int sayac = buttonMap.size();
        int istenilenindis = mapindis + 1;

        if (istenilenindis == buttonMap.size() + 1){
             istenilenindis = 1;
        }

        for (JButton allbutton : buttonMap.values()) {
            if(sayac == istenilenindis){
                MeneSelectButton(allbutton);
                return;
            }
            sayac--;

        }

    }
    private void MeneSelectdownerButton() {

        s1player.playAsync("laserpiuv2.wav");

        int sayac = buttonMap.size();
        int istenilenindis = mapindis-1;

        if (istenilenindis == 0){
             istenilenindis = buttonMap.size();
        }

        for (JButton allbutton : buttonMap.values()) {
            if(sayac == istenilenindis){
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
