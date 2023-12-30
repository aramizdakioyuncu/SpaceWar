package Spaceboom.Services;

import Spaceboom.API.APP_FUNCTION;
import Spaceboom.Commons;
import Spaceboom.DTOS.BoardDTO;
import Spaceboom.sprite.Shot;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
    BoardDTO boardDTO;
    public TAdapter(BoardDTO boardDTO){
        this.boardDTO = boardDTO;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        boardDTO.player.keyReleased(e);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        boardDTO.player.keyPressed(e);
        double yeni = ((double) Commons.PLAYER_WIDTH/2.25);
        double x = boardDTO.player.getX()+yeni;
        double y = boardDTO.player.getY();

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (boardDTO.inGame) {
                if (!boardDTO.player.shot.isVisible()) {
                    boardDTO.player.shot = new Shot(x, y);
                }
            }
        }

        if (key == KeyEvent.VK_P) {
            boardDTO.ekZaman += APP_FUNCTION.PauseResumeGame(boardDTO);
        }
    }
}
