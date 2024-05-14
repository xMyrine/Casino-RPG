package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

/**
 * SecondLevel is the second level of the game.
 * The player has to get laid and win the blackjack minigame to finish the
 * level.
 * 
 * @Author Minh Tu Pham
 */
public class SecondLevel extends Level {
    private boolean gotLaid;

    public SecondLevel(GamePanel gamePanel) {
        super();
        this.levelFinished = false;
        this.gamePanel = gamePanel;
        this.gotLaid = false;
    }

    /**
     * Sets the gotLaid to true and checks if the level is finished.
     */
    public void getLaid() {
        gotLaid = true;
        gamePanel.getLevelManager().checkLevelFinished();
    }

    /**
     * Checks if the level is finished.
     * Checks if the player has won the blackjack minigame and got laid.
     */
    @Override
    public boolean checkLevelFinished() {
        if (gotLaid && gamePanel.getLevelManager().getBlackjack().getFinished()) {
            this.levelFinished = true;
            logger.info("Second level finished");
            return true;
        }
        return false;
    }

    public void setGotLaid(boolean gotLaid) {
        this.gotLaid = gotLaid;
    }

}
