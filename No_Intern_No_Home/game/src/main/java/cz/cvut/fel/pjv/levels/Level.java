package cz.cvut.fel.pjv.levels;

import java.util.logging.Logger;

public abstract class Level {

    protected boolean levelFinished;
    protected Logger logger;

    protected Level() {
    }

    public void setLevelFinished(boolean finished) {
        levelFinished = finished;
    }

    public boolean checkLevelFinished() {
        return levelFinished;
    }

}
