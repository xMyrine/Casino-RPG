package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

public class SecondLevel extends Level {

    private GamePanel gamePanel;
    private boolean miniGameFinished;

    public SecondLevel(GamePanel gamePanel) {
        this.miniGameFinished = true; // !TO BE CHANGED BECASE I DONT HAVE MINIGAME YET
        this.levelFinished = false;
        this.gamePanel = gamePanel;
    }

    private boolean isLuckSexyAndChipsBlaze() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLuckSexyAndChipsBlaze'");
    }

    @Override
    public boolean checkLevelFinished() {
        if (isLuckSexyAndChipsBlaze() && miniGameFinished) {
            logger.info("Level finished");
            return true;
        }
        return false;
    }

}
