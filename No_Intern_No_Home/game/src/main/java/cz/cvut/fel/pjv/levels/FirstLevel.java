package cz.cvut.fel.pjv.levels;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.minigames.Roulette;

public class FirstLevel extends Level {
    private int finishedSlotMachineCount;
    private boolean miniGameFinished;
    private int playerSlotMachineCount;
    private GamePanel gamePanel;
    public Roulette roulette;

    public FirstLevel(int playerSlotMachineCount, int totalSlotMachineCount, GamePanel gamePanel) {
        this.finishedSlotMachineCount = totalSlotMachineCount;
        this.playerSlotMachineCount = playerSlotMachineCount;
        this.miniGameFinished = false;
        this.levelFinished = false;
        logger = Logger.getLogger(FirstLevel.class.getName());
        this.gamePanel = gamePanel;

    }

    public int getPlayerSlotMachineCount() {
        return playerSlotMachineCount;
    }

    public void setPlayerSlotMachineCount(int playerSlotMachineCount) {
        this.playerSlotMachineCount = playerSlotMachineCount;
    }

    public void setFinishedSlotMachineCount(int count) {
        this.finishedSlotMachineCount = count;
    }

    public void setMiniGameFinished(boolean miniGameFinished) {
        this.miniGameFinished = miniGameFinished;
        gamePanel.levelManager.checkLevelFinished();
    }

    @Override
    public void setLevelFinished(boolean levelFinished) {
        this.levelFinished = levelFinished;
    }

    public int getFinishedSlotMachineCount() {
        return finishedSlotMachineCount;
    }

    public boolean isMiniGameFinished() {
        return miniGameFinished;
    }

    public boolean isLevelFinished() {
        return levelFinished;
    }

    public boolean checkSlotMachineCount() {
        fetchPlayerSlotMachineCount(gamePanel.player.getSlotMachineCount());
        System.out.println("Player slot machine count: " + playerSlotMachineCount);
        if (finishedSlotMachineCount <= playerSlotMachineCount) {
            logger.config("Slot machines finished");
            return true;
        }
        return false;
    }

    @Override
    public boolean checkLevelFinished() {
        if (checkSlotMachineCount() && miniGameFinished) {
            logger.info("Level finished");
            return true;
        }
        return false;
    }

    public void fetchPlayerSlotMachineCount(int playerSlotMachineCount) {
        this.playerSlotMachineCount = playerSlotMachineCount;
    }

}
