package Spaceboom.API;

import Spaceboom.sprite.BoardData;

import java.awt.*;

public class APP_FUNCTION {

    public static void ToggleFullScreen(Frame frame){

        if(frame.isUndecorated()){
            frame.dispose();
            frame.setUndecorated(false);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }else{
            frame.dispose();
            frame.setUndecorated(true);
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setLocation(0, 0);
            frame.setVisible(true);
        }
    }

    public static void setFullScreen(Frame frame){
        frame.dispose();
        frame.setUndecorated(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocation(0, 0);
        frame.setVisible(true);
    }

    public static void setBorderScreen(Frame frame){
        frame.dispose();
        frame.setUndecorated(false); // Tam ekran modunu kapat
        frame.setSize(800, 600); // Pencere boyutunu ayarla
        frame.setLocationRelativeTo(null); // Pencereyi ortala
        frame.setVisible(true);
    }

    public static double PauseResumeGame(BoardData boardData) {
        System.out.println("P tuşuna basıldı. Oyun Duracak");

        if(boardData.timer.isRunning()){
            boardData.ekZamanBaslangic = System.currentTimeMillis();
            boardData.timer.stop();
            return 0;
        }
        boardData.timer.start();
        boardData.ekZamanBitis = System.currentTimeMillis();
        return (double) ((boardData.ekZamanBitis-boardData.ekZamanBaslangic)/1000);
    }
}
