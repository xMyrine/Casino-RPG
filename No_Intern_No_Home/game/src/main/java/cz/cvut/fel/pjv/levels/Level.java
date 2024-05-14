package cz.cvut.fel.pjv.levels;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.GamePanel;

/**
 * Level is an abstract class that represents a level of the game.
 * 
 * @Author Minh Tu Pham
 */
public abstract class Level {

    protected boolean levelFinished;
    protected Logger logger;
    protected GamePanel gamePanel;

    protected Level() {
        logger = Logger.getLogger(Level.class.getName());
        logger.setLevel(java.util.logging.Level.INFO);
    }

    /**
     * Sets the levelFinished to true.
     * 
     * @param finished
     */
    public void setLevelFinished(boolean finished) {
        levelFinished = finished;
    }

    /**
     * Checks if the level is finished.
     * 
     * @return
     */
    public boolean checkLevelFinished() {
        return levelFinished;
    }

}
