package cz.cvut.fel.pjv;

import java.awt.event.KeyListener;

import cz.cvut.fel.pjv.entity.*;
import cz.cvut.fel.pjv.saves.Storage;

import java.awt.event.KeyEvent;

/**
 * KeyHandler is a class that handles key events in the game.
 * 
 * @Author Minh Tu Pham
 */
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

    /**
     * Handles key presses in the game depending on the current game state.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gamePanel.getGameState()) {
            case GamePanel.MENUSCREEN:
                handleMenuScreenKeyPress(code);
                break;
            case GamePanel.GAMESCREEN:
                handleGameScreenKeyPress(code);
                break;
            case GamePanel.PAUSESCREEN:
                handlePauseScreenKeyPress(code);
                break;
            case GamePanel.DIALOGUESCREEN:
                handleDialogueScreenKeyPress(code);
                break;
            case GamePanel.SHOPSCREEN:
                handleShopScreenKeyPress(code);
                break;
            case GamePanel.CONTROLSSCREEN:
                handleControlsScreenKeyPress(code);
                break;
            case GamePanel.MINIGAMESCREEN:
                handleMinigameScreenKeyPress(code);
                break;
            case GamePanel.CRAFTSCREEN:
                handleCraftScreenKeyPress(code);
                break;
            case GamePanel.ENDSCREEN:
                handleEndScreenKeyPress(code);
                break;
            default:
                break;
        }
    }

    /**
     * Handles key presses in the menu screen.
     * 
     * @param code
     */
    private void handleMenuScreenKeyPress(int code) {
        if (code == KeyEvent.VK_W) {
            UI.decreaseCommand(3);
        }
        if (code == KeyEvent.VK_S) {
            UI.increaseCommand(3);
        }
        if (code == KeyEvent.VK_ENTER && UI.getCommand() == 0) {
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        } else if (code == KeyEvent.VK_ENTER && UI.getCommand() == 1) {
            gamePanel.storageLoader.load();
        } else if (code == KeyEvent.VK_ENTER && UI.getCommand() == 2) {
            System.exit(0);
        }

        if (code == KeyEvent.VK_F1) {
            gamePanel.getSound().toggleSound();
        }
    }

    /**
     * Handles key presses in the game screen.
     * 
     * @param code
     */
    private void handleGameScreenKeyPress(int code) {
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
    }

    /**
     * Handles key presses in the pause screen.
     * 
     * @param code
     */
    private void handlePauseScreenKeyPress(int code) {
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
            gamePanel.storageLoader.save();
        }
        if (code == KeyEvent.VK_ENTER && UI.getCommand() == 3) {
            System.exit(0);
        }
        if (code == KeyEvent.VK_W) {
            UI.decreaseCommand(4);
        }
        if (code == KeyEvent.VK_S) {
            UI.increaseCommand(4);
        }
    }

    /**
     * Handles key presses in the dialogue screen.
     * 
     * @param code
     */
    private void handleDialogueScreenKeyPress(int code) {
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
                || gamePanel.player.getNpcIndex() == 5 || gamePanel.player.getNpcIndex() == 7
                || gamePanel.player.getNpcIndex() == 8)) {
            if (gamePanel.player.getNpcIndex() == 5) {
                gamePanel.getLevelManager().getPokermon().reset(true);
            }
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
    }

    /**
     * Handles key presses in the shop screen.
     * 
     * @param code
     */
    private void handleShopScreenKeyPress(int code) {
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
    }

    /**
     * Handles key presses in the controls screen.
     * 
     * @param code
     */
    private void handleControlsScreenKeyPress(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.changeGameState(GamePanel.PAUSESCREEN);
        }
    }

    /**
     * Handles key presses in the minigame screen.
     * 
     * @param code
     */
    private void handleMinigameScreenKeyPress(int code) {
        int levelNumber = LevelManager.getLevelNumber();
        int npcIndex = gamePanel.player.getNpcIndex();

        if (levelNumber >= 1 && npcIndex == 1) {
            handleRouletteKeyPress(code);
        } else if (levelNumber >= 2 && npcIndex == 2) {
            handleBlackjackKeyPress(code);
        } else if (levelNumber >= 3 && npcIndex == 5) {
            handlePokermonKeyPress(code);
        } else if (levelNumber >= 3 && npcIndex == 7) {
            handleDicesKeyPress(code);
        } else if (levelNumber >= 4 && npcIndex == 8) {
            handleRpsKeyPress(code);
        }
    }

    /**
     * Handles key presses in the roulette minigame.
     * 
     * @param code
     */
    private void handleRouletteKeyPress(int code) {
        if (code == KeyEvent.VK_W) {
            UI.decreaseCommand(38);
        }
        if (code == KeyEvent.VK_S) {
            UI.increaseCommand(38);
        }
        if (code == KeyEvent.VK_E) {
            gamePanel.levelManager.getRoulette().bet();
        }
        if (code == KeyEvent.VK_Q) {
            gamePanel.levelManager.getRoulette().reduceBet();
        }
        if (code == KeyEvent.VK_ENTER
                && (gamePanel.levelManager.getRoulette().getBet() <= gamePanel.player.getChipCount())) {
            gamePanel.levelManager.getRoulette().startR(UI.getCommand());
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        }
    }

    /**
     * Handles key presses in the blackjack minigame.
     * 
     * @param code
     */
    private void handleBlackjackKeyPress(int code) {
        if (code == KeyEvent.VK_W) {
            UI.decreaseCommand(2);
        }
        if (code == KeyEvent.VK_S) {
            UI.increaseCommand(2);
        }
        if (code == KeyEvent.VK_ENTER && UI.getCommand() == 0
                && gamePanel.levelManager.getBlackjack().getHitEnabled()) {
            gamePanel.levelManager.getBlackjack().hit();
        } else if (code == KeyEvent.VK_ENTER && UI.getCommand() == 1) {
            gamePanel.levelManager.getBlackjack().stand();
        }
        if (code == KeyEvent.VK_R) {
            gamePanel.levelManager.getBlackjack().reset();
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        }
    }

    /**
     * Handles key presses in the pokermon minigame.
     * 
     * @param code
     */
    private void handlePokermonKeyPress(int code) {
        if (code == KeyEvent.VK_W) {
            UI.decreaseCommand(3);
        }
        if (code == KeyEvent.VK_S) {
            UI.increaseCommand(3);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.levelManager.getPokermon().setMode(0);
        }
        if (code == KeyEvent.VK_ENTER && gamePanel.levelManager.getPokermon().getMode() == 0) {
            gamePanel.levelManager.getPokermon().setMode(UI.getCommand() + 1);

        }
        if (code == KeyEvent.VK_ENTER && (gamePanel.levelManager.getPokermon().getMode() == 1)
                || (gamePanel.levelManager.getPokermon().getMode() == 2)) {
            gamePanel.levelManager.getPokermon().executeCommand(UI.getCommand());
        }
        if (code == KeyEvent.VK_R) {
            gamePanel.levelManager.getPokermon().reset(false);
        }
    }

    /**
     * Handles key presses in the dices minigame.
     * 
     * @param code
     */
    private void handleDicesKeyPress(int code) {
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

    /**
     * Handles key presses in the rps minigame.
     * 
     * @param code
     */
    private void handleRpsKeyPress(int code) {
        if (gamePanel.getPlayer().getChipCount() > 1000) {
            if (code == KeyEvent.VK_W) {
                UI.decreaseCommand(3);
            }
            if (code == KeyEvent.VK_S) {
                UI.increaseCommand(3);
            }
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.levelManager.rps.getChoices(UI.getCommand());
            }
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        }
    }

    /**
     * Handles key presses in the craft screen.
     * 
     * @param code
     */
    private void handleCraftScreenKeyPress(int code) {
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

    /**
     * Handles key presses in the end screen.
     * 
     * @param code
     */
    private void handleEndScreenKeyPress(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    /**
     * Handles key releases in the game.
     * 
     * @param e key event
     */
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