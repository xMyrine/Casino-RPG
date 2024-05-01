package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

public class ThirdLevel extends Level {
    private GamePanel gamePanel;

    public ThirdLevel(GamePanel gamePanel) {
        this.levelFinished = false;
        this.gamePanel = gamePanel;

    }

    @Override
    public boolean checkLevelFinished() {
        if (gamePanel.levelManager.pokermon.isFinished()) {
            this.levelFinished = true;
            return true;
        }
        return false;
    }
}
