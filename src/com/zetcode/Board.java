package com.zetcode;

import com.zetcode.sprite.Alien;
import com.zetcode.sprite.Player;
import com.zetcode.sprite.PowerUps;
import com.zetcode.sprite.Shot;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {

    private Dimension d;
    private List<Alien> aliens;
    private Player player;
    private Shot shot;
    private PowerUps powerUps;
    
    private int direction = -5;
    private int deaths = 0;


    private boolean inGame = true;
    final String explImg = "src/images/explosion.png";
    final String backgroundImgPath = "src/images/background.jpg";
    private Image backgroundImage;
    final String finishgraundImgPath = "src/images/finishgraund.jpg";
    private Image finishgraundImage;
    final String gameoverImgPath = "src/images/gameover.jpg";
    private Image gameoverImage;

    private String message = "Game Over";

    private Timer timer;


    private boolean powerUpDrawn = false;
    private int powerUpX;
    private int powerUpY;



    public Board() {

        initBoard();
        gameInit();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);


        ImageIcon backgroundIcon = new ImageIcon(backgroundImgPath);
        backgroundImage = backgroundIcon.getImage();
        ImageIcon finishgraundIcon = new ImageIcon(finishgraundImgPath);
        finishgraundImage = finishgraundIcon.getImage();
        ImageIcon gameoverIcon = new ImageIcon(gameoverImgPath);
        gameoverImage = gameoverIcon.getImage();




        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();


        gameInit();
    }


    private void gameInit() {

        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                var alien = new Alien(Commons.ALIEN_INIT_X + 80 * j,
                        Commons.ALIEN_INIT_Y + 45 * i);
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();
        powerUps = new PowerUps();

    }


    private void drawAliens(Graphics g) {

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), (int) alien.getX(), (int) alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {


            g.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }

    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), (int) shot.getX(), (int) shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {


        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), (int) b.getX(), (int) b.getY(), this);
            }
        }
    }



    private void drawPowerUps(Graphics g) {
        if (!powerUpDrawn && deaths >= 3) {
            powerUpX = (int) (Math.random() * 1830);
            powerUpY = (int) (Math.random() * 400) + 200;
            g.drawImage(powerUps.getImage(), powerUpX, powerUpY, this);
            powerUpDrawn = true;
        } else if (!powerUpDrawn && deaths >= 5) {
            powerUpX = (int) (Math.random() * 1830);
            powerUpY = (int) (Math.random() * 400) + 200;
            g.drawImage(powerUps.getImage(), powerUpX, powerUpY, this);
            powerUpDrawn = true;
        } else if (powerUpDrawn) {

            g.drawImage(powerUps.getImage(), powerUpX, powerUpY, this);
        }
    }











    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

    }

    private void doDrawing(Graphics g) {
        // Arka plan resmini çiz
        g.drawImage(backgroundImage, 0, 0, this);



        if (inGame) {

            Graphics2D g2d=(Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.red);

            g2d.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
            g2d.setStroke(new BasicStroke());
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawPowerUps(g);


        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            g.drawImage(finishgraundImage, 0, 0, this);  // "Game Won" için farklı bir resim kullan
        } else {
            g.drawImage(gameoverImage, 0, 0, this);  // Orijinal "Game Over" resmini kullan
        }

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2);
    }


    private void update() {





        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player

        player.act();

        // shot
        if (shot.isVisible()) {

            double shotX = shot.getX();
            double shotY = shot.getY();

            for (Alien alien : aliens) {

                double alienX = alien.getX();
                double alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        var ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        double width4 = ((double) ii.getIconWidth() / 12);
                        double length4 = ((double) ii.getIconHeight() / 8);
                        java.awt.Image scaledImage = ii.getImage().getScaledInstance((int) width4, (int) length4, java.awt.Image.SCALE_SMOOTH);
                        ii = new ImageIcon(scaledImage);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            double y = shot.getY();
            y -= 20;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (Alien alien : aliens) {

            double x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {

                direction = -5;

                for (Alien a2 : aliens) {

                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {

                direction = 5;

                for (Alien a : aliens) {

                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                double y = alien.getY();

                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        var generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();

            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            double bombX = bomb.getX();
            double bombY = bomb.getY();
            double playerX = player.getX();
            double playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    var ii = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    double width4 = ((double) ii.getIconWidth() / 5);
                    double length4 = ((double) ii.getIconHeight() / 4);
                    java.awt.Image scaledImage = ii.getImage().getScaledInstance((int) width4, (int) length4, java.awt.Image.SCALE_SMOOTH);
                    ii = new ImageIcon(scaledImage);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 10);

                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {

        update();
        repaint();

    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            double yeni = ((double) Commons.PLAYER_WIDTH / 2)*0.54;

            double x = player.getX()+yeni;
            double y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {

                    if (!shot.isVisible()) {

                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}
