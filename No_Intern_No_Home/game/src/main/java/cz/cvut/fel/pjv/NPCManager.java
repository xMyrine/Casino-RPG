package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.IntroNPC;
import cz.cvut.fel.pjv.entity.Worker;

public class NPCManager {

    GamePanel gamePanel;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel, 100, 200);
        gamePanel.entities[1] = new Worker(this.gamePanel, gamePanel.tileSize * 23, gamePanel.tileSize * 13);
    }
}
