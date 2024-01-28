package Spaceboom.Services;

import Spaceboom.API.FUNCTION;
import Spaceboom.API.USER;
import Spaceboom.Commons;
import Spaceboom.Screens.GameScreen;
import Spaceboom.SpaceBoom;
import Spaceboom.Utility.Items;
import Spaceboom.sprite.*;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class GameService {
    public static SoundPlayer splayer = new SoundPlayer();
    SoundPlayer splayer2 = new SoundPlayer();

    public void timeControl(BoardData boardData){
        if (boardData.sayac>Commons.MAX_GAME_TIME-1) {
            boardData.inGame = false;
            boardData.timer.stop();
        }
    }

    public void initBoard(BoardData boardData, JPanel panel, PlayerService playerService,AlienService alienService) {
        boardData.baslangicZaman = System.currentTimeMillis();

        panel.addKeyListener(new TAdapter(boardData));
        panel.setFocusable(true);
        boardData.d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(boardData.backgroundImgPath));
        boardData.backgroundImage = backgroundIcon.getImage();

        ImageIcon finishgraundIcon = new ImageIcon(getClass().getResource(boardData.finishgraundImgPath));
        boardData.finishgraundImage = finishgraundIcon.getImage();

        ImageIcon gameoverIcon = new ImageIcon(getClass().getResource(boardData.gameoverImgPath));
        boardData.gameoverImage = gameoverIcon.getImage();

        boardData.timer.start();

        gameInit(boardData,playerService,alienService);
    }

    public void gameInit(BoardData boardData, PlayerService playerService,AlienService alienService){
        boardData.aliens = new ArrayList<Alien>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(Commons.ALIEN_INIT_X + 80 * j,
                        Commons.ALIEN_INIT_Y + 45 * i,alienService);
                boardData.aliens.add(alien);
            }
        }
        boardData.player = new Player(playerService);
        boardData.player.shot = new Shot();
        boardData.attackSpeed = new AttackSpeed((int) (Math.random() * 1830),(int) (Math.random() * 400) + 200);
        boardData.speedUp = new SpeedUp((int) (Math.random() * 1830),(int) (Math.random() * 400) + 200);
    }



    public double scoreCalculator(BoardData boardData, boolean isWon){


        int totalShot = Player.totalShotCount * (-200);
        int destroyedEnemies = boardData.deaths * 2000;
        Double doublesayac = 0.0;

        if (isWon){
             doublesayac =  (Commons.MAX_GAME_TIME-boardData.sayac) * 500;
        }

        return totalShot + destroyedEnemies + doublesayac;


    }
    //game over
    public void gameOver(BoardData boardData, Graphics g, FontMetrics fontMetrics){
        String finishbackgroundImage;
        String buttonText;
        String scoreinfo;
        String scoreinfodetail;
        Double score;

        String restartText = "RESTART";

        if (boardData.deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY*Commons.levelCount) {
            score = scoreCalculator(boardData,true);
            finishbackgroundImage = boardData.finishgraundImgPath;
            splayer.RepeatMusic(true);
            splayer.playAsync("win.wav");
            scoreinfo = "Score: "+ score;
            scoreinfodetail = "Destroyed Enemies: " + boardData.deaths + "\n Total Shots: " + Player.totalShotCount;
            buttonText = "\nYOU WON BUT IT'S STILL NOT THE END\n\n";

        } else {
            score = scoreCalculator(boardData,false);

            finishbackgroundImage = boardData.gameoverImgPath;
            splayer.playAsync("explosion-80108.wav");
            splayer2.RepeatMusic(true);
            splayer2.playAsync("lose.wav");
            scoreinfo = "Score: "+ score;
            scoreinfodetail = "Destroyed Enemies: " + boardData.deaths + "\n Total Shots: " + Player.totalShotCount;
            buttonText = "\nMAKE A LITTLE EFFORT TO WIN\n\n";

        }

        if(USER.username != null){
            String formData = "skor="+score;

            CompletableFuture<JSONObject> postRequestFuture = FUNCTION.SaveScore2(formData);
            postRequestFuture.thenAcceptAsync(response -> {
                JSONObject cevap = response;
                System.out.println(cevap.get("aciklama"));
            });
        }

        Player.totalShotCount = 0;

        g.setColor(new Color(0, 32, 48));

        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(boardData.message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(boardData.message)) / 2, Commons.BOARD_WIDTH / 2);

        JTextArea scoreInfoArea = Items.TextArea(scoreinfo);
        JTextArea scoreInfodetailArea = Items.TextArea(scoreinfodetail);
        scoreInfoArea.setFont(new Font("Arial", Font.BOLD, 50));
        scoreInfodetailArea.setFont(new Font("Arial", Font.BOLD, 35));

        JTextArea gameOverText = Items.TextArea(buttonText);

        JButton button = Items.Button(restartText);
        JButton mainmenu  = Items.Button("MAIN MENU");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Alien.bombSpeed = Commons.bulletSpeed;
                splayer2.StopMusic();
                splayer.StopMusic();
                boardData.frameGame.dispose();
                boardData.frameGame = new GameScreen().frame;
            }
        });

        mainmenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splayer2.StopMusic();
                splayer.StopMusic();

                splayer.playAsync("startmonkey.wav");
                SpaceBoom.Jframe_Game.setVisible(true);
                GameScreen.frame.dispose();
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
        gbc.gridy = 3;
        gbc.gridwidth = 0;
        panel.add(scoreInfoArea,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 0;
        panel.add(scoreInfodetailArea,gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 0;
        panel.add(gameOverText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 0;
        panel.add(button,gbc);


        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 0;
        panel.add(mainmenu,gbc);

        boardData.frameGame.setContentPane(panel);
        boardData.frameGame.add(button);
        boardData.frameGame.setVisible(true);
    }


    public void update(BoardData boardData, PlayerService playerService, AlienService alienService){
        alienService.alienDeathsControl(boardData,this,playerService);

        playerService.act(boardData);

        playerService.playerBulletHandler(boardData);

        alienService.alienActHandler(boardData);

        alienService.isAliensOutFrame(boardData);

        Random generator = new Random();

        for (Alien alien : boardData.aliens) {
            int shot = generator.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();
            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }
            playerService.playerDeathControl(boardData,bomb);
            alienService.alienBombHandler(bomb);
        }
    }
    public void levelUp(BoardData boardData, AlienService alienService,PlayerService playerService){
        boardData.loading = true;
        alienService.resetLocation(boardData.aliens, boardData);
        alienService.setBulletSpeed(Alien.bombSpeed+5);
        playerService.resetLocation(boardData.player);
        playerService.setPlayerImage(boardData.level,boardData.player);
    }

    int loadingCounter = 0;
    public void loadingLevel(BoardData boardData){
        if (loadingCounter==0){
            boardData.loadingBaslangicZaman = System.currentTimeMillis();
            loadingCounter++;
        }
        if ((System.currentTimeMillis()-boardData.loadingBaslangicZaman)/1000 > 3){
            boardData.loading = false;
            boardData.ekZaman+=3;
            loadingCounter = 0;
        }
    }
}
