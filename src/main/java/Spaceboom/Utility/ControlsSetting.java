package Spaceboom.Utility;

import java.awt.event.KeyEvent;

public class ControlsSetting {
    public static int up = KeyEvent.VK_UP;
    public static int down = KeyEvent.VK_DOWN;
    public static int right = KeyEvent.VK_RIGHT;
    public static int left = KeyEvent.VK_LEFT;
    public static int shot = KeyEvent.VK_SPACE;
    public static int pause = KeyEvent.VK_P;


    public static void setButton(String keyBinding, String presss) {
        try {

            int uppercaseKeyBinding = presss.toUpperCase().charAt(0);
            int press = uppercaseKeyBinding;

            switch (keyBinding) {
                case "UP":
                    up = press;
                    System.out.println("tuş Ataması Başarıyla Yapıldı!");

                    break;
                case "DOWN":
                    down = press;
                    System.out.println("Tuş Ataması Başarıyla Yapıldı!");

                    break;
                case "RIGHT":
                    right = press;
                    System.out.println("Tuş Ataması Başarıyla Yapıldı!");

                    break;
                case "LEFT":
                    left = press;
                    System.out.println("Tuş Ataması Başarıyla Yapıldı!");

                    break;
                case "SHOT":
                    shot = press;
                    System.out.println("Tuş Ataması Başarıyla Yapıldı!");

                    break;
                case "PAUSE":
                    pause = press;
                    System.out.println("Tuş Ataması Başarıyla Yapıldı!");

                    break;


                default:
                    System.out.println("Bilinmeyen keybinding: " + keyBinding);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}






