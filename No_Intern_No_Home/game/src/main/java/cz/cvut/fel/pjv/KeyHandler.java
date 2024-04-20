package cz.cvut.fel.pjv;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

    public boolean up, down, left, right;
    private GamePanel gamePanel;
    public boolean interact;
    public boolean yes;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        interact = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.getGameState() == GamePanel.gameScreen) {
            if (code == KeyEvent.VK_W) {
                up = true;
            }
            if (code == KeyEvent.VK_S) {
                down = true;
            }
            if (code == KeyEvent.VK_A) {
                left = true;
            }
            if (code == KeyEvent.VK_D) {
                right = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.pauseScreen);
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
            }
        } else if (gamePanel.getGameState() == GamePanel.pauseScreen) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.gameScreen);
            }
        } else if (gamePanel.getGameState() == GamePanel.dialogueScreen) {
            if (code == KeyEvent.VK_ESCAPE) {
                interact = false;
                gamePanel.changeGameState(GamePanel.gameScreen);
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
                gamePanel.entities[gamePanel.player.npcIndex].talk();
            }
            if (code == KeyEvent.VK_Y) {
                yes = true;
                switch (LevelManager.getLevelNumber()) {
                    case 1:
                        gamePanel.levelManager.firstLevel.setMiniGameFinished(true);
                        break;
                    default:
                        break;
                }
            } else if (code == KeyEvent.VK_N) {
                yes = false;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            up = false;
        }
        if (code == KeyEvent.VK_S) {
            down = false;
        }
        if (code == KeyEvent.VK_A) {
            left = false;
        }
        if (code == KeyEvent.VK_D) {
            right = false;
        }
    }

}