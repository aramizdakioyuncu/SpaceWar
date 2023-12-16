package Sariaboom;
import Sariaboom.API.API;
import Sariaboom.API.USER;
import org.json.JSONObject;

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
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/gif/backroundstart.gif"));
                Image image = imageIcon.getImage();


                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton button = new JButton("START");
        JButton button1 = new JButton("GUEST START");

        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 30));

        button1.setBackground(new Color(0, 0, 0, 0));
        button1.setBorderPainted(false);
        button1.setFocusPainted(true);
        button1.setContentAreaFilled(false);
        button1.setForeground(Color.WHITE);
        button1.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField textField1 = new JTextField(20);
        JTextField textField2 = new JTextField(20);

        JLabel label1 = new JLabel("USERNAME:");
        JLabel label2 = new JLabel("PASSWORD:");


        label1.setForeground(Color.white);
        label2.setForeground(Color.WHITE);

        textField1.addActionListener(e-> {
            textField2.requestFocusInWindow();
        });
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String username = textField1.getText();
                    String password = API.convertToMD5(textField2.getText());

                    JSONObject cevap = API.request(username + "/" + password + "/0/0/0/");
                    if(cevap.get("kontrol").toString().equals("0")){
                        System.out.println("Kullanıcı Bulunamadı");
                        return;
                    }
                    //Başarılı ile giriş yapıldı
                    USER.username = username;
                    USER.password = password;

                    SpaceInvaders ex = new SpaceInvaders();
                    ex.setVisible(true);
                }
            });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SpaceInvaders ex = new SpaceInvaders();
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

        //EventQueue.invokeLater(() -> {
       // });
        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(textField1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(textField2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        panel.add(button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(80, 0, 0, 0);
        panel.add(button1, gbc);

        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


