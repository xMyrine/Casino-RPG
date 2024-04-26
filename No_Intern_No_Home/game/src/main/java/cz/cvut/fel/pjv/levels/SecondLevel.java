package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

public class SecondLevel extends Level {

    private GamePanel gamePanel;
    public boolean gotLaid;

    public SecondLevel(GamePanel gamePanel) {
        this.levelFinished = false;
        this.gamePanel = gamePanel;
        this.gotLaid = false;
    }

    public void getLaid() {
        gotLaid = true;
        gamePanel.levelManager.checkLevelFinished();
    }

    @Override
    public boolean checkLevelFinished() {
        if (gotLaid && gamePanel.levelManager.blackjack.getFinished()) {
            this.levelFinished = true;
            return true;
        }
        return false;
    }

}
