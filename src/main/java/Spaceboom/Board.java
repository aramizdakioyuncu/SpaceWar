package Spaceboom;

import Spaceboom.DTOS.BoardDTO;
import Spaceboom.Services.AlienService;
import Spaceboom.Services.GameService;
import Spaceboom.Services.PlayerService;
import Spaceboom.Services.SpecialAbilityService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Board extends JPanel{
    private BoardDTO boardDTO = new BoardDTO();

    private final SpecialAbilityService specialAbilityService = new SpecialAbilityService();
    private final PlayerService playerService = new PlayerService();
    private final AlienService alienService = new AlienService();
    private final GameService gameService = new GameService();

    public Board(JFrame frame) {
        boardDTO.frameGame = frame;
        boardDTO.timer = new Timer(Commons.DELAY, new GameCycle());
        gameService.initBoard(boardDTO,this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
     g.drawImage(boardDTO.backgroundImage, 0, 0, this);
        Font small = new Font("Helvetica", Font.BOLD, 100);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        DecimalFormat df = new DecimalFormat("#.##");

        boardDTO.sayac =  ((double)(System.currentTimeMillis()-boardDTO.baslangicZaman)/1000)+ boardDTO.ekZaman;

        gameService.timeControl(boardDTO);

        if (boardDTO.inGame) {
            Graphics2D g2d=(Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.red);
            g2d.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
            g2d.setStroke(new BasicStroke());

            alienService.drawAliens(g,boardDTO.aliens,this);
            boardDTO.inGame = playerService.drawPlayer(g,boardDTO.player,this);
            playerService.drawPlayerBullet(boardDTO.player.shot,g,this);
            alienService.alienBulletHandler(boardDTO.aliens,g,this);
            boardDTO.attackSpeedBaslangicZaman = specialAbilityService.attackSpeedHandler(g,this,boardDTO.sayac,boardDTO.attackSpeed,boardDTO.player,boardDTO.farklimiti,boardDTO.attackSpeedBaslangicZaman,boardDTO.player.shot);
            boardDTO.speedUpBaslangicZaman = specialAbilityService.speedUpHandler(g,this,boardDTO.sayac,boardDTO.speedUp,boardDTO.player,boardDTO.farklimiti,boardDTO.speedUpBaslangicZaman);

            g.setFont(small);
            g.setColor(Color.white);
            g.drawString(df.format(Commons.MAX_GAME_TIME - boardDTO.sayac), (Commons.BOARD_WIDTH-fontMetrics.stringWidth(String.valueOf(boardDTO.format)))/2   , Commons.BOARD_HEIGHT/13);
        } else {
            if (boardDTO.timer.isRunning()) {
                boardDTO.timer.stop();
            }
            gameService.gameOver(boardDTO,g,this.getFontMetrics(small));
        }
        Toolkit.getDefaultToolkit().sync();
    }
    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameService.update(boardDTO,playerService,alienService);
            repaint();
        }
    }
}