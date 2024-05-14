package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

/**
 * FourthLevel is the fourth level of the game.
 * The player has to beat the last boss at rock paper scissors to finish the
 * level.
 * 
 * @Author Minh Tu Pham
 */
public class FourthLevel extends Level {

    public FourthLevel(GamePanel gp) {
        super();
        this.gamePanel = gp;
        this.levelFinished = false;
    }

    /**
     * Checks if the level is finished.
     * Checks if the player has beaten last boss at rock paper scissors.
     */
    @Override
    public boolean checkLevelFinished() {
        if (gamePanel.getLevelManager().getRps().end() || gamePanel.getLevelManager().getRps().getBusted()) {
            this.levelFinished = true;
            logger.info("Fourth level finished");
            return true;
        }
        return false;
    }
}
