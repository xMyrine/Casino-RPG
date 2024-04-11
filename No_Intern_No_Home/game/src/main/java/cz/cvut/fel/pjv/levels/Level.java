package cz.cvut.fel.pjv.levels;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.GamePanel;

public abstract class Level {

    protected boolean levelFinished;
    protected Logger logger;
    protected boolean firstLevelFinished;

    protected Level() {
    }

    public void setLevelFinished(boolean finished) {
        levelFinished = finished;
    }

    public boolean checkLevelFinished() {
        return levelFinished;
    }

    public abstract boolean isMiniGameFinished();

}
