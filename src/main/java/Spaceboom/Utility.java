package Spaceboom;

import javax.swing.*;
import java.awt.*;

public class Utility {



    static JButton Button(String text) {
        JButton button = new JButton(text);

        // Buton kenarlığını kaldır
        button.setBorderPainted(false);
        // Buton odaklanıldığında çizilen kenarlığı kaldır
        button.setFocusPainted(false);
        // Butonun iç kısmını dolduran rengi kaldır
        button.setContentAreaFilled(false);
        // Buton metin rengini beyaz yap
        button.setForeground(Color.PINK);
        // Buton metin fontunu ayarla
        button.setFont(new Font("Arial", Font.BOLD, 25));

        return button;
    }
}
