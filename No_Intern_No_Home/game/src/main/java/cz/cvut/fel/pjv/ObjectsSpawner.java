package cz.cvut.fel.pjv;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.objects.*;
import cz.cvut.fel.pjv.objects.Alcohol.*;

public class ObjectsSpawner {

    private GamePanel gamePanel;
    private int currentObjectLevelSpawned = 1;
    private Logger logger = Logger.getLogger(ObjectsSpawner.class.getName());

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

        gamePanel.objects[10] = new SlotMachine(gamePanel.tileSize * 21,
                gamePanel.tileSize * 6);
        gamePanel.objects[13] = new SlotMachine(gamePanel.tileSize * 19,
                gamePanel.tileSize * 9);

        gamePanel.objects[15] = new SlotMachine(gamePanel.tileSize * 23,
                gamePanel.tileSize * 9);

        gamePanel.objects[6] = new Chest(gamePanel.tileSize * 1, gamePanel.tileSize * 23);

        gamePanel.objects[7] = new Chest(gamePanel.tileSize * 9, gamePanel.tileSize * 23);

        gamePanel.objects[8] = new Chest(gamePanel.tileSize * 2, gamePanel.tileSize * 23);

        gamePanel.objects[9] = new Chest(gamePanel.tileSize * 8, gamePanel.tileSize * 23);

        gamePanel.objects[28] = new Beer(gamePanel.tileSize * 5, gamePanel.tileSize * 5);

        gamePanel.objects[29] = new Beer(gamePanel.tileSize * 6, gamePanel.tileSize * 5);

        gamePanel.objects[30] = new Door(gamePanel.tileSize * 24, gamePanel.tileSize * 13);

        gamePanel.objects[31] = new Door(gamePanel.tileSize * 24, gamePanel.tileSize * 14);
    }

    private void spawnLevelTwoObjects() {
        gamePanel.objects[0] = new Door(gamePanel.tileSize * 37, gamePanel.tileSize * 24);

        gamePanel.objects[1] = new Door(gamePanel.tileSize * 36, gamePanel.tileSize * 24);

        gamePanel.objects[10] = new Chest(gamePanel.tileSize * 47, gamePanel.tileSize * 1);

        gamePanel.objects[28] = new DomPerignon(gamePanel.tileSize * 46, gamePanel.tileSize * 3);

        gamePanel.objects[29] = new Vodka(gamePanel.tileSize * 46, gamePanel.tileSize * 4);

        gamePanel.objects[27] = new Beer(gamePanel.tileSize * 46, gamePanel.tileSize * 5);

    }

    private void spawnLevelThreeObjects() {
        gamePanel.objects[2] = new Door(gamePanel.tileSize * 24, gamePanel.tileSize * 35);
        gamePanel.objects[3] = new Door(gamePanel.tileSize * 24, gamePanel.tileSize * 36);
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
            if (LevelManager.getLevelNumber() == 2 && !gamePanel.levelManager.levelInProgress) {
                spawnLevelTwoObjects();
                logger.warning("Level 2 objects spawned");
            }
            if (LevelManager.getLevelNumber() == 3 && !gamePanel.levelManager.levelInProgress) {
                spawnLevelThreeObjects();
                logger.warning("Level 3 objects spawned");
            }
            currentObjectLevelSpawned++;
            gamePanel.levelManager.levelInProgress = true;

        }
    }

}
