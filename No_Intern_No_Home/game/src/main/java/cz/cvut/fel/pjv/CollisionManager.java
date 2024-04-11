package cz.cvut.fel.pjv;

import java.util.logging.Logger;

import cz.cvut.fel.pjv.entity.Entity;

public class CollisionManager {

    GamePanel gamePanel;
    private Logger logger;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        logger = Logger.getLogger(this.getClass().getName());
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

    public int checkObjectCollision(Entity entity, boolean player) {
        int index = 69;

        for (int i = 0; i < gamePanel.objects.length; i++) {
            if (gamePanel.objects[i] != null) {
                entity.collisionArea.x = entity.worldX + entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.worldY + entity.collisionAreaDefaultY;

                gamePanel.objects[i].collisionArea.x = gamePanel.objects[i].worldX
                        + gamePanel.objects[i].collisionArea.x;
                gamePanel.objects[i].collisionArea.y = gamePanel.objects[i].worldY
                        + gamePanel.objects[i].collisionArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.collisionArea.y -= entity.speed;
                        if (entity.collisionArea.intersects(gamePanel.objects[i].collisionArea)) {
                            logger.info("UP Collision with object " + gamePanel.objects[i].name);
                            if (gamePanel.objects[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.collisionArea.y += entity.speed;
                        if (entity.collisionArea.intersects(gamePanel.objects[i].collisionArea)) {
                            logger.finer("DOWN Collision with object " + gamePanel.objects[i].name);
                            if (gamePanel.objects[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.collisionArea.x -= entity.speed;
                        if (entity.collisionArea.intersects(gamePanel.objects[i].collisionArea)) {
                            logger.finer("LEFT Collision with object " + gamePanel.objects[i].name);
                            if (gamePanel.objects[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.collisionArea.x += entity.speed;
                        if (entity.collisionArea.intersects(gamePanel.objects[i].collisionArea)) {
                            logger.finer("RIGHT Collision with object " + gamePanel.objects[i].name);
                            if (gamePanel.objects[i].collision) {
                                entity.collision = true;
                            }
                            if (player) {
                                index = i;
                            }

                        }
                        break;
                }
                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;

                gamePanel.objects[i].collisionArea.x = gamePanel.objects[i].collisionAreaDefaultX;
                gamePanel.objects[i].collisionArea.y = gamePanel.objects[i].collisionAreaDefaultY;
            }
        }

        return index;

    }
}
