package Spaceboom.Screens;

import Spaceboom.API.FUNCTION;
import Spaceboom.Commons;
import Spaceboom.SpaceBoom;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class LoadingScreen {


    private static int controlStatus = 0;
    private boolean  ControlFinishStatus = false;
    private Timer timer_login;
    private static int tryconnectcount=2;
    private static int tryupdatecount=2;
    public LoadingScreen(JFrame Jframe_Game){

     JPanel Jpanel_Game = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
            Image image = imageIcon.getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };

        ImageIcon imageIcon = new ImageIcon(SpaceBoom.class.getResource("/gif/loading.gif"));


        Image image = imageIcon.getImage();
        image = image.getScaledInstance(30, 30 ,Image.SCALE_DEFAULT);

        JLabel img_loading = new JLabel(new ImageIcon(image));




        JLabel serverStatus = new JLabel("Loading");
        serverStatus.setForeground(Color.white);
        serverStatus.setVisible(true);


        timer_login = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 5 saniye sonra bu kod çalışır
                System.out.println(tryconnectcount);
                if(ControlFinishStatus){
                    return;
                }
                if(controlStatus == 2 || tryconnectcount <=0){
                    new LoginScreen(Jframe_Game);
                    ControlFinishStatus = true;
                    timer_login.stop();

                }

            }
        });
        timer_login.start();
        controlinternet();
        updatenotes();

        Jpanel_Game.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();





        gbc.gridx = 0;
        gbc.gridy = 0;
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

}
public static void controlinternet(){
    if (tryconnectcount<=0) {
       return;
    }
      tryconnectcount--;
        //Async
        CompletableFuture<JSONObject> POST_FetchScoreList = FUNCTION.FetchScoreList();

        POST_FetchScoreList.thenAcceptAsync(response -> {
            SwingUtilities.invokeLater(() -> {

                JSONObject cevap = response;

                if (cevap.get("durum").toString().equals("0")) {
                    System.out.println("Sunucuya bağlanılamadı!");
                    controlinternet();
                    return;
                }
                if (cevap.get("icerik").toString().equals("null")) {
                    return;
                }

                controlStatus++;

                JSONArray recs = cevap.getJSONArray("icerik");

                int sayac = 1;
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

            });
        });
    }
public static void updatenotes(){
        if (tryupdatecount<=0){
            return;
        }
        tryupdatecount--;
    //Async
    CompletableFuture<JSONObject> POST_GameInformation = FUNCTION.GameInformation();
    POST_GameInformation.thenAcceptAsync(response -> {
        SwingUtilities.invokeLater(() -> {

            JSONObject cevap = response;

            if (cevap.get("durum").toString().equals("0")) {
                System.out.println("Sunucuya bağlanılamadı!");
                updatenotes();
                return;
            }
            controlStatus++;
            System.out.println(cevap.get("guncellemenotlari").toString());
            Commons.UPDATELIST = cevap.get("guncellemenotlari").toString();

        });
    });
}}
