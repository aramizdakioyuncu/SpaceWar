package Spaceboom;

import Spaceboom.Services.*;
import Spaceboom.sprite.BoardData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Board extends JPanel{
    SoundPlayer splayer = new SoundPlayer();
    SoundPlayer splayer2 = new SoundPlayer();
    private BoardData boardData = new BoardData();
    private final SpecialAbilityService specialAbilityService = new SpecialAbilityService();
    private final PlayerService playerService = new PlayerService();
    private final AlienService alienService = new AlienService();
    private final GameService gameService = new GameService();

    public Board(JFrame frame) {
        boardData.frameGame = frame;
        boardData.timer = new Timer(Commons.DELAY, new GameCycle());
        gameService.initBoard(boardData,this);
        splayer2.RepeatMusic(true);
        splayer2.playAsync("backroundgame.wav");

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    private void doDrawing(Graphics g) {
        g.drawImage(boardData.backgroundImage, 0, 0, this);
        Font small = new Font("Helvetica", Font.BOLD, 100);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        DecimalFormat df = new DecimalFormat("#.##");
        boardData.sayac =  ((double)(System.currentTimeMillis()- boardData.baslangicZaman)/1000)- boardData.ekZaman;
        if (boardData.inGame) {
            if (boardData.loading){
                g.setFont(small);
                g.setColor(Color.white);
                g.drawString("Level "+boardData.level, (Commons.BOARD_WIDTH-fontMetrics.stringWidth("Level " + boardData.level))/2   , Commons.BOARD_HEIGHT/2);
                return;
            }
            Graphics2D g2d=(Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.red);
            g2d.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
            g2d.setStroke(new BasicStroke());

            alienService.drawAliens(g, boardData.aliens,this);
            boardData.inGame = playerService.drawPlayer(g, boardData.player,this);
            playerService.drawPlayerBullet(boardData.player.shot,g,this);
            alienService.alienBulletHandler(boardData.aliens,g,this);
            if (boardData.timer.isRunning()){
                boardData.attackSpeedBaslangicZaman = specialAbilityService.attackSpeedHandler(g,this, boardData.sayac, boardData.attackSpeed, boardData.player, boardData.farklimiti, boardData.attackSpeedBaslangicZaman, boardData.player.shot);
                boardData.speedUpBaslangicZaman = specialAbilityService.speedUpHandler(g,this, boardData.sayac, boardData.speedUp, boardData.player, boardData.farklimiti, boardData.speedUpBaslangicZaman);
            }

            g.setFont(small);
            g.setColor(Color.white);
            if (boardData.timer.isRunning()){
                g.drawString(df.format(Commons.MAX_GAME_TIME - boardData.sayac), (Commons.BOARD_WIDTH-fontMetrics.stringWidth(String.valueOf(boardData.format)))/2   , Commons.BOARD_HEIGHT/13);
            }else
                g.drawString("Pause", (Commons.BOARD_WIDTH-fontMetrics.stringWidth(String.valueOf(boardData.format)))/2   , Commons.BOARD_HEIGHT/2);

        } else {
            if (boardData.timer.isRunning()) {
                boardData.timer.stop();
            }
            splayer.StopMusic();
            splayer2.StopMusic();

            gameService.gameOver(boardData,g,this.getFontMetrics(small));
        }
        gameService.timeControl(boardData);
        Toolkit.getDefaultToolkit().sync();
    }
    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
//            splayer2.RepeatMusic(true);
//            splayer2.playAsync("backroundgame.wav");
            if (boardData.loading){
                gameService.loadingLevel(boardData);
            }else {
                gameService.update(boardData,playerService,alienService);
            }
        }
    }
}