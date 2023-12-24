package Spaceboom.API;

import Spaceboom.DTOS.BoardDTO;

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

    public static double PauseResumeGame(BoardDTO boardDTO) {
        System.out.println("P tuşuna basıldı. Oyun Duracak");

        if(boardDTO.timer.isRunning()){
            boardDTO.timer.stop();
            boardDTO.ekZamanBaslangic = System.currentTimeMillis();
            return 0;
        }
        boardDTO.ekZamanBitis = System.currentTimeMillis();
        boardDTO.timer.start();
        return (double) ((boardDTO.ekZamanBaslangic - boardDTO.ekZamanBitis)/1000);
    }
}
