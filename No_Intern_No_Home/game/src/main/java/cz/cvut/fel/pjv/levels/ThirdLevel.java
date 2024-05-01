package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

public class ThirdLevel extends Level {
    private GamePanel gamePanel;
    private boolean pokerManBeaten;

    public ThirdLevel(GamePanel gamePanel) {
        this.levelFinished = false;
        this.gamePanel = gamePanel;
        this.pokerManBeaten = false;
    }

    @Override
    public boolean checkLevelFinished() {
        if (pokerManBeaten && gamePanel.levelManager.blackjack.getFinished()) {
            this.levelFinished = true;
            return true;
        }
        return false;
    }
}
