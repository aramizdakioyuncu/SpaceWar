package Spaceboom.Services;

import Spaceboom.API.APP_FUNCTION;
import Spaceboom.Commons;
import Spaceboom.Utility.ControlsSetting;
import Spaceboom.sprite.BoardData;
import Spaceboom.sprite.Player;
import Spaceboom.sprite.Shot;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
    BoardData boardData;
    public TAdapter(BoardData boardData){
        this.boardData = boardData;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        boardData.player.keyReleased(e);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        boardData.player.keyPressed(e);
        double yeni = ((double) Commons.PLAYER_WIDTH/2.25);
        double x = boardData.player.getX()+yeni;
        double y = boardData.player.getY();

        int key = e.getKeyCode();
        if (key == ControlsSetting.shot ){
            if (boardData.inGame) {
                if (!boardData.player.shot.isVisible()) {
                    boardData.player.shot = new Shot(x, y);
                    Player.totalShotCount++;
                }
            }
        }

        if (key ==ControlsSetting.pause) {
            boardData.ekZaman += APP_FUNCTION.PauseResumeGame(boardData);
        }
    }
}
