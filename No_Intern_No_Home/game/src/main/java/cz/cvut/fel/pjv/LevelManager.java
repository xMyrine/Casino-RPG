package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.minigames.*;
import cz.cvut.fel.pjv.objects.*;

import java.util.logging.Logger;
import java.util.Random;
import java.util.logging.Level;

public class LevelManager {
    public FirstLevel firstLevel;
    public SecondLevel secondLevel;
    public ThirdLevel thirdLevel;
    private GamePanel gamePanel;
    private boolean firstLevelMessage = false;
    private boolean secondLevelMessage = false;
    private boolean thirdLevelmessage = false;
    private boolean levelInProgress = true;
    private static int levelNumber = 1; // ! Change to 1
    private Logger logger = Logger.getLogger(LevelManager.class.getName());
    public Roulette roulette;
    public Blackjack blackjack;
    public Pokermon pokermon;
    protected Dices dices;
    private Random rand = new Random();

    private static final String LEVEL_NUMBER_MSG = "Level number: {0}";

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(), 5, gamePanel);
        this.secondLevel = new SecondLevel(gamePanel);
        this.thirdLevel = new ThirdLevel(gamePanel);
        this.gamePanel = gamePanel;
        logger.setLevel(Level.WARNING);
        roulette = new Roulette(gamePanel);
        blackjack = new Blackjack(gamePanel);
        pokermon = new Pokermon(gamePanel);
        dices = new Dices(gamePanel);
    }

    public boolean isLevelInProgress() {
        return levelInProgress;
    }

    public void setLevelInProgress(boolean levelInProgress) {
        this.levelInProgress = levelInProgress;
    }

    private static void increaseLevel() {
        levelNumber++;
    }

    /**
     * Check if the first level is finished and increment the level number if it is
     */
    public boolean checkLevelFinished() {
        if (levelNumber == 1) {
            if (firstLevel.checkLevelFinished() && !firstLevelMessage) {
                gamePanel.gameUI.setAnnounceMessage("First Level finished");
                firstLevelMessage = true;
                levelInProgress = false;
                increaseLevel();
                openDoors();
                rewardPlayer();
                gamePanel.changeGameState(GamePanel.GAMESCREEN);
                logger.log(Level.WARNING, LEVEL_NUMBER_MSG, levelNumber);
                return firstLevel.checkLevelFinished();
            }
        } else if (levelNumber == 2) {
            if (secondLevel.checkLevelFinished() && !secondLevelMessage) {
                gamePanel.gameUI.setAnnounceMessage("Second Level finished");
                secondLevelMessage = true;
                levelInProgress = false;
                increaseLevel();
                openDoors();
                rewardPlayer();
                logger.log(Level.WARNING, LEVEL_NUMBER_MSG, levelNumber);
                return secondLevel.checkLevelFinished();
            }
        } else if (levelNumber == 3) {
            if (thirdLevel.checkLevelFinished() && !thirdLevelmessage) {
                gamePanel.gameUI.setAnnounceMessage("Third Level finished");
                thirdLevelmessage = true;
                levelInProgress = false;
                increaseLevel();
                openDoors();
                rewardPlayer();
                logger.log(Level.WARNING, LEVEL_NUMBER_MSG, levelNumber);
                return thirdLevel.checkLevelFinished();
            }
        }
        return false;
    }

    public static int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Open the doors based on the level number
     */
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

    /**
     * Reward the player with a special item fragment
     * Based on the random number generated
     */
    private void rewardPlayer() {
        int reward = rand.nextInt(3);
        gamePanel.player.setSpecialItemsFragmentCount(reward,
                gamePanel.player.getSpecialItemsFragmentCount(reward) + 1);
    }

    public Dices getDices() {
        return dices;
    }
}