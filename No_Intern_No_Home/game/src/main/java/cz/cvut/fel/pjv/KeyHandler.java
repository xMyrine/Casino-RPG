package cz.cvut.fel.pjv;

import java.awt.event.KeyListener;

import cz.cvut.fel.pjv.entity.Prostitute;
import cz.cvut.fel.pjv.entity.Shopkeeper;

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
                UI.command--;
                if (UI.command < 0) {
                    UI.command = 2;
                }
                UI.command = UI.command % 3;
            }
            if (code == KeyEvent.VK_S) {
                UI.command++;
                UI.command = UI.command % 3;

            }
            if (code == KeyEvent.VK_ENTER && UI.command == 0) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            } else if (code == KeyEvent.VK_ENTER && UI.command == 1) {
                System.exit(0);
            } else if (code == KeyEvent.VK_ENTER && UI.command == 2) {
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
                UI.command = 0;
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
            }
            if (code == KeyEvent.VK_I) {
                gamePanel.player.toggleInventory();
            }
        } else if (gamePanel.getGameState() == GamePanel.PAUSESCREEN) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_ENTER && UI.command == 0) {
                gamePanel.changeGameState(GamePanel.MENUSCREEN);
            }
            if (code == KeyEvent.VK_ENTER && UI.command == 1) {
                gamePanel.changeGameState(GamePanel.CONTROLSSCREEN);
            }
            if (code == KeyEvent.VK_ENTER && UI.command == 2) {
                System.exit(0);
            }
            if (code == KeyEvent.VK_W) {
                UI.command--;
                if (UI.command < 0) {
                    UI.command = 2;
                }
                UI.command = UI.command % 3;
            }
            if (code == KeyEvent.VK_S) {
                UI.command++;
                UI.command = UI.command % 3;

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
            if (code == KeyEvent.VK_Y && (gamePanel.player.npcIndex == 1 || gamePanel.player.npcIndex == 2
                    || gamePanel.player.npcIndex == 5)) {
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
        } else if (gamePanel.getGameState() == GamePanel.SHOPSCREEN) {
            if (code == KeyEvent.VK_W) {
                UI.command--;
                if (UI.command < 0) {
                    UI.command = 2;
                }
                UI.command = UI.command % 3;
            }
            if (code == KeyEvent.VK_S) {
                UI.command++;
                UI.command = UI.command % 3;

            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_ENTER) {
                ((Shopkeeper) gamePanel.entities[4]).executeCommand(UI.command);
                System.out.println(UI.command);
            }
        } else if (gamePanel.getGameState() == GamePanel.CONTROLSSCREEN) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.PAUSESCREEN);
            }
        } else if (gamePanel.getGameState() == GamePanel.MINIGAMESCREEN) {
            if (gamePanel.levelManager.getLevelNumber() >= 1 && gamePanel.player.npcIndex == 1) {
                if (code == KeyEvent.VK_W) {
                    UI.command--;
                    if (UI.command < 0) {
                        UI.command = 38;

                    }
                }
                if (code == KeyEvent.VK_S) {
                    UI.command++;
                    UI.command = UI.command % 38;
                }
                if (code == KeyEvent.VK_E) {
                    gamePanel.levelManager.roulette.bet();
                }
                if (code == KeyEvent.VK_Q) {
                    gamePanel.levelManager.roulette.reduceBet();
                }
                if (code == KeyEvent.VK_ENTER
                        && (gamePanel.levelManager.roulette.getBet() <= gamePanel.player.getChipCount())) {
                    gamePanel.levelManager.roulette.startR(UI.command);
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.changeGameState(GamePanel.GAMESCREEN);
                }

            } else if (gamePanel.levelManager.getLevelNumber() >= 2 && gamePanel.player.npcIndex == 2) {
                if (code == KeyEvent.VK_W) {
                    UI.command--;
                    if (UI.command < 0) {
                        UI.command = 1;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    UI.command++;
                    UI.command = UI.command % 2;
                }
                if (code == KeyEvent.VK_ENTER && UI.command == 0
                        && gamePanel.levelManager.blackjack.getHitEnabled()) {
                    gamePanel.levelManager.blackjack.hit();
                } else if (code == KeyEvent.VK_ENTER && UI.command == 1) {
                    gamePanel.levelManager.blackjack.stand();
                }
                if (code == KeyEvent.VK_R) {
                    gamePanel.levelManager.blackjack.reset();
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.changeGameState(GamePanel.GAMESCREEN);
                }
            } else if (gamePanel.levelManager.getLevelNumber() >= 3 && gamePanel.player.npcIndex == 5) {
                if (code == KeyEvent.VK_W) {
                    UI.command--;
                    if (UI.command < 0) {
                        UI.command = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    UI.command++;
                    UI.command = UI.command % 3;
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.levelManager.pokermon.setMode(0);
                }
                if (code == KeyEvent.VK_ENTER && gamePanel.levelManager.pokermon.getMode() == 0) {
                    gamePanel.levelManager.pokermon.setMode(UI.command + 1);

                }
                if (code == KeyEvent.VK_ENTER && gamePanel.levelManager.pokermon.getMode() == 1) {
                    gamePanel.levelManager.pokermon.executeCommand(UI.command);
                }
            }
            // if (code == KeyEvent.VK_ESCAPE) {
            // gamePanel.changeGameState(GamePanel.GAMESCREEN);
            // }
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