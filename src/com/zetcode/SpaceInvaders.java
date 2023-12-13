package com.zetcode;
import javax.swing.JButton;
import java.awt.EventQueue;
import javax.swing.JFrame;

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

            // Pencereyi oluştur
            JFrame frame = new JFrame("Java Button Ekleme Örneği");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Düğmeyi oluştur
            JButton button = new JButton("Tıkla");

            // Düğmeye tıklanma olayını dinle
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Düğmeye tıklandı!");
                    var ex = new SpaceInvaders();
                    ex.setVisible(true);
                }
            });

            // Düğmeyi pencereye ekle
            frame.add(button);

            // Pencereyi görünür yap
            frame.setVisible(true);

        EventQueue.invokeLater(() -> {

        });
    }
}


