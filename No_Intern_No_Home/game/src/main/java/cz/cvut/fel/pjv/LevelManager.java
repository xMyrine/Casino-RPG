package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.minigames.*;
import cz.cvut.fel.pjv.objects.*;

import java.util.logging.Logger;
import java.util.Random;
import java.util.logging.Level;

/**
 * LevelManager is a class that manages the levels in the game.
 * It checks if the level is finished and increments the level number.
 * It also rewards the player with a special item fragment.
 * 
 * @Author Minh Tu Pham
 */
public class LevelManager {
    private FirstLevel firstLevel;
    private SecondLevel secondLevel;
    private ThirdLevel thirdLevel;
    private FourthLevel fourthLevel;
    private GamePanel gamePanel;
    private boolean firstLevelMessage = false;
    private boolean secondLevelMessage = false;
    private boolean thirdLevelmessage = false;
    private boolean levelInProgress = true;
    private static int levelNumber = 1; // ! Change to 1
    private Logger logger = Logger.getLogger(LevelManager.class.getName());
    private Roulette roulette;
    private Blackjack blackjack;
    private Pokermon pokermon;
    protected Dices dices;
    protected RPS rps;
    private Random rand = new Random();

    private static final String LEVEL_NUMBER_MSG = "Level number: {0}";

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(), 5, gamePanel);
        this.secondLevel = new SecondLevel(gamePanel);
        this.thirdLevel = new ThirdLevel(gamePanel);
        this.fourthLevel = new FourthLevel(gamePanel);
        this.gamePanel = gamePanel;
        logger.setLevel(Level.WARNING);
        roulette = new Roulette(gamePanel);
        blackjack = new Blackjack(gamePanel);
        pokermon = new Pokermon(gamePanel);
        dices = new Dices(gamePanel);
        rps = new RPS(gamePanel);
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
        } else if (levelNumber == 4 && fourthLevel.checkLevelFinished()) {
            gamePanel.changeGameState(GamePanel.ENDSCREEN);
            return fourthLevel.checkLevelFinished();
        }
        return false;

    }

    public static int getLevelNumber() {
        return levelNumber;
    }

    public void spawnObjectsAndNPC(int level) {
        gamePanel.getObjectsSpawner().spawnObjectsFromSave(level);
        gamePanel.getNpcManager().spawnNPCs(level);
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
                        && !((Door) gamePanel.objects[i]).isOpen()) {
                    ((Door) gamePanel.objects[i]).setOpen(true);
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

    public static int setLevelNumber(int levelNumber) {
        if (levelNumber < 1) {
            LevelManager.levelNumber = 1;
        } else if (levelNumber > 4) {
            LevelManager.levelNumber = 4;
        } else {
            LevelManager.levelNumber = levelNumber;
        }
        return LevelManager.levelNumber;
    }

    public Dices getDices() {
        return dices;
    }

    public RPS getRps() {
        return rps;
    }

    public FirstLevel getFirstLevel() {
        return firstLevel;
    }

    public SecondLevel getSecondLevel() {
        return secondLevel;
    }

    public ThirdLevel getThirdLevel() {
        return thirdLevel;
    }

    public Roulette getRoulette() {
        return roulette;
    }

    public Blackjack getBlackjack() {
        return blackjack;
    }

    public Pokermon getPokermon() {
        return pokermon;
    }
}