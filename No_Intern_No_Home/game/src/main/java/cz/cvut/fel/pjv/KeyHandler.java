package cz.cvut.fel.pjv;

import java.awt.event.KeyListener;

import cz.cvut.fel.pjv.entity.Prostitute;

import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

    public boolean up, down, left, right;
    private GamePanel gamePanel;
    public boolean interact;

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

        if (gamePanel.getGameState() == GamePanel.MENUSCREEN) {
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.command--;
                if (gamePanel.ui.command < 0) {
                    gamePanel.ui.command = 2;
                }
                gamePanel.ui.command = gamePanel.ui.command % 3;
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.command++;
                gamePanel.ui.command = gamePanel.ui.command % 3;

            }
            if (code == KeyEvent.VK_ENTER && gamePanel.ui.command == 0) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            } else if (code == KeyEvent.VK_ENTER && gamePanel.ui.command == 1) {
                System.exit(0);
            } else if (code == KeyEvent.VK_ENTER && gamePanel.ui.command == 2) {
                gamePanel.changeGameState(GamePanel.MINIGAMESCREEN);
            }

        } else if (gamePanel.getGameState() == GamePanel.GAMESCREEN) {
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
                gamePanel.changeGameState(GamePanel.PAUSESCREEN);
                gamePanel.ui.command = 0;
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
            }
        } else if (gamePanel.getGameState() == GamePanel.PAUSESCREEN) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_ENTER && gamePanel.ui.command == 0) {
                gamePanel.changeGameState(GamePanel.MENUSCREEN);
            }
            if (code == KeyEvent.VK_ENTER && gamePanel.ui.command == 1) {
                System.exit(0);
            }
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.command--;
                if (gamePanel.ui.command < 0) {
                    gamePanel.ui.command = 1;
                }
                gamePanel.ui.command = gamePanel.ui.command % 2;
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.command++;
                gamePanel.ui.command = gamePanel.ui.command % 2;

            }

        } else if (gamePanel.getGameState() == GamePanel.DIALOGUESCREEN) {
            if (code == KeyEvent.VK_ESCAPE) {
                interact = false;
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
                gamePanel.entities[gamePanel.player.npcIndex].talk();
            }
            // ROOM MASTERS
            if (code == KeyEvent.VK_Y && (gamePanel.player.npcIndex == 1 || gamePanel.player.npcIndex == 2)) {
                gamePanel.changeGameState(GamePanel.MINIGAMESCREEN);
                // PROSTITUTE
            } else if (code == KeyEvent.VK_Y && gamePanel.player.npcIndex == 3) {
                ((Prostitute) gamePanel.entities[gamePanel.player.npcIndex]).increaseReputation();
                gamePanel.entities[gamePanel.player.npcIndex].talk();
            } else if (code == KeyEvent.VK_X && gamePanel.player.npcIndex == 3) {
                ((Prostitute) gamePanel.entities[gamePanel.player.npcIndex]).decreaseReputation();
                gamePanel.entities[gamePanel.player.npcIndex].talk();
            } else if (code == KeyEvent.VK_N) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            // ROULETTE
        } else if (gamePanel.getGameState() == GamePanel.MINIGAMESCREEN
                && gamePanel.levelManager.getLevelNumber() >= 1 && gamePanel.player.npcIndex == 1) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.command--;
                if (gamePanel.ui.command < 0) {
                    gamePanel.ui.command = 38;
                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.command++;
                if (gamePanel.ui.command > 38) {
                    gamePanel.ui.command = 0;
                }
                System.out.println(gamePanel.ui.command);
            }
            if (code == KeyEvent.VK_E) {
                gamePanel.levelManager.roulette.bet();
            }
            if (code == KeyEvent.VK_Q) {
                gamePanel.levelManager.roulette.reduceBet();
            }
            if (code == KeyEvent.VK_ENTER
                    && (gamePanel.levelManager.roulette.getBet() <= gamePanel.player.getChipCount())) {
                gamePanel.levelManager.roulette.startR(gamePanel.ui.command);
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