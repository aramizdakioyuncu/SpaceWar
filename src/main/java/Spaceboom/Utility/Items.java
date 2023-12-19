package Spaceboom.Utility;

import javax.swing.*;
import java.awt.*;

public class Items {


   public static JButton Button(String text) {
        JButton button = new JButton(text);
        // Buton kenarlığını kaldır
        button.setBorderPainted(false);
        // Buton odaklanıldığında çizilen kenarlığı kaldır
        button.setFocusPainted(false);
        // Butonun iç kısmını dolduran rengi kaldır
        button.setContentAreaFilled(false);
        // Buton metin rengini beyaz yap
        button.setForeground(Color.WHITE);
        // Buton metin fontunu ayarla
        button.setFont(new Font("Arial", Font.BOLD, 25));
        return button;
    }

    public static JTextField TextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setOpaque(false);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.BOLD, 20));
        return textField;
    }

   public static JPasswordField PasswordTextField(int columns) {
        JPasswordField passwordtextField = new JPasswordField(columns);
        passwordtextField.setOpaque(false);
        passwordtextField.setForeground(Color.WHITE);
        passwordtextField.setFont(new Font("Arial", Font.BOLD, 20));
        return passwordtextField;
    }
}
