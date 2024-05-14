package cz.cvut.fel.pjv;

import java.awt.event.KeyListener;

import cz.cvut.fel.pjv.entity.*;
import cz.cvut.fel.pjv.levels.Level;

import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private GamePanel gamePanel;
    private boolean interact;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        interact = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method is intentionally left empty because keyTyped events are not used
        // in this application.
    }

    public boolean getInteract() {
        return interact;
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getDown() {
        return down;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.getGameState() == GamePanel.MENUSCREEN) {
            if (code == KeyEvent.VK_W) {
                UI.decreaseCommand(3);
            }
            if (code == KeyEvent.VK_S) {
                UI.increaseCommand(3);

            }
            if (code == KeyEvent.VK_ENTER && UI.getCommand() == 0) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            } else if (code == KeyEvent.VK_ENTER && UI.getCommand() == 1) {
                System.exit(0);
            } else if (code == KeyEvent.VK_ENTER && UI.getCommand() == 2) {
                gamePanel.changeGameState(GamePanel.MINIGAMESCREEN);
            }
            if (code == KeyEvent.VK_F1) {
                gamePanel.getSound().toggleSound();
            }
        } else if (gamePanel.getGameState() == GamePanel.GAMESCREEN) {
            if (code == KeyEvent.VK_F1) {
                gamePanel.getSound().toggleSound();
            }
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
                UI.setCommand(0);
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
            }
            if (code == KeyEvent.VK_I) {
                gamePanel.player.toggleInventory();
            }
            if (code == KeyEvent.VK_Q) {
                gamePanel.player.useCigarette();
            }
        } else if (gamePanel.getGameState() == GamePanel.PAUSESCREEN) {
            if (code == KeyEvent.VK_F1) {
                gamePanel.getSound().toggleSound();
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_ENTER && UI.getCommand() == 0) {
                gamePanel.changeGameState(GamePanel.MENUSCREEN);
            }
            if (code == KeyEvent.VK_ENTER && UI.getCommand() == 1) {
                gamePanel.changeGameState(GamePanel.CONTROLSSCREEN);
            }
            if (code == KeyEvent.VK_ENTER && UI.getCommand() == 2) {
                System.exit(0);
            }
            if (code == KeyEvent.VK_W) {
                UI.decreaseCommand(3);
            }
            if (code == KeyEvent.VK_S) {
                UI.increaseCommand(3);
            }

        } else if (gamePanel.getGameState() == GamePanel.DIALOGUESCREEN) {
            if (code == KeyEvent.VK_ESCAPE) {
                interact = false;
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_E) {
                interact = true;
                gamePanel.entities[gamePanel.player.getNpcIndex()].talk();
            }
            // ROOM MASTERS
            if (code == KeyEvent.VK_Y && (gamePanel.player.getNpcIndex() == 1 || gamePanel.player.getNpcIndex() == 2
                    || gamePanel.player.getNpcIndex() == 5 || gamePanel.player.getNpcIndex() == 7)) {
                gamePanel.changeGameState(GamePanel.MINIGAMESCREEN);
                // PROSTITUTE
            } else if (code == KeyEvent.VK_Y && gamePanel.player.getNpcIndex() == 3) {
                ((Prostitute) gamePanel.entities[gamePanel.player.getNpcIndex()]).increaseReputation();
                gamePanel.entities[gamePanel.player.getNpcIndex()].talk();
            } else if (code == KeyEvent.VK_X && gamePanel.player.getNpcIndex() == 3) {
                ((Prostitute) gamePanel.entities[gamePanel.player.getNpcIndex()]).decreaseReputation();
                gamePanel.entities[gamePanel.player.getNpcIndex()].talk();
            } else if (code == KeyEvent.VK_N) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            // ROULETTE
        } else if (gamePanel.getGameState() == GamePanel.SHOPSCREEN) {
            if (code == KeyEvent.VK_W) {
                UI.decreaseCommand(3);
            }
            if (code == KeyEvent.VK_S) {
                UI.increaseCommand(3);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_ENTER) {
                ((Shopkeeper) gamePanel.entities[4]).executeCommand(UI.getCommand());
            }
        } else if (gamePanel.getGameState() == GamePanel.CONTROLSSCREEN) {
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.PAUSESCREEN);
            }
        } else if (gamePanel.getGameState() == GamePanel.MINIGAMESCREEN) {
            if (LevelManager.getLevelNumber() >= 1 && gamePanel.player.getNpcIndex() == 1) {
                if (code == KeyEvent.VK_W) {
                    UI.decreaseCommand(38);
                }
                if (code == KeyEvent.VK_S) {
                    UI.increaseCommand(38);
                }
                if (code == KeyEvent.VK_E) {
                    gamePanel.levelManager.roulette.bet();
                }
                if (code == KeyEvent.VK_Q) {
                    gamePanel.levelManager.roulette.reduceBet();
                }
                if (code == KeyEvent.VK_ENTER
                        && (gamePanel.levelManager.roulette.getBet() <= gamePanel.player.getChipCount())) {
                    gamePanel.levelManager.roulette.startR(UI.getCommand());
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.changeGameState(GamePanel.GAMESCREEN);
                }

            } else if (LevelManager.getLevelNumber() >= 2 && gamePanel.player.getNpcIndex() == 2) {
                if (code == KeyEvent.VK_W) {
                    UI.decreaseCommand(2);
                }
                if (code == KeyEvent.VK_S) {
                    UI.increaseCommand(2);
                }
                if (code == KeyEvent.VK_ENTER && UI.getCommand() == 0
                        && gamePanel.levelManager.blackjack.getHitEnabled()) {
                    gamePanel.levelManager.blackjack.hit();
                } else if (code == KeyEvent.VK_ENTER && UI.getCommand() == 1) {
                    gamePanel.levelManager.blackjack.stand();
                }
                if (code == KeyEvent.VK_R) {
                    gamePanel.levelManager.blackjack.reset();
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.changeGameState(GamePanel.GAMESCREEN);
                }
            } else if (LevelManager.getLevelNumber() >= 3 && gamePanel.player.getNpcIndex() == 5) {
                if (code == KeyEvent.VK_W) {
                    UI.decreaseCommand(3);
                }
                if (code == KeyEvent.VK_S) {
                    UI.increaseCommand(3);
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.levelManager.pokermon.setMode(0);
                }
                if (code == KeyEvent.VK_ENTER && gamePanel.levelManager.pokermon.getMode() == 0) {
                    gamePanel.levelManager.pokermon.setMode(UI.getCommand() + 1);

                }
                if (code == KeyEvent.VK_ENTER && (gamePanel.levelManager.pokermon.getMode() == 1)
                        || (gamePanel.levelManager.pokermon.getMode() == 2)) {
                    gamePanel.levelManager.pokermon.executeCommand(UI.getCommand());
                }
                if (code == KeyEvent.VK_R) {
                    gamePanel.levelManager.pokermon.reset();
                }
            } else if (LevelManager.getLevelNumber() >= 3 && gamePanel.player.getNpcIndex() == 7) {
                if (code == KeyEvent.VK_ESCAPE) {
                    gamePanel.changeGameState(GamePanel.GAMESCREEN);
                }
                if (code == KeyEvent.VK_W) {
                    UI.decreaseCommand(2);
                }
                if (code == KeyEvent.VK_S) {
                    UI.increaseCommand(2);
                }
                if (code == KeyEvent.VK_Q) {
                    gamePanel.levelManager.getDices().bet();
                }
                if (code == KeyEvent.VK_E) {
                    gamePanel.levelManager.getDices().reduceBet();
                }
                if (code == KeyEvent.VK_ENTER) {
                    gamePanel.levelManager.getDices().startD(UI.getCommand());
                }
            }
        } else if (gamePanel.getGameState() == GamePanel.CRAFTSCREEN) {
            if (code == KeyEvent.VK_W) {
                UI.decreaseCommand(3);
            }
            if (code == KeyEvent.VK_S) {
                UI.increaseCommand(3);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
            }
            if (code == KeyEvent.VK_ENTER) {
                ((Craftsman) gamePanel.entities[6]).craft(UI.getCommand());
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