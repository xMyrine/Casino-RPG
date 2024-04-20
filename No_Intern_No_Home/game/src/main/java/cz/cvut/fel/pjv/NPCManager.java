package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.IntroNPC;
import cz.cvut.fel.pjv.entity.Prostitute;
import cz.cvut.fel.pjv.entity.Worker;

public class NPCManager {

    GamePanel gamePanel;
    private int currentNPCLevel = 1;
    private boolean level2NPCSpawned = false;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel, 100, 200);
        gamePanel.entities[1] = new Worker(this.gamePanel, gamePanel.tileSize * 23, gamePanel.tileSize * 13);
    }

    private void spawnLevelTwoNPC() {
        gamePanel.entities[1] = new Worker(this.gamePanel, gamePanel.tileSize * 37, gamePanel.tileSize * 23);
        gamePanel.entities[2] = new Prostitute(this.gamePanel, gamePanel.tileSize * 36, gamePanel.tileSize * 23);
    }

    public void update() {
        if (LevelManager.getLevelNumber() > currentNPCLevel) {
            for (int i = 0; i < gamePanel.entities.length; i++) {
                if (gamePanel.entities[i] != null && !(gamePanel.entities[i] instanceof IntroNPC)) {
                    gamePanel.entities[i] = null;
                }
            }
            currentNPCLevel++;
            if (LevelManager.getLevelNumber() == 2 && !level2NPCSpawned) {
                spawnLevelTwoNPC();
                System.out.println("Level 2 NPC spawned");
                level2NPCSpawned = true;
            }
            gamePanel.levelManager.levelInProgress = true;

        }
    }
}
