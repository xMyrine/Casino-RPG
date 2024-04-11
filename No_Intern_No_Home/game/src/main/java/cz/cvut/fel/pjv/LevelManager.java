package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.levels.*;
import cz.cvut.fel.pjv.objects.*;

public class LevelManager {
    private FirstLevel firstLevel;

    public LevelManager(GamePanel gamePanel) {
        this.firstLevel = new FirstLevel(gamePanel.player.getSlotMachineCount(),
                gamePanel.countObjectsByClass(SlotMachine.class));
    }

    public boolean checkLevelFirstFinished() {
        return firstLevel.checkLevelFinished();
    }

}
