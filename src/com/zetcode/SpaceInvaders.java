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

            JFrame frame = new JFrame("***!!!!!!!SARİYE BOOOOOOOM!!!!!!!!***");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton button = new JButton("***START***");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    var ex = new SpaceInvaders();
                    ex.setVisible(true);
                }
            });

            frame.add(button);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        EventQueue.invokeLater(() -> {

        });
    }
}


