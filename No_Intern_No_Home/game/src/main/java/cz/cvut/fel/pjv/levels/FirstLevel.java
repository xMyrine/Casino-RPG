package cz.cvut.fel.pjv.levels;

public class FirstLevel {
    private int FinishedSlotMachineCount;
    private boolean miniGameFinished;
    private boolean levelFinished;
    private int playerSlotMachineCount;

    public FirstLevel(int playerSlotMachineCount, int totalSlotMachineCount) {
        this.FinishedSlotMachineCount = playerSlotMachineCount;
        this.playerSlotMachineCount = totalSlotMachineCount;
        this.miniGameFinished = true; // !TO BE CHANGED BECASE I DONT HAVE MINIGAME YET
        this.levelFinished = false;
    }

    public int getPlayerSlotMachineCount() {
        return playerSlotMachineCount;
    }

    public void setPlayerSlotMachineCount(int playerSlotMachineCount) {
        this.playerSlotMachineCount = playerSlotMachineCount;
    }

    public void setFinishedSlotMachineCount(int finishedSlotMachineCount) {
        FinishedSlotMachineCount = finishedSlotMachineCount;
    }

    public void setMiniGameFinished(boolean miniGameFinished) {
        this.miniGameFinished = miniGameFinished;
    }

    public void setLevelFinished(boolean levelFinished) {
        this.levelFinished = levelFinished;
    }

    public int getFinishedSlotMachineCount() {
        return FinishedSlotMachineCount;
    }

    public boolean isMiniGameFinished() {
        return miniGameFinished;
    }

    public boolean isLevelFinished() {
        return levelFinished;
    }

    public boolean checkSlotMachineCount() {
        if (FinishedSlotMachineCount == playerSlotMachineCount) {
            System.out.println("Slot machines finished");
            return true;
        }
        return false;
    }

    public boolean checkLevelFinished() {
        if (checkSlotMachineCount() && miniGameFinished) {
            return true;
        }
        return false;
    }

}
