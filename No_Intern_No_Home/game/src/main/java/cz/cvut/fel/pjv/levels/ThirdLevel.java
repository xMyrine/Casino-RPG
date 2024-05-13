package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

public class ThirdLevel extends Level {
    private GamePanel gamePanel;

    public ThirdLevel(GamePanel gamePanel) {
        super();
        this.levelFinished = false;
        this.gamePanel = gamePanel;

    }

    @Override
    public boolean checkLevelFinished() {
        if (gamePanel.getLevelManager().pokermon.isFinished()) {
            this.levelFinished = true;
            logger.info("Third level finished");
            return true;
        }
        return false;
    }
}
