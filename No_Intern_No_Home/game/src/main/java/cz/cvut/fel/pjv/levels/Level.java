package cz.cvut.fel.pjv.levels;

public interface Level {

    public void setLevelFinished(boolean levelFinished);

    public boolean checkLevelFinished();

    public boolean isMiniGameFinished();

}
