package Spaceboom.Services;

import Spaceboom.Commons;
import Spaceboom.DTOS.BoardDTO;
import Spaceboom.Screens.GameScreen;
import Spaceboom.Utility.Items;
import Spaceboom.Utility.SoundPlayer;
import Spaceboom.sprite.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameService {
    SoundPlayer splayer = new SoundPlayer();
    SoundPlayer splayer2 = new SoundPlayer();

    public void timeControl(BoardDTO boardDTO){
        if (boardDTO.sayac>Commons.MAX_GAME_TIME-1) {
            boardDTO.inGame = false;
            boardDTO.timer.stop();
        }
    }

    public void initBoard(BoardDTO boardDTO,JPanel panel) {
        boardDTO.baslangicZaman = System.currentTimeMillis();

        panel.addKeyListener(new TAdapter(boardDTO));
        panel.setFocusable(true);
        boardDTO.d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(boardDTO.backgroundImgPath));
        boardDTO.backgroundImage = backgroundIcon.getImage();

        ImageIcon finishgraundIcon = new ImageIcon(getClass().getResource(boardDTO.finishgraundImgPath));
        boardDTO.finishgraundImage = finishgraundIcon.getImage();

        ImageIcon gameoverIcon = new ImageIcon(getClass().getResource(boardDTO.gameoverImgPath));
        boardDTO.gameoverImage = gameoverIcon.getImage();

        boardDTO.timer.start();

        gameInit(boardDTO);
    }

    public void gameInit(BoardDTO boardDTO){
        boardDTO.aliens = new ArrayList<Alien>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(Commons.ALIEN_INIT_X + 80 * j,
                        Commons.ALIEN_INIT_Y + 45 * i);
                boardDTO.aliens.add(alien);
            }
        }
        boardDTO.player = new Player();
        boardDTO.player.shot = new Shot();
        boardDTO.attackSpeed = new AttackSpeed((int) (Math.random() * 1830),(int) (Math.random() * 400) + 200);
        boardDTO.speedUp = new SpeedUp((int) (Math.random() * 1830),(int) (Math.random() * 400) + 200);
    }

    //game over
    public void gameOver(BoardDTO boardDTO,Graphics g,FontMetrics fontMetrics){
        String finishbackgroundImage;
        String buttonText;
        if (boardDTO.deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            finishbackgroundImage = boardDTO.finishgraundImgPath;
            splayer.RepeatMusic(true);
            splayer.playAsync("win.wav");
            buttonText = "YOU WON BUT IT'S STILL NOT THE END";

        } else {
            finishbackgroundImage = boardDTO.gameoverImgPath;
            splayer.playAsync("explosion-80108.wav");
            splayer2.RepeatMusic(true);
            splayer2.playAsync("lose.wav");
            buttonText = "MAKE A LITTLE EFFORT TO WIN";

        }
        g.setColor(new Color(0, 32, 48));

        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(boardDTO.message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(boardDTO.message)) / 2, Commons.BOARD_WIDTH / 2);

        JButton button = Items.Button(buttonText);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splayer2.StopMusic();
                splayer.StopMusic();
                boardDTO.frameGame.dispose();
                boardDTO.frameGame = new GameScreen().frame;
            }
        });
        String finalFinishbackgroundImage = finishbackgroundImage;
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(getClass().getResource(finalFinishbackgroundImage));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 0;
        panel.add(button,gbc);

        boardDTO.frameGame.setContentPane(panel);
        boardDTO.frameGame.add(button);
        boardDTO.frameGame.setVisible(true);
    }

    //update
    public void update(BoardDTO boardDTO,PlayerService playerService,AlienService alienService){
        alienService.alienDeathsControl(boardDTO);

        playerService.act(boardDTO);

        playerService.playerBulletHandler(boardDTO);

        alienService.alienActHandler(boardDTO);

        alienService.isAliensOutFrame(boardDTO);

        Random generator = new Random();

        for (Alien alien : boardDTO.aliens) {
            int shot = generator.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();
            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }
            playerService.playerDeathControl(boardDTO,bomb);
            alienService.alienBombHandler(bomb);
        }
    }
}
