package cz.cvut.fel.pjv;

import java.util.logging.Logger;
import java.util.logging.Level;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.objects.GameObject;

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
        logger.setLevel(Level.FINEST);
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
                updateCollisionAreas(entity, gamePanel.objects[i]);
                index = checkCollisionInDirection(entity, gamePanel.objects[i], player, i, index);
                resetCollisionAreas(entity, gamePanel.objects[i]);
            }
        }

        return index;
    }

    /**
     * Updates the x and y coordinates of the collision areas for the given entity
     * and object.
     *
     * @param entity The entity whose collision area coordinates are to be updated.
     * @param object The object whose collision area coordinates are to be updated.
     */
    private void updateCollisionAreas(Entity entity, GameObject object) {
        entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionAreaDefaultX();
        entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionAreaDefaultY();

        object.getCollisionArea().x = object.getWorldX() + object.getCollisionArea().x;
        object.getCollisionArea().y = object.getWorldY() + object.getCollisionArea().y;
    }

    /**
     * Checks for a collision between the given entity and object in the direction
     * the entity is moving.
     *
     * @param entity The entity to check for a collision.
     * @param object The object to check for a collision with.
     * @param player A boolean indicating whether the entity is a player.
     * @param i      The index of the object in the game panel's objects array.
     * @param index  The current index of the object the player is colliding with.
     * @return The index of the object the player is colliding with, or 69 if the
     *         player is not colliding with any object.
     */
    private int checkCollisionInDirection(Entity entity, GameObject object, boolean player, int i, int index) {
        switch (entity.getDirection()) {
            case Constants.UP:
                return checkCollision(entity, object, player, i, index, 0, -entity.getSpeed());
            case Constants.DOWN:
                return checkCollision(entity, object, player, i, index, 0, entity.getSpeed());
            case Constants.LEFT:
                return checkCollision(entity, object, player, i, index, -entity.getSpeed(), 0);
            case Constants.RIGHT:
                return checkCollision(entity, object, player, i, index, entity.getSpeed(), 0);
            default:
                return index;
        }
    }

    /**
     * Checks for a collision between the given entity and object, and updates the
     * entity's collision status and the index if necessary.
     *
     * @param entity The entity to check for a collision.
     * @param object The object to check for a collision with.
     * @param player A boolean indicating whether the entity is a player.
     * @param i      The index of the object in the game panel's objects array.
     * @param index  The current index of the object the player is colliding with.
     * @param dx     The change in the x coordinate of the entity's collision area.
     * @param dy     The change in the y coordinate of the entity's collision area.
     * @return The index of the object the player is colliding with, or the current
     *         index if the player is not colliding with the object.
     */
    private int checkCollision(Entity entity, GameObject object, boolean player, int i, int index, int dx, int dy) {
        entity.getCollisionArea().x += dx;
        entity.getCollisionArea().y += dy;

        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
            logger.log(Level.FINER, "{0} Collision with object {1}",
                    new Object[] { entity.getDirection(), object.getName() });
            if (object.getCollision()) {
                entity.setCollision(true);
            }
            if (player) {
                index = i;
            }
        }

        return index;
    }

    /**
     * Resets the x and y coordinates of the collision areas for the given entity
     * and object to their default values.
     *
     * @param entity The entity whose collision area coordinates are to be reset.
     * @param object The object whose collision area coordinates are to be reset.
     */
    private void resetCollisionAreas(Entity entity, GameObject object) {
        entity.getCollisionArea().x = entity.getCollisionAreaDefaultX();
        entity.getCollisionArea().y = entity.getCollisionAreaDefaultY();

        object.getCollisionArea().x = object.getCollisionAreaDefaultX();
        object.getCollisionArea().y = object.getCollisionAreaDefaultY();
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
                updateCollisionAreas(entity, target[i]);
                index = checkCollisionInDirection(entity, target[i], i, index);
                resetCollisionAreas(entity, target[i]);
            }
        }

        return index;
    }

    /**
     * Updates the x and y coordinates of the collision areas for the given entity
     * and target.
     *
     * @param entity The entity whose collision area coordinates are to be updated.
     * @param target The target entity whose collision area coordinates are to be
     *               updated.
     */
    private void updateCollisionAreas(Entity entity, Entity target) {
        entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionAreaDefaultX();
        entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionAreaDefaultY();

        target.getCollisionArea().x = target.getWorldX() + target.getCollisionArea().x;
        target.getCollisionArea().y = target.getWorldY() + target.getCollisionArea().y;
    }

    /**
     * Checks for a collision between the given entity and target in the direction
     * the entity is moving.
     *
     * @param entity The entity to check for a collision.
     * @param target The target entity to check for a collision with.
     * @param i      The index of the target in the array of entities.
     * @param index  The current index of the target the entity is colliding with.
     * @return The index of the target the entity is colliding with, or the current
     *         index if the entity is not colliding with the target.
     */
    private int checkCollisionInDirection(Entity entity, Entity target, int i, int index) {
        switch (entity.getDirection()) {
            case Constants.UP:
                return checkCollision(entity, target, i, index, 0, -entity.getSpeed());
            case Constants.DOWN:
                return checkCollision(entity, target, i, index, 0, entity.getSpeed());
            case Constants.LEFT:
                return checkCollision(entity, target, i, index, -entity.getSpeed(), 0);
            case Constants.RIGHT:
                return checkCollision(entity, target, i, index, entity.getSpeed(), 0);
            default:
                return index;
        }
    }

    /**
     * Checks for a collision between the given entity and target, and updates the
     * entity's collision status and the index if necessary.
     *
     * @param entity The entity to check for a collision.
     * @param target The target entity to check for a collision with.
     * @param i      The index of the target in the array of entities.
     * @param index  The current index of the target the entity is colliding with.
     * @param dx     The change in the x coordinate of the entity's collision area.
     * @param dy     The change in the y coordinate of the entity's collision area.
     * @return The index of the target the entity is colliding with, or the current
     *         index if the entity is not colliding with the target.
     */
    private int checkCollision(Entity entity, Entity target, int i, int index, int dx, int dy) {
        entity.getCollisionArea().x += dx;
        entity.getCollisionArea().y += dy;

        if (entity.getCollisionArea().intersects(target.getCollisionArea())) {
            entity.setCollision(true);
            index = i;
        }

        return index;
    }

    /**
     * Resets the x and y coordinates of the collision areas for the given entity
     * and target to their default values.
     *
     * @param entity The entity whose collision area coordinates are to be reset.
     * @param target The target entity whose collision area coordinates are to be
     *               reset.
     */
    private void resetCollisionAreas(Entity entity, Entity target) {
        entity.getCollisionArea().x = entity.getCollisionAreaDefaultX();
        entity.getCollisionArea().y = entity.getCollisionAreaDefaultY();

        target.getCollisionArea().x = target.getCollisionAreaDefaultX();
        target.getCollisionArea().y = target.getCollisionAreaDefaultY();
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
