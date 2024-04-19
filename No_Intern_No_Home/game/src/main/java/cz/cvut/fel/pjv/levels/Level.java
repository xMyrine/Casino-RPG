package cz.cvut.fel.pjv.levels;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.GamePanel;

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

    private boolean isMiniGameFinished() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isMiniGameFinished'");
    }

}
