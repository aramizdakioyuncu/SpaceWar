package Spaceboom.Screens;

import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.Commons;
import Spaceboom.SpaceBoom;
import Spaceboom.Utility.Items;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class LoadingScreen {



    public LoadingScreen(JFrame Jframe_Game){

     JPanel Jpanel_Game = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Resmi yükleyin
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
            Image image = imageIcon.getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };

        ImageIcon imageIcon = new ImageIcon(SpaceBoom.class.getResource("/gif/loading.gif"));


        Image image = imageIcon.getImage();
        image = image.getScaledInstance(30, 30 ,Image.SCALE_DEFAULT);

        JLabel img_loading = new JLabel(new ImageIcon(image));


        JButton button1 = Items.Button("GUEST START");

        JLabel serverStatus = new JLabel("Loading");
        serverStatus.setForeground(Color.white);
        serverStatus.setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen(Jframe_Game);
            }
        });


        //Async
        CompletableFuture<JSONObject> POST_GameInformation = FUNCTION.GameInformation();
        POST_GameInformation.thenAcceptAsync(response -> {
            SwingUtilities.invokeLater(() -> {

                JSONObject cevap = response;

                if (cevap.get("durum").toString().equals("0")) {
                    System.out.println("Sunucuya bağlanılamadı!");
                    return;
                }
                System.out.println(cevap.get("guncellemenotlari").toString());
                Commons.UPDATELIST = cevap.get("guncellemenotlari").toString();

            });
        });
        //Async
        CompletableFuture<JSONObject> POST_FetchScoreList = FUNCTION.FetchScoreList();

        POST_FetchScoreList.thenAcceptAsync(response -> {
            SwingUtilities.invokeLater(() -> {

                JSONObject cevap = response;

                if (cevap.get("durum").toString().equals("0")) {
                    System.out.println("Sunucuya bağlanılamadı!");
                    return;
                }
                if (    cevap.get("icerik").toString().equals("null")) {
                    return;
                }
                JSONArray recs = cevap.getJSONArray("icerik");
                for (int i = 0; i < recs.length(); ++i) {
                    JSONObject rec = recs.getJSONObject(i);
                    Commons.SCORELIST += rec.get("proje_oyunuc").toString();
                    Commons.SCORELIST += rec.get("proje_skor").toString();
                    System.out.println(rec.get("proje_oyunuc").toString());
                    System.out.println(rec.get("proje_skor").toString());

                }

            });
        });

        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);

        Jpanel_Game.add(button1, gbc);

        gbc.gridx = 0; // Use RELATIVE to add to the right of the previous component
        gbc.gridy = 0; // Use RELATIVE to add below the previous component
        gbc.gridwidth = 2;
        gbc.insets = new Insets(Commons.BOARD_HEIGHT-30, Commons.BOARD_WIDTH-50, 0, 0);
        Jpanel_Game.add(img_loading, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, Commons.BOARD_WIDTH-150, 30, 0);
        Jpanel_Game.add(serverStatus, gbc);

        Jframe_Game.setContentPane(Jpanel_Game);
        Jframe_Game.setLocationRelativeTo(null);
        Jframe_Game.setVisible(true);

}}
