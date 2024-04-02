package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.Entity;

public class CollisionManager {

    GamePanel gamePanel;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftTileX = entityLeftWorldX / gamePanel.tileSize;
        int entityRightTileX = entityRightWorldX / gamePanel.tileSize;
        int entityTopTileY = entityTopWorldY / gamePanel.tileSize;
        int entityBottomTileY = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1;
        int tileNum2;

        if (entity.direction.equals("up")) {
            entityTopTileY = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftTileX][entityTopTileY];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightTileX][entityTopTileY];
            if (gamePanel.tileManager.tiles[tileNum1].isSolid() || gamePanel.tileManager.tiles[tileNum2].isSolid()) {
                entity.collision = true;
            }
        } else if (entity.direction.equals("down")) {
            entityBottomTileY = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftTileX][entityBottomTileY];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightTileX][entityBottomTileY];
            if (gamePanel.tileManager.tiles[tileNum1].isSolid() || gamePanel.tileManager.tiles[tileNum2].isSolid()) {
                entity.collision = true;
            }
        } else if (entity.direction.equals("left")) {
            entityLeftTileX = (entityLeftWorldX - entity.speed) / gamePanel.tileSize; // subtract speed
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftTileX][entityTopTileY];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftTileX][entityBottomTileY];
            if (gamePanel.tileManager.tiles[tileNum1].isSolid() || gamePanel.tileManager.tiles[tileNum2].isSolid()) {
                entity.collision = true;
            }
        } else {
            entityRightTileX = (entityRightWorldX + entity.speed) / gamePanel.tileSize; // add speed
            tileNum1 = gamePanel.tileManager.mapTileNum[entityRightTileX][entityTopTileY];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightTileX][entityBottomTileY];
            if (gamePanel.tileManager.tiles[tileNum1].isSolid() || gamePanel.tileManager.tiles[tileNum2].isSolid()) {
                entity.collision = true;
            }
        }
    }
}
