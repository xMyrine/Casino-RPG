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
    private static int levelNumber = 1;
    private Logger logger = Logger.getLogger(LevelManager.class.getName());

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(), 2, gamePanel);
        this.gamePanel = gamePanel;
        logger.setLevel(Level.WARNING);
    }

    /*
     * Check if the first level is finished
     */
    public boolean checkLevelFinished() {
        if (levelNumber == 1) {
            if (firstLevel.checkLevelFinished() && !firstLevelMessage) {
                gamePanel.ui.setAnnounceMessage("First Level finished");
                firstLevelMessage = true;
                levelInProgress = false;
                levelNumber++;
                openDoors();
                logger.warning("Level number: " + levelNumber);
                return firstLevel.checkLevelFinished();
            }
        }
        return false;
    }

    // Get the current level number
    public static int getLevelNumber() {
        return levelNumber;
    }

    private void openDoors() {
        for (int i = 0; i < gamePanel.objects.length; i++) {
            if (gamePanel.objects[i] instanceof Door) {
                ((Door) gamePanel.objects[i])
                        .changeState((LevelManager.getLevelNumber() > gamePanel.objectsSpawner
                                .getCurrentObjectLevelSpawned()));
                logger.log(Level.INFO, "Door state changed");
                if (((Door) gamePanel.objects[i]).getState()
                        && !((Door) gamePanel.objects[i]).open) {
                    ((Door) gamePanel.objects[i]).open = true;
                    gamePanel.sound.playMusic(4);
                }
            }
        }
    }

}
