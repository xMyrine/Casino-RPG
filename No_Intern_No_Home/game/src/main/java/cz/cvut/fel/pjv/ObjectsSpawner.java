package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.objects.*;
import cz.cvut.fel.pjv.objects.alcohol.*;

public class ObjectsSpawner {

    public GamePanel gamePanel;
    private int currentObjectLevelSpawned = 1;

    public ObjectsSpawner(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /*
     * Spawns objects in the game
     */
    public void spawnObjects() {
        gamePanel.objects[0] = new Chip(gamePanel.tileSize * 1, gamePanel.tileSize * 2);
        gamePanel.objects[1] = new Chip(gamePanel.tileSize, gamePanel.tileSize * 3);
        gamePanel.objects[2] = new Chip(gamePanel.tileSize * 2, gamePanel.tileSize * 3);
        gamePanel.objects[3] = new Chip(gamePanel.tileSize * 2, gamePanel.tileSize * 2);

        gamePanel.objects[4] = new SlotMachine(gamePanel.tileSize * 17, gamePanel.tileSize * 3);

        gamePanel.objects[5] = new SlotMachine(gamePanel.tileSize * 19, gamePanel.tileSize * 3);

        // gamePanel.objects[6] = new SlotMachine(gamePanel.tileSize * 21,
        // gamePanel.tileSize * 3);
        //
        // gamePanel.objects[7] = new SlotMachine(gamePanel.tileSize * 23,
        // gamePanel.tileSize * 3);
        //
        // gamePanel.objects[8] = new SlotMachine(gamePanel.tileSize * 17,
        // gamePanel.tileSize * 6);
        //
        // gamePanel.objects[9] = new SlotMachine(gamePanel.tileSize * 19,
        // gamePanel.tileSize * 6);
        //
        // gamePanel.objects[10] = new SlotMachine(gamePanel.tileSize * 21,
        // gamePanel.tileSize * 6);
        //
        // gamePanel.objects[11] = new SlotMachine(gamePanel.tileSize * 23,
        // gamePanel.tileSize * 6);
        //
        // gamePanel.objects[12] = new SlotMachine(gamePanel.tileSize * 17,
        // gamePanel.tileSize * 9);
        //
        // gamePanel.objects[13] = new SlotMachine(gamePanel.tileSize * 19,
        // gamePanel.tileSize * 9);
        //
        // gamePanel.objects[14] = new SlotMachine(gamePanel.tileSize * 21,
        // gamePanel.tileSize * 9);
        //
        // gamePanel.objects[15] = new SlotMachine(gamePanel.tileSize * 23,
        // gamePanel.tileSize * 9);

        gamePanel.objects[28] = new Beer(gamePanel.tileSize * 5, gamePanel.tileSize * 5);

        gamePanel.objects[29] = new Beer(gamePanel.tileSize * 6, gamePanel.tileSize * 5);

        gamePanel.objects[30] = new Door(gamePanel.tileSize * 24, gamePanel.tileSize * 13);

        gamePanel.objects[31] = new Door(gamePanel.tileSize * 24, gamePanel.tileSize * 14);
    }

    private void spawnLevelTwoObjects() {
        gamePanel.objects[1] = new Door(gamePanel.tileSize * 37, gamePanel.tileSize * 24);

        gamePanel.objects[28] = new Vodka(gamePanel.tileSize * 45, gamePanel.tileSize * 3);

        gamePanel.objects[29] = new Vodka(gamePanel.tileSize * 45, gamePanel.tileSize * 4);

        gamePanel.objects[27] = new Beer(gamePanel.tileSize * 45, gamePanel.tileSize * 5);

    }

    public int getCurrentObjectLevelSpawned() {
        return currentObjectLevelSpawned;
    }

    /*
     * Updates the game
     */
    public void update() {
        if (!gamePanel.levelManager.levelInProgress && LevelManager.getLevelNumber() > currentObjectLevelSpawned) {
            for (int i = 0; i < gamePanel.objects.length; i++) {
                if (gamePanel.objects[i] != null && !gamePanel.objects[i].isSolid()
                        && !(gamePanel.objects[i] instanceof Door)) {
                    gamePanel.objects[i] = null;
                }

            }
            currentObjectLevelSpawned++;
            if (LevelManager.getLevelNumber() == 2 && !gamePanel.levelManager.levelInProgress) {
                spawnLevelTwoObjects();
            }
            gamePanel.levelManager.levelInProgress = true;

        }
    }

}
