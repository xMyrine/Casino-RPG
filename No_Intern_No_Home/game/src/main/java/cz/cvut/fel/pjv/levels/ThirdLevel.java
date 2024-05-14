package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

/**
 * ThirdLevel is the third level of the game.
 * The player has to finish the pokermon minigame and the dices minigame to
 * finish
 * the level.
 * 
 * @Author Minh Tu Pham
 */
public class ThirdLevel extends Level {

    public ThirdLevel(GamePanel gamePanel) {
        super();
        this.levelFinished = false;
        this.gamePanel = gamePanel;

    }

    /**
     * Checks if the level is finished.
     * Player has to finish the pokermon minigame and the dices minigame to finish
     * the level.
     */
    @Override
    public boolean checkLevelFinished() {
        if (gamePanel.getLevelManager().getPokermon().isFinished() && gamePanel.getLevelManager().getDices().end()) {
            this.levelFinished = true;
            logger.info("Third level finished");
            return true;
        }
        return false;
    }
}
