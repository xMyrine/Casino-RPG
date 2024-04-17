package cz.cvut.fel.pjv.levels;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.GamePanel;

public class FirstLevel extends Level {
    private int finishedSlotMachineCount;
    private boolean miniGameFinished;
    private int playerSlotMachineCount;
    private GamePanel gamePanel;

    public FirstLevel(int playerSlotMachineCount, int totalSlotMachineCount, GamePanel gamePanel) {
        this.finishedSlotMachineCount = 2;
        this.playerSlotMachineCount = playerSlotMachineCount;
        this.miniGameFinished = true; // !TO BE CHANGED BECASE I DONT HAVE MINIGAME YET
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
        if (finishedSlotMachineCount == playerSlotMachineCount) {
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
