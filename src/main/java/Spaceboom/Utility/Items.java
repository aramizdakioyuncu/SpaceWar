package Spaceboom.Utility;

import javax.swing.*;
import java.awt.*;

public class Items {


   public static JButton Button(String text) {
        JButton button = new JButton(text);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setContentAreaFilled(false);

        button.setForeground(Color.WHITE);

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
        textarea.setForeground(Color.green);
        textarea.setEditable(false);
        textarea.setOpaque(false);
        textarea.setBackground(new Color(0, 0, 0, 0));
        textarea.setFont(new Font("Arial", Font.BOLD, 20));
        return textarea;
    }

    public static JPanel Panel() {
        Color transparentMagenta = new Color(255, 0, 255, 0);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(transparentMagenta);
        return panel;
    }

    public static JLabel Label(String text){
        JLabel Label =   new JLabel(text);
        Label.setForeground(Color.yellow);
        Label.setFont(new Font("Arial", Font.BOLD, 20));
       return Label;
    }
}
