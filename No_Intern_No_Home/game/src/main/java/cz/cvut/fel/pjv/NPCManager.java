package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.IntroNPC;

public class NPCManager {

    GamePanel gamePanel;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel);
        gamePanel.entities[0].worldX = 100;
        gamePanel.entities[0].worldY = 200;
    }
}
