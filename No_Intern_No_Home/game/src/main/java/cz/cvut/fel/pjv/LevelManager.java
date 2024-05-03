package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.minigames.Blackjack;
import cz.cvut.fel.pjv.minigames.Pokermon;
import cz.cvut.fel.pjv.minigames.Roulette;
import cz.cvut.fel.pjv.objects.*;

import java.util.logging.Logger;
import java.util.logging.Level;

public class LevelManager {
    public FirstLevel firstLevel;
    public SecondLevel secondLevel;
    public ThirdLevel thirdLevel;
    public GamePanel gamePanel;
    private boolean firstLevelMessage = false;
    private boolean secondLevelMessage = false;
    private boolean thirdLevelmessage = false;
    public boolean levelInProgress = true;
    private static int levelNumber = 1; // ! Change to 1
    private Logger logger = Logger.getLogger(LevelManager.class.getName());
    public Roulette roulette;
    public Blackjack blackjack;
    public Pokermon pokermon;

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(), 5, gamePanel);
        this.secondLevel = new SecondLevel(gamePanel);
        this.thirdLevel = new ThirdLevel(gamePanel);
        this.gamePanel = gamePanel;
        logger.setLevel(Level.WARNING);
        roulette = new Roulette(gamePanel);
        blackjack = new Blackjack(gamePanel);
        pokermon = new Pokermon(gamePanel);
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
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
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
        } else if (levelNumber == 3) {
            levelInProgress = true;
            System.out.println(thirdLevel.checkLevelFinished());
            if (thirdLevel.checkLevelFinished() && !thirdLevelmessage) {
                System.out.println("Third Level finished");
                gamePanel.ui.setAnnounceMessage("Third Level finished");
                thirdLevelmessage = true;
                levelInProgress = false;
                levelNumber++;
                openDoors();
                logger.warning("Level number: " + levelNumber);
                return thirdLevel.checkLevelFinished();
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
