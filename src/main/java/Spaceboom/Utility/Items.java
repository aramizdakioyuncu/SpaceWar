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

       button.setFocusable(true);
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

    public static JTextArea TextArea(String text) {
        JTextArea textarea = new JTextArea(text);
        textarea.setForeground(Color.WHITE);
        textarea.setEditable(false);
        textarea.setOpaque(false); // Şeffaf olmasını sağla
        textarea.setBackground(new Color(0, 0, 0, 0));
        textarea.setFont(new Font("Arial", Font.BOLD, 20));
        return textarea;
    }

    public static JPanel Panel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.BLUE);
        return panel;
    }

}
