package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.IntroNPC;
import cz.cvut.fel.pjv.entity.Prostitute;
import cz.cvut.fel.pjv.entity.Worker;
import cz.cvut.fel.pjv.entity.*;

public class NPCManager {

    GamePanel gamePanel;
    private int currentNPCLevel = 1;
    private boolean level2NPCSpawned = false;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel, 100, 200);
        gamePanel.entities[1] = new Worker(this.gamePanel, gamePanel.tileSize * 23, gamePanel.tileSize * 12);
    }

    private void spawnLevelTwoNPC() {
        gamePanel.entities[2] = new Worker2(this.gamePanel, gamePanel.tileSize * 37, gamePanel.tileSize * 23);
        gamePanel.entities[3] = new Prostitute(this.gamePanel, gamePanel.tileSize * 43, gamePanel.tileSize * 23);
    }

    public void update() {
        if (LevelManager.getLevelNumber() > currentNPCLevel) {
            gamePanel.gameState = GamePanel.GAMESCREEN;
            currentNPCLevel++;
            if (LevelManager.getLevelNumber() == 2 && !level2NPCSpawned) {
                spawnLevelTwoNPC();
                level2NPCSpawned = true;
            }
            gamePanel.levelManager.levelInProgress = true;

        }
    }
}
