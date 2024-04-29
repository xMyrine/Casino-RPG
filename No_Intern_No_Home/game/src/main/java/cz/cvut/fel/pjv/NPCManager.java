package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.*;

public class NPCManager {

    GamePanel gamePanel;
    private int currentNPCLevel = 1;
    private boolean level2NPCSpawned = false;
    private boolean level3NPCSpawned = false;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel, 100, 200);
        gamePanel.entities[1] = new Worker(this.gamePanel, gamePanel.tileSize * 19, gamePanel.tileSize * 12);
        gamePanel.entities[4] = new Shopkeeper(this.gamePanel, gamePanel.tileSize * 1, gamePanel.tileSize * 12);
    }

    private void spawnLevelTwoNPC() {
        gamePanel.entities[2] = new Worker2(this.gamePanel, gamePanel.tileSize * 36, gamePanel.tileSize * 20);
        gamePanel.entities[3] = new Prostitute(this.gamePanel, gamePanel.tileSize * 43, gamePanel.tileSize * 23);
    }

    private void spawnLevelThreeNPC() {
        gamePanel.entities[5] = new PokerManTrainer(this.gamePanel, gamePanel.tileSize * 36, gamePanel.tileSize * 40);
    }

    public void update() {
        if (LevelManager.getLevelNumber() > currentNPCLevel) {
            gamePanel.gameState = GamePanel.GAMESCREEN;
            currentNPCLevel++;
            if (LevelManager.getLevelNumber() == 2 && !level2NPCSpawned) {
                spawnLevelTwoNPC();
                level2NPCSpawned = true;
            }
            if (LevelManager.getLevelNumber() == 3 && !level3NPCSpawned) {
                spawnLevelThreeNPC();
                level3NPCSpawned = true;
            }
            gamePanel.levelManager.levelInProgress = true;

        }
    }
}
