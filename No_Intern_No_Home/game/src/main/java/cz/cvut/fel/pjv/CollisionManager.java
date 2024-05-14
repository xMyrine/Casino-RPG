package cz.cvut.fel.pjv;

import java.util.logging.Logger;
import java.util.logging.Level;

import cz.cvut.fel.pjv.entity.Entity;

/**
 * CollisionManager is a class that manages all the collisions in the game.
 * It checks if the entity collides with a tile, object, player or another
 * entity.
 * Most of the methods take advantage of the Rectangle class and its methods to
 * check if two rectangles intersect.
 * 
 * @Author Minh Tu Pham
 */
public class CollisionManager {

    GamePanel gamePanel;
    private Logger logger;

    public CollisionManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.WARNING);
    }

    /**
     * Check if the entity collides with a tile
     * 
     * @param entity - entity to check
     */
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getCollisionArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getCollisionArea().x + entity.getCollisionArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getCollisionArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getCollisionArea().y + entity.getCollisionArea().height;

        int entityLeftTileX = entityLeftWorldX / GamePanel.TILE_SIZE;
        int entityRightTileX = entityRightWorldX / GamePanel.TILE_SIZE;
        int entityTopTileY = entityTopWorldY / GamePanel.TILE_SIZE;
        int entityBottomTileY = entityBottomWorldY / GamePanel.TILE_SIZE;

        switch (entity.getDirection()) {
            case Constants.UP:
                checkTileUp(entity, entityLeftTileX, entityRightTileX, entityTopWorldY);
                break;
            case Constants.DOWN:
                checkTileDown(entity, entityLeftTileX, entityRightTileX, entityBottomWorldY);
                break;
            case Constants.LEFT:
                checkTileLeft(entity, entityTopTileY, entityBottomTileY);
                break;
            case Constants.RIGHT:
                checkTileRight(entity, entityTopTileY, entityBottomTileY);
                break;
            default:
                break;
        }
    }

    /**
     * Check if the entity collides with a tile
     * 
     * @param entity
     * @param entityLeftTileX
     * @param entityRightTileX
     * @param entityTopWorldY
     */
    private void checkTileUp(Entity entity, int entityLeftTileX, int entityRightTileX, int entityTopWorldY) {
        int entityTopTileY = (entityTopWorldY - entity.getSpeed()) / GamePanel.TILE_SIZE;
        checkCollision(entity, entityLeftTileX, entityRightTileX, entityTopTileY);
    }

    /**
     * Check if the entity collides with a tiles
     * 
     * @param entity             - entity to check
     * @param entityLeftTileX    - left tile of the entity
     * @param entityRightTileX   - right tile of the entity
     * @param entityBottomWorldY - bottom of the entity
     */
    private void checkTileDown(Entity entity, int entityLeftTileX, int entityRightTileX, int entityBottomWorldY) {
        int entityBottomTileY = (entityBottomWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;
        checkCollision(entity, entityLeftTileX, entityRightTileX, entityBottomTileY);
    }

    /**
     * Check if the entity collides with a tile
     * 
     * @param entity            - entity to check
     * @param entityTopTileY    - top tile of the entity
     * @param entityBottomTileY - bottom tile of the entity
     */
    private void checkTileLeft(Entity entity, int entityTopTileY, int entityBottomTileY) {
        int newEntityLeftTileX = (entity.getWorldX() + entity.getCollisionArea().x - entity.getSpeed())
                / GamePanel.TILE_SIZE;
        checkCollision(entity, newEntityLeftTileX, entityTopTileY, entityBottomTileY);
    }

    /**
     * Check if the entity collides with a tile
     * 
     * @param entity            - entity to check
     * @param entityTopTileY    - top tile of the entity
     * @param entityBottomTileY - bottom tile of the entity
     */
    private void checkTileRight(Entity entity, int entityTopTileY, int entityBottomTileY) {
        int newEntityRightTileX = (entity.getWorldX() + entity.getCollisionArea().x + entity.getCollisionArea().width
                + entity.getSpeed())
                / GamePanel.TILE_SIZE;
        checkCollision(entity, newEntityRightTileX, entityTopTileY, entityBottomTileY);
    }

    /**
     * Check if the entity collides with a tile
     * 
     * @param entity       - entity to check
     * @param entityTileX1 - left tile of the entity
     * @param entityTileX2 - right tile of the entity
     * @param entityTileY  - top tile of the entity
     */
    private void checkCollision(Entity entity, int entityTileX1, int entityTileX2, int entityTileY) {
        int tileNum1 = gamePanel.tileManager.getMapTileNum(entityTileX1, entityTileY);
        int tileNum2 = gamePanel.tileManager.getMapTileNum(entityTileX2, entityTileY);
        if (gamePanel.tileManager.getTile(tileNum1).isSolid() || gamePanel.tileManager.getTile(tileNum2).isSolid()) {
            entity.setCollision(true);
        }
    }

    /**
     * Check if the Player collides with an object
     * The method works by checking if the player's collision area intersects with
     * the object's collision area. If the player collides with the object, the
     * player's collision is set to true.
     * 
     * @param entity - entity to check
     * @param player - true if the entity is the player
     * @return index of the object
     */
    public int checkObjectCollision(Entity entity, boolean player) {
        int index = 69;

        for (int i = 0; i < gamePanel.objects.length; i++) {
            if (gamePanel.objects[i] != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionAreaDefaultX();
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionAreaDefaultY();

                gamePanel.objects[i].getCollisionArea().x = gamePanel.objects[i].getWorldX()
                        + gamePanel.objects[i].getCollisionArea().x;
                gamePanel.objects[i].getCollisionArea().y = gamePanel.objects[i].getWorldY()
                        + gamePanel.objects[i].getCollisionArea().y;

                switch (entity.getDirection()) {
                    case Constants.UP:
                        entity.getCollisionArea().y -= entity.getSpeed();
                        if (entity.getCollisionArea().intersects(gamePanel.objects[i].getCollisionArea())) {
                            logger.log(Level.FINER, "UP Collision with object {0}", gamePanel.objects[i].getName());
                            if (gamePanel.objects[i].getCollision()) {
                                entity.setCollision(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case Constants.DOWN:
                        entity.getCollisionArea().y += entity.getSpeed();
                        if (entity.getCollisionArea().intersects(gamePanel.objects[i].getCollisionArea())) {
                            logger.log(Level.FINER, "DOWN Collision with object {0}", gamePanel.objects[i].getName());
                            if (gamePanel.objects[i].getCollision()) {
                                entity.setCollision(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case Constants.LEFT:
                        entity.getCollisionArea().x -= entity.getSpeed();
                        if (entity.getCollisionArea().intersects(gamePanel.objects[i].getCollisionArea())) {
                            logger.log(Level.FINER, "LEFT Collision with object {0}", gamePanel.objects[i].getName());
                            if (gamePanel.objects[i].getCollision()) {
                                entity.setCollision(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case Constants.RIGHT:
                        entity.getCollisionArea().x += entity.getSpeed();
                        if (entity.getCollisionArea().intersects(gamePanel.objects[i].getCollisionArea())) {
                            logger.log(Level.FINER, "RIGHT Collision with object {0}", gamePanel.objects[i].getName());
                            if (gamePanel.objects[i].getCollision()) {
                                entity.setCollision(true);
                            }
                            if (player) {
                                index = i;
                            }

                        }
                        break;
                    default:
                        break;
                }
                entity.getCollisionArea().x = entity.getCollisionAreaDefaultX();
                entity.getCollisionArea().y = entity.getCollisionAreaDefaultY();

                gamePanel.objects[i].getCollisionArea().x = gamePanel.objects[i].getCollisionAreaDefaultX();
                gamePanel.objects[i].getCollisionArea().y = gamePanel.objects[i].getCollisionAreaDefaultY();
            }
        }

        return index;
    }

    /**
     * Check if the player collides with an entity
     * The method works by checking if the player's collision area intersects with
     * the entity's collision area. If the player collides with the entity, the
     * player's collision is set to true.
     * 
     * @param entity - entity to check
     * @param target - array of entities to check
     * @return index of the entity
     */
    public int checkEntityCollision(Entity entity, Entity[] target) {
        int index = 69;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionAreaDefaultX();
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionAreaDefaultY();

                target[i].getCollisionArea().x = target[i].getWorldX()
                        + target[i].getCollisionArea().x;
                target[i].getCollisionArea().y = target[i].getWorldY()
                        + target[i].getCollisionArea().y;

                switch (entity.getDirection()) {
                    case Constants.UP:
                        entity.getCollisionArea().y -= entity.getSpeed();
                        if (entity.getCollisionArea().intersects(target[i].getCollisionArea())) {
                            entity.setCollision(true);
                            index = i;
                        }
                        break;
                    case Constants.DOWN:
                        entity.getCollisionArea().y += entity.getSpeed();
                        if (entity.getCollisionArea().intersects(target[i].getCollisionArea())) {
                            entity.setCollision(true);
                            index = i;
                        }
                        break;
                    case Constants.LEFT:
                        entity.getCollisionArea().x -= entity.getSpeed();
                        if (entity.getCollisionArea().intersects(target[i].getCollisionArea())) {
                            entity.setCollision(true);
                            index = i;
                        }
                        break;
                    case Constants.RIGHT:
                        entity.getCollisionArea().x += entity.getSpeed();
                        if (entity.getCollisionArea().intersects(target[i].getCollisionArea())) {
                            entity.setCollision(true);
                            index = i;
                        }
                        break;
                    default:
                        break;
                }
                entity.getCollisionArea().x = entity.getCollisionAreaDefaultX();
                entity.getCollisionArea().y = entity.getCollisionAreaDefaultY();

                target[i].getCollisionArea().x = target[i].getCollisionAreaDefaultX();
                target[i].getCollisionArea().y = target[i].getCollisionAreaDefaultY();
            }
        }
        return index;
    }

    /**
     * Check if the entity collides with the player
     * The method works by checking if the entity's collision area intersects with
     * the player's collision area. If the entity collides with the player, the
     * entity's collision is set to true.
     * 
     * @param entity - entity to check
     */
    public void checkEntityToPlayerCollision(Entity entity) {
        entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionAreaDefaultX();
        entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionAreaDefaultY();

        gamePanel.player.getCollisionArea().x = gamePanel.player.getWorldX()
                + gamePanel.player.getCollisionArea().x;
        gamePanel.player.getCollisionArea().y = gamePanel.player.getWorldY()
                + gamePanel.player.getCollisionArea().y;

        switch (entity.getDirection()) {
            case Constants.UP:
                entity.getCollisionArea().y -= entity.getSpeed();
                if (entity.getCollisionArea().intersects(gamePanel.player.getCollisionArea())) {
                    entity.setCollision(true);
                }
                break;
            case Constants.DOWN:
                entity.getCollisionArea().y += entity.getSpeed();
                if (entity.getCollisionArea().intersects(gamePanel.player.getCollisionArea())) {
                    entity.setCollision(true);
                }
                break;
            case Constants.LEFT:
                entity.getCollisionArea().x -= entity.getSpeed();
                if (entity.getCollisionArea().intersects(gamePanel.player.getCollisionArea())) {
                    entity.setCollision(true);
                }
                break;
            case Constants.RIGHT:
                entity.getCollisionArea().x += entity.getSpeed();
                if (entity.getCollisionArea().intersects(gamePanel.player.getCollisionArea())) {
                    entity.setCollision(true);
                }
                break;
            default:
                break;
        }
        entity.getCollisionArea().x = entity.getCollisionAreaDefaultX();
        entity.getCollisionArea().y = entity.getCollisionAreaDefaultY();

        gamePanel.player.getCollisionArea().x = gamePanel.player.getCollisionAreaDefaultX();
        gamePanel.player.getCollisionArea().y = gamePanel.player.getCollisionAreaDefaultY();
    }
}
