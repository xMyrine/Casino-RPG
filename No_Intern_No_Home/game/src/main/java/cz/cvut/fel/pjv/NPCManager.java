package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.*;

/**
 * NPCManager is a class that manages the NPCs in the game.
 * 
 * @Author Minh Tu Pham
 */
public class NPCManager {

    GamePanel gamePanel;
    private int currentNPCLevel = 1;
    private boolean level2NPCSpawned = false;
    private boolean level3NPCSpawned = false;

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Spawns the NPC in the game.
     */
    public void spawnNPC() {
        gamePanel.entities[0] = new IntroNPC(this.gamePanel, 100, 200);
        gamePanel.entities[1] = new Worker(this.gamePanel, GamePanel.TILE_SIZE * 19, GamePanel.TILE_SIZE * 12);
        gamePanel.entities[4] = new Shopkeeper(this.gamePanel, GamePanel.TILE_SIZE * 22, GamePanel.TILE_SIZE * 22);
        gamePanel.entities[8] = new FinalBoss(this.gamePanel, GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 40);
        gamePanel.entities[6] = new Craftsman(GamePanel.TILE_SIZE * 20, GamePanel.TILE_SIZE * 22);
    }

    /**
     * Spawns the NPC for level 2.
     */
    private void spawnLevelTwoNPC() {
        gamePanel.entities[2] = new Worker2(this.gamePanel, GamePanel.TILE_SIZE * 36, GamePanel.TILE_SIZE * 20);
        gamePanel.entities[3] = new Prostitute(GamePanel.TILE_SIZE * 43, GamePanel.TILE_SIZE * 23);
    }

    /**
     * Spawns the NPC for level 3.
     */
    private void spawnLevelThreeNPC() {
        gamePanel.entities[5] = new PokerManTrainer(GamePanel.TILE_SIZE * 37, GamePanel.TILE_SIZE * 36);
        gamePanel.entities[7] = new Worker3(this.gamePanel, GamePanel.TILE_SIZE * 40,
                GamePanel.TILE_SIZE * 37);
    }

    /**
     * Spawns the NPCs in the game depending on the level.
     * 
     * @param level the level of the game
     */
    protected void spawnNPCs(int level) {
        if (level >= 2) {
            spawnLevelTwoNPC();
        }
        if (level >= 3) {
            spawnLevelThreeNPC();
        }
    }

    /**
     * Updates the NPC in the game depending on the level.
     */
    public void update() {
        if (LevelManager.getLevelNumber() > currentNPCLevel) {
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
            currentNPCLevel++;
            if (LevelManager.getLevelNumber() == 2 && !level2NPCSpawned) {
                spawnLevelTwoNPC();
                level2NPCSpawned = true;
            }
            if (LevelManager.getLevelNumber() == 3 && !level3NPCSpawned) {
                spawnLevelThreeNPC();
                level3NPCSpawned = true;
            }
            gamePanel.levelManager.setLevelInProgress(true);

        }
    }
}
