package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.minigames.Roulette;
import cz.cvut.fel.pjv.objects.*;

import java.util.logging.Logger;
import java.util.logging.Level;

public class LevelManager {
    public FirstLevel firstLevel;
    public SecondLevel secondLevel;
    public GamePanel gamePanel;
    private boolean firstLevelMessage = false;
    private boolean secondLevelMessage = false;
    public boolean levelInProgress = true;
    private static int levelNumber = 1;
    private Logger logger = Logger.getLogger(LevelManager.class.getName());
    public Roulette roulette;

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(), 5, gamePanel);
        this.secondLevel = new SecondLevel(gamePanel);
        this.gamePanel = gamePanel;
        logger.setLevel(Level.WARNING);
        roulette = new Roulette(gamePanel);

    }

    /*
     * Check if the first level is finished
     */
    public boolean checkLevelFinished() {
        System.out.println("Level number: " + levelNumber);
        if (levelNumber == 1) {
            if (firstLevel.checkLevelFinished() && !firstLevelMessage) {
                gamePanel.ui.setAnnounceMessage("First Level finished");
                firstLevelMessage = true;
                levelInProgress = false;
                levelNumber++;
                openDoors();
                gamePanel.gameState = GamePanel.GAMESCREEN;
                logger.warning("Level number: " + levelNumber);
                return firstLevel.checkLevelFinished();
            }
        } else if (levelNumber == 2) {
            levelInProgress = true;
            if (secondLevel.checkLevelFinished() && !secondLevelMessage) {
                gamePanel.ui.setAnnounceMessage("Second Level finished");
                secondLevelMessage = true;
                levelInProgress = false;
                levelNumber++;
                openDoors();
                logger.warning("Level number: " + levelNumber);
                return secondLevel.checkLevelFinished();
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
