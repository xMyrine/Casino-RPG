package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.IntroNPC;

public class NPCManager {

    private GamePanel gamePanel;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel, gamePanel.tileSize * 8, gamePanel.tileSize * 8);
    }
}
