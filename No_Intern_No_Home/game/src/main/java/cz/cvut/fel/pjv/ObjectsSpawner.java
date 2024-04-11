package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.objects.*;
import cz.cvut.fel.pjv.objects.Alcohol.*;

public class ObjectsSpawner {

    GamePanel gamePanel;

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

        gamePanel.objects[4] = new SlotMachine(gamePanel.tileSize * 4, gamePanel.tileSize * 3);

        gamePanel.objects[5] = new SlotMachine(gamePanel.tileSize * 4, gamePanel.tileSize * 2);

        gamePanel.objects[8] = new Beer(gamePanel.tileSize * 5, gamePanel.tileSize * 5);

        gamePanel.objects[9] = new Beer(gamePanel.tileSize * 6, gamePanel.tileSize * 5);

        gamePanel.objects[10] = new Beer(gamePanel.tileSize * 7, gamePanel.tileSize * 5);

        gamePanel.objects[15] = new Door(gamePanel.tileSize * 23, gamePanel.tileSize * 13);

        gamePanel.objects[16] = new Door(gamePanel.tileSize * 23, gamePanel.tileSize * 14);
    }

    public void update() {
        if (!gamePanel.levelManager.levelInProgress && gamePanel.levelManager.checkLevelFirstFinished()) {
            for (int i = 0; i < gamePanel.objects.length; i++) {
                if (gamePanel.objects[i] != null && !gamePanel.objects[i].isSolid()
                        && !(gamePanel.objects[i] instanceof Door)) {
                    gamePanel.objects[i] = null;
                }
            }
            gamePanel.levelManager.levelInProgress = true;

        }
    }

}
