package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

/**
 * FirstLevel is the first level of the game.
 * The player has to finish all slot machines and the minigame to finish the
 * level.
 * 
 * @Author Minh Tu Pham
 */
public class FirstLevel extends Level {
    private int finishedSlotMachineCount;
    private boolean miniGameFinished;
    private int playerSlotMachineCount;

    public FirstLevel(int playerSlotMachineCount, int totalSlotMachineCount, GamePanel gamePanel) {
        super();
        this.finishedSlotMachineCount = totalSlotMachineCount;
        this.playerSlotMachineCount = playerSlotMachineCount;
        this.miniGameFinished = false;
        this.levelFinished = false;
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

    /**
     * Sets the minigameFinished to true and checks if the level is finished.
     * 
     * @param miniGameFinished
     */
    public void setMiniGameFinished(boolean miniGameFinished) {
        this.miniGameFinished = miniGameFinished;
        gamePanel.getLevelManager().checkLevelFinished();
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

    /**
     * Checks if the player has finished all slot machines.
     * 
     * @return true if the player has finished all slot machines
     */
    public boolean checkSlotMachineCount() {
        fetchPlayerSlotMachineCount(gamePanel.getPlayer().getSlotMachineCount());
        if (finishedSlotMachineCount <= playerSlotMachineCount) {
            logger.config("Slot machines finished");
            return true;
        }
        return false;
    }

    /**
     * Checks if the level is finished.
     * If the slot machines are finished and the roulette is finished, the level is
     * finished.
     * 
     * @return true if the level is finished
     */
    @Override
    public boolean checkLevelFinished() {
        if (checkSlotMachineCount() && miniGameFinished) {
            logger.info("Level finished");
            return true;
        }
        return false;
    }

    /**
     * Fetches the player's slot machine count.
     * 
     * @param playerSlotMachineCount
     */
    public void fetchPlayerSlotMachineCount(int playerSlotMachineCount) {
        this.playerSlotMachineCount = playerSlotMachineCount;
    }

}
