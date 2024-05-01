package cz.cvut.fel.pjv;

import java.util.logging.Logger;
import java.util.logging.Level;

import cz.cvut.fel.pjv.entity.Entity;

public class CollisionManager {

    GamePanel gamePanel;
    private Logger logger;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.WARNING);
    }

    /*
     * Check if the entity collides with a tile
     */
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftTileX = entityLeftWorldX / gamePanel.tileSize;
        int entityRightTileX = entityRightWorldX / gamePanel.tileSize;
        int entityTopTileY = entityTopWorldY / gamePanel.tileSize;
        int entityBottomTileY = entityBottomWorldY / gamePanel.tileSize;

        switch (entity.direction) {
            case "up":
                checkTileUp(entity, entityLeftTileX, entityRightTileX, entityTopWorldY);
                break;
            case "down":
                checkTileDown(entity, entityLeftTileX, entityRightTileX, entityBottomWorldY);
                break;
            case "left":
                checkTileLeft(entity, entityLeftTileX, entityTopTileY, entityBottomTileY);
                break;
            case "right":
                checkTileRight(entity, entityRightTileX, entityTopTileY, entityBottomTileY);
                break;
        }
    }

    private void checkTileUp(Entity entity, int entityLeftTileX, int entityRightTileX, int entityTopWorldY) {
        int entityTopTileY = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
        checkCollision(entity, entityLeftTileX, entityRightTileX, entityTopTileY);
    }

    private void checkTileDown(Entity entity, int entityLeftTileX, int entityRightTileX, int entityBottomWorldY) {
        int entityBottomTileY = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
        checkCollision(entity, entityLeftTileX, entityRightTileX, entityBottomTileY);
    }

    private void checkTileLeft(Entity entity, int entityLeftTileX, int entityTopTileY, int entityBottomTileY) {
        entityLeftTileX = (entity.worldX + entity.collisionArea.x - entity.speed) / gamePanel.tileSize;
        checkCollision(entity, entityLeftTileX, entityTopTileY, entityBottomTileY);
    }

    private void checkTileRight(Entity entity, int entityRightTileX, int entityTopTileY, int entityBottomTileY) {
        entityRightTileX = (entity.worldX + entity.collisionArea.x + entity.collisionArea.width + entity.speed)
                / gamePanel.tileSize;
        checkCollision(entity, entityRightTileX, entityTopTileY, entityBottomTileY);
    }

    private void checkCollision(Entity entity, int entityTileX1, int entityTileX2, int entityTileY) {
        int tileNum1 = gamePanel.tileManager.mapTileNum[entityTileX1][entityTileY];
        int tileNum2 = gamePanel.tileManager.mapTileNum[entityTileX2][entityTileY];
        if (gamePanel.tileManager.tiles[tileNum1].isSolid() || gamePanel.tileManager.tiles[tileNum2].isSolid()) {
            entity.collision = true;
        }
    }

    /*
     * Check if the Player collides with an object
     */
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

    /*
     * Check if the player collides with an entity
     */
    public int checkEntityCollision(Entity entity, Entity[] target) {
        int index = 69;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                entity.collisionArea.x = entity.worldX + entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.worldY + entity.collisionAreaDefaultY;

                target[i].collisionArea.x = target[i].worldX
                        + target[i].collisionArea.x;
                target[i].collisionArea.y = target[i].worldY
                        + target[i].collisionArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.collisionArea.y -= entity.speed;
                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
                            entity.collision = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.collisionArea.y += entity.speed;
                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
                            entity.collision = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.collisionArea.x -= entity.speed;
                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
                            entity.collision = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.collisionArea.x += entity.speed;
                        if (entity.collisionArea.intersects(target[i].collisionArea)) {
                            entity.collision = true;
                            index = i;
                        }
                        break;
                }
                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;

                target[i].collisionArea.x = target[i].collisionAreaDefaultX;
                target[i].collisionArea.y = target[i].collisionAreaDefaultY;
            }
        }
        return index;
    }

    /*
     * Check if the entity collides with the player
     */
    public void checkEntityToPlayerCollision(Entity entity) {
        entity.collisionArea.x = entity.worldX + entity.collisionAreaDefaultX;
        entity.collisionArea.y = entity.worldY + entity.collisionAreaDefaultY;

        gamePanel.player.collisionArea.x = gamePanel.player.worldX
                + gamePanel.player.collisionArea.x;
        gamePanel.player.collisionArea.y = gamePanel.player.worldY
                + gamePanel.player.collisionArea.y;

        switch (entity.direction) {
            case "up":
                entity.collisionArea.y -= entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collision = true;
                }
                break;
            case "down":
                entity.collisionArea.y += entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collision = true;
                }
                break;
            case "left":
                entity.collisionArea.x -= entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collision = true;
                }
                break;
            case "right":
                entity.collisionArea.x += entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collision = true;
                }
                break;
        }
        entity.collisionArea.x = entity.collisionAreaDefaultX;
        entity.collisionArea.y = entity.collisionAreaDefaultY;

        gamePanel.player.collisionArea.x = gamePanel.player.collisionAreaDefaultX;
        gamePanel.player.collisionArea.y = gamePanel.player.collisionAreaDefaultY;
    }
}
