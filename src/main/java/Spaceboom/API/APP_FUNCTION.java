package Spaceboom.API;

import javax.swing.*;
import java.awt.*;

public class APP_FUNCTION {

    public static void ToggleFullScreen(Frame frame){
        //Tam ekran mı
        if(frame.isUndecorated()){
            frame.dispose();
            frame.setUndecorated(false); // Tam ekran modunu kapat
            frame.setSize(800, 600); // Pencere boyutunu ayarla
            frame.setLocationRelativeTo(null); // Pencereyi ortala
            frame.setVisible(true);
        }else{
            frame.dispose();
            frame.setUndecorated(true); // Tam ekran modunu etkinleştir
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ekran boyutuna göre ayarla
            frame.setLocation(0, 0); // Pencereyi sol üst köşeye yerleştir
            frame.setVisible(true);
        }
    }

    public static void setFullScreen(Frame frame){
        frame.dispose();
        frame.setUndecorated(true); // Tam ekran modunu etkinleştir
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Ekran boyutuna göre ayarla
        frame.setLocation(0, 0); // Pencereyi sol üst köşeye yerleştir
        frame.setVisible(true);
    }

    public static void setBorderScreen(Frame frame){
        frame.dispose();
        frame.setUndecorated(false); // Tam ekran modunu kapat
        frame.setSize(800, 600); // Pencere boyutunu ayarla
        frame.setLocationRelativeTo(null); // Pencereyi ortala
        frame.setVisible(true);
    }

    public static void PauseResumeGame(Timer timer) {
        System.out.println("P tuşuna basıldı. Oyun Duracak");

        if(timer.isRunning()){
            timer.stop();
            return;
        }
        timer.start();
    }
}
