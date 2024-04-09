package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.objects.*;

public class LevelManager {
    private FirstLevel firstLevel;
    private boolean firstLevelFinished;

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(),
                gamePanel.countObjectsByClass(SlotMachine.class));
        this.firstLevelFinished = false;
    }

    public void checkLevelFirstFinished() {
        if (firstLevel.checkLevelFinished()) {
            firstLevelFinished = true;
        }
    }

    public boolean isFirstLevelFinished() {
        return firstLevelFinished;
    }

}
