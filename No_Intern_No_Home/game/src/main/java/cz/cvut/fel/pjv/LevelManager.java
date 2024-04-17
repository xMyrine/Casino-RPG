package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.objects.*;

import java.util.logging.Logger;
import java.util.logging.Level;

public class LevelManager {
    private FirstLevel firstLevel;
    public GamePanel gamePanel;
    private boolean firstLevelMessage = false;
    public boolean levelInProgress = true;
    private int levelNumber = 1;
    private Logger logger = Logger.getLogger(LevelManager.class.getName());

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(),
                gamePanel.countObjectsByClass(SlotMachine.class), gamePanel);
        this.gamePanel = gamePanel;
        logger.setLevel(Level.WARNING);
    }

    /*
     * Check if the first level is finished
     */
    public boolean checkLevelFirstFinished() {
        if (firstLevel.checkLevelFinished() && !firstLevelMessage) {
            gamePanel.ui.setAnnounceMessage("First Level finished");
            firstLevelMessage = true;
            levelInProgress = false;
            levelNumber++;
            logger.warning("Level number: " + levelNumber);
        }
        return firstLevel.checkLevelFinished();
    }

    // Get the current level number
    private int getLevelNumber() {
        return levelNumber;
    }

}
