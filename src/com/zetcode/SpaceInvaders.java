package com.zetcode;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {


       initUI();
    }

    private void initUI() {

        add(new Board());

        setTitle("Space Invaders");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {


            JFrame frame = new JFrame("***!!!!!!!SARİYE BOOOOOOOM!!!!!!!!***");
            frame.setSize(1920, 1080);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Resmi yükleyin
                ImageIcon imageIcon = new ImageIcon("src/gif/backroundstart.gif");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton button = new JButton("START");

        button.setBackground(new Color(0, 0, 0, 0));

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.setForeground(Color.WHITE);

        button.setFont(new Font("Arial", Font.BOLD, 30));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    var ex = new SpaceInvaders();
                    ex.setVisible(true);
                }
            });
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(button, gbc);

        frame.setContentPane(panel);
        frame.add(button);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        EventQueue.invokeLater(() -> {

        });
    }
}


