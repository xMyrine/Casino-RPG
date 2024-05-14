package cz.cvut.fel.pjv;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.objects.*;
import cz.cvut.fel.pjv.objects.alcohol.*;

public class ObjectsSpawner {

    private GamePanel gamePanel;
    private int currentObjectLevelSpawned = 1;
    private Logger logger = Logger.getLogger(ObjectsSpawner.class.getName());

    public ObjectsSpawner(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Spawns objects in the game
     */
    public void spawnObjects() {
        gamePanel.objects[0] = new Chip(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 2);
        gamePanel.objects[1] = new Chip(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 3);
        gamePanel.objects[2] = new Chip(GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 3);
        gamePanel.objects[3] = new Chip(GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 2);
        gamePanel.objects[4] = new SlotMachine(GamePanel.TILE_SIZE * 17, GamePanel.TILE_SIZE * 3);
        gamePanel.objects[5] = new SlotMachine(GamePanel.TILE_SIZE * 19, GamePanel.TILE_SIZE * 3);
        gamePanel.objects[11] = new SlotMachine(GamePanel.TILE_SIZE * 21,
                GamePanel.TILE_SIZE * 6);
        gamePanel.objects[13] = new SlotMachine(GamePanel.TILE_SIZE * 19,
                GamePanel.TILE_SIZE * 9);
        gamePanel.objects[15] = new SlotMachine(GamePanel.TILE_SIZE * 23,
                GamePanel.TILE_SIZE * 9);
        gamePanel.objects[6] = new Chest(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 23);

        gamePanel.objects[7] = new Chest(GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 23);

        gamePanel.objects[8] = new Chest(GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 23);

        gamePanel.objects[9] = new Chest(GamePanel.TILE_SIZE * 8, GamePanel.TILE_SIZE * 23);

        gamePanel.objects[28] = new Beer(GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 5);

        gamePanel.objects[29] = new Beer(GamePanel.TILE_SIZE * 6, GamePanel.TILE_SIZE * 5);

        gamePanel.objects[30] = new Door(GamePanel.TILE_SIZE * 24, GamePanel.TILE_SIZE * 13);

        gamePanel.objects[31] = new Door(GamePanel.TILE_SIZE * 24, GamePanel.TILE_SIZE * 14);
    }

    /**
     * Spawns objects for level 2
     */
    private void spawnLevelTwoObjects() {
        logger.fine("Spawning level 2 objects");
        gamePanel.objects[0] = new Door(GamePanel.TILE_SIZE * 37, GamePanel.TILE_SIZE * 24);
        gamePanel.objects[1] = new Door(GamePanel.TILE_SIZE * 36, GamePanel.TILE_SIZE * 24);
        gamePanel.objects[10] = new Chest(GamePanel.TILE_SIZE * 47, GamePanel.TILE_SIZE * 1);
        gamePanel.objects[28] = new DomPerignon(GamePanel.TILE_SIZE * 46, GamePanel.TILE_SIZE * 3);
        gamePanel.objects[29] = new Vodka(GamePanel.TILE_SIZE * 46, GamePanel.TILE_SIZE * 4);
        gamePanel.objects[27] = new Beer(GamePanel.TILE_SIZE * 46, GamePanel.TILE_SIZE * 5);
        gamePanel.levelManager.setLevelInProgress(true);
    }

    /**
     * Spawns objects for level 3
     */
    public void spawnLevelThreeObjects() {
        logger.fine("Spawning level 3 objects");
        gamePanel.objects[32] = new Door(GamePanel.TILE_SIZE * 24, GamePanel.TILE_SIZE * 35);
        gamePanel.objects[33] = new Door(GamePanel.TILE_SIZE * 24, GamePanel.TILE_SIZE * 36);
        gamePanel.objects[21] = new Chip(GamePanel.TILE_SIZE * 24, GamePanel.TILE_SIZE * 38);
        gamePanel.objects[22] = new Chip(GamePanel.TILE_SIZE * 25, GamePanel.TILE_SIZE * 38);
        gamePanel.objects[23] = new Chip(GamePanel.TILE_SIZE * 26, GamePanel.TILE_SIZE * 38);
        gamePanel.objects[24] = new DomPerignon(GamePanel.TILE_SIZE * 24, GamePanel.TILE_SIZE * 39);
        gamePanel.objects[25] = new Vodka(GamePanel.TILE_SIZE * 25, GamePanel.TILE_SIZE * 39);
        gamePanel.levelManager.setLevelInProgress(true);
    }

    public int getCurrentObjectLevelSpawned() {
        return currentObjectLevelSpawned;
    }

    /**
     * Updates the game
     * This method clears the items laying on the ground when the level is changed
     * and spawns new objects based on the level
     * Objects that are not solid are removed from the game, while the solid objects
     * are kept
     */
    public void update() {
        if (!gamePanel.levelManager.isLevelInProgress() && LevelManager.getLevelNumber() > currentObjectLevelSpawned) {
            for (int i = 0; i < gamePanel.objects.length; i++) {
                if (gamePanel.objects[i] != null && !gamePanel.objects[i].isSolid()
                        && !(gamePanel.objects[i] instanceof Door)) {
                    gamePanel.objects[i] = null;
                }

            }
            if (LevelManager.getLevelNumber() == 2 && !gamePanel.levelManager.isLevelInProgress()) {
                spawnLevelTwoObjects();
                logger.warning("Level 2 objects spawned");
            }
            if (LevelManager.getLevelNumber() == 3 && !gamePanel.levelManager.isLevelInProgress()) {
                spawnLevelThreeObjects();
                logger.warning("Level 3 objects spawned");
            }
            currentObjectLevelSpawned++;
            gamePanel.levelManager.setLevelInProgress(true);

        }
    }

}
