package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Inventory;
import cz.cvut.fel.pjv.KeyHandler;
import cz.cvut.fel.pjv.objects.*;
import cz.cvut.fel.pjv.objects.alcohol.*;
import cz.cvut.fel.pjv.items.*;
import cz.cvut.fel.pjv.Constants;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Player is the main character in the game.
 * 
 * @Author Minh Tu Pham
 */
public class Player extends Entity {

    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private Inventory inventory;
    private boolean showInventory = false;

    private int chipCount;
    private int slotMachineCount = 0;
    private Random random = new Random();
    private float playerLuck = 1f;
    private int npcIndex = 69;
    private int[] specialItemsFragnments = { 0, 0, 0 };

    public static final int CIGAR_INDEX = 0;
    public static final int GUN_INDEX = 1;
    public static final int CARDS_INDEX = 2;

    private ArrayList<Items> items;

    private static final String PLAYER_LUCK_MESSAGE = "Player''s luck increased to {0}";

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = GamePanel.SCREEN_WIDTH / 2 - GamePanel.TILE_SIZE / 2;
        screenY = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.TILE_SIZE / 2;

        collisionArea = new Rectangle(8, 16, 32, 32);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        inventory = new Inventory();

        logger = Logger.getLogger(Player.class.getName());
        logger.setLevel(Level.WARNING);

        items = new ArrayList<>();

        setDefaultValues();
        getPlayerImage();
    }

    public int getNpcIndex() {
        return npcIndex;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void toggleInventory() {
        showInventory = !showInventory;
    }

    public boolean isInventoryVisible() {
        return showInventory;
    }

    public int getSlotMachineCount() {
        return slotMachineCount;
    }

    public int getChipCount() {
        return chipCount;
    }

    public void setChipCount(int chipCount) {
        this.chipCount = chipCount;
    }

    /**
     * Set the player's luck
     * Ensure the player's luck is between 0 and 1
     * 
     * @param playerLuck the player's luck
     * 
     * @return the player's luck
     */
    public void setPlayerLuck(float playerLuck) {
        if (playerLuck < 0) {
            this.playerLuck = 0;
        } else if (playerLuck > 1) {
            this.playerLuck = 1;
        } else {
            this.playerLuck = playerLuck;
        }
    }

    public float getPlayerLuck() {
        return playerLuck;
    }

    /**
     * Set the player's default values
     */
    public void setDefaultValues() {
        worldX = GamePanel.TILE_SIZE * 2;
        worldY = GamePanel.TILE_SIZE * 2;
        this.speed = 3;
        direction = Constants.DOWN; // default direction
        chipCount = 500;
        items.add(new Cigarette(this));
    }

    /**
     * Set the player's speed
     * Ensure the player's speed is not negative
     * 
     * @param speed the player's speed
     */
    @Override
    public void setSpeed(int speed) {
        if (speed < 0) {
            this.speed = 0;
        } else {
            this.speed = speed;
        }

    }

    /**
     * Get the player's image
     */
    public void getPlayerImage() {
        up1 = assignImage("/player/pup");
        up2 = assignImage("/player/pup2");
        down1 = assignImage("/player/pdown");
        down2 = assignImage("/player/pdown2");
        left1 = assignImage("/player/pleft");
        left2 = assignImage("/player/pleft2");
        right1 = assignImage("/player/pright");
        right2 = assignImage("/player/pright2");

    }

    /**
     * Update the player's position based on the key inputs
     */
    @Override
    public void update() {
        if (keyHandler.getUp() || keyHandler.getDown() || keyHandler.getLeft() || keyHandler.getRight()) {
            updateDirection();
            collision = false;
            checkCollisions();
            movePlayer();
            updateSprite();
        }
    }

    /**
     * Check if the player can smoke a cigarette
     */
    private boolean canSmoke() {
        if (worldY <= 270 && worldX >= 2100) {
            gamePanel.getGameUI().setAnnounceMessage("You can smoke here (Q)");
            return true;
        }
        return false;
    }

    /**
     * Update the direction of the player based on the key inputs
     */
    private void updateDirection() {
        if (keyHandler.getUp()) {
            direction = Constants.UP;
        }
        if (keyHandler.getDown()) {
            direction = Constants.DOWN;
        }
        if (keyHandler.getLeft()) {
            direction = Constants.LEFT;
        }
        if (keyHandler.getRight()) {
            direction = Constants.RIGHT;
        }
    }

    /**
     * Check for collisions with objects, tiles and NPCs
     */
    private void checkCollisions() {
        gamePanel.getCollisionManager().checkTile(this);
        int objectIndex = gamePanel.getCollisionManager().checkObjectCollision(this, true);
        pickUp(objectIndex);
        npcIndex = gamePanel.getCollisionManager().checkEntityCollision(this, gamePanel.getEntities());
        interactWithNPC(npcIndex);
    }

    /**
     * Move the player based on the direction
     */
    private void movePlayer() {
        if (!collision) {
            switch (direction) {
                case Constants.UP:
                    worldY -= speed;
                    break;
                case Constants.DOWN:
                    worldY += speed;
                    break;
                case Constants.LEFT:
                    worldX -= speed;
                    break;
                case Constants.RIGHT:
                    worldX += speed;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Update the player's sprite
     */
    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 15) {
            spriteIndex = (spriteIndex == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    /**
     * Pick up an object
     * 
     * @param objectIndex index of the object in the objects array
     */
    public void pickUp(int objectIndex) {
        if (objectIndex != 69) {
            String objectName = gamePanel.getGameObject(objectIndex).getName();

            switch (objectName) {
                case "chip":
                    pickUpChip(objectIndex);
                    break;
                case "slotMachine":
                    pickUpSlotMachine(objectIndex);
                    break;
                case "beer":
                    pickUpBeer(objectIndex);
                    break;
                case "chest":
                    pickUpChest(objectIndex);
                    break;
                case "vodka":
                    pickUpVodka(objectIndex);
                    break;
                case "domperignon":
                    pickUpDomPerignon(objectIndex);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pick up a chip
     * 
     * @param objectIndex index of the object in the objects array
     */
    private void pickUpChip(int objectIndex) {
        chipCount += 25;
        gamePanel.setGameObject(objectIndex, null);
        gamePanel.getSound().playMusic(1);
    }

    /**
     * Pick up a slot machine
     * 
     * @param objectIndex index of the object in the objects array
     */
    private void pickUpSlotMachine(int objectIndex) {
        if (gamePanel.getGameObject(objectIndex) instanceof SlotMachine &&
                !((SlotMachine) gamePanel.getGameObject(objectIndex)).finished() &&
                chipCount > 0) {
            chipCount--;
            if (random.nextFloat() < playerLuck) {
                ((SlotMachine) gamePanel.getGameObject(objectIndex)).setFinished(true);
                ((SlotMachine) gamePanel.getGameObject(objectIndex)).changeState(true);
                slotMachineCount++;
                gamePanel.getLevelManager().checkLevelFinished();
                gamePanel.getSound().playMusic(3);
            }
        }
    }

    /**
     * Pick up a beer
     * 
     * @param objectIndex index of the object in the objects array
     */
    private void pickUpBeer(int objectIndex) {
        if (gamePanel.getGameObject(objectIndex) instanceof Beer) {
            this.playerLuck = ((Beer) gamePanel.getGameObject(objectIndex)).increasePlayersLuck(this);
            logger.log(Level.INFO, PLAYER_LUCK_MESSAGE, this.playerLuck);
        }
        gamePanel.setGameObject(objectIndex, null);
    }

    /**
     * Pick up a chest
     * 
     * @param objectIndex index of the object in the objects array
     */
    private void pickUpChest(int objectIndex) {
        if (gamePanel.getGameObject(objectIndex) instanceof Chest && gamePanel.getKeyHandler().getInteract()
                && ((Chest) gamePanel.getGameObject(objectIndex)).open()) {
            chipCount += 50;
            int randomInt = random.nextInt(3);
            specialItemsFragnments[randomInt]++;
            gamePanel.getKeyHandler().setInteract(false);
            gamePanel.getSound().playMusic(5);
        }
    }

    /**
     * Pick up a vodka
     * 
     * @param objectIndex index of the object in the objects array
     */
    private void pickUpVodka(int objectIndex) {
        if (gamePanel.getGameObject(objectIndex) instanceof Vodka) {
            this.playerLuck = ((Vodka) gamePanel.getGameObject(objectIndex)).increasePlayersLuck(this);
            logger.log(Level.INFO, PLAYER_LUCK_MESSAGE, this.playerLuck);
        }
        gamePanel.setGameObject(objectIndex, null);
    }

    /**
     * Pick up a Dom Perignon
     * 
     * @param objectIndex index of the object in the objects array
     */
    private void pickUpDomPerignon(int objectIndex) {
        if (gamePanel.getGameObject(objectIndex) instanceof DomPerignon) {
            this.playerLuck = ((DomPerignon) gamePanel.getGameObject(objectIndex)).increasePlayersLuck(this);
            logger.log(Level.INFO, PLAYER_LUCK_MESSAGE, this.playerLuck);
        }
        gamePanel.setGameObject(objectIndex, null);
    }

    /**
     * Interact with an NPC
     * 
     * @param npcIndex index of the NPC in the entities array
     */
    public void interactWithNPC(int npcIndex) {
        if (npcIndex != 69) {
            if (gamePanel.getKeyHandler().getInteract()) {
                gamePanel.changeGameState(GamePanel.DIALOGUESCREEN);
                gamePanel.getEntity(npcIndex).talk();
            }
            gamePanel.getKeyHandler().setInteract(false);
        }
    }

    /**
     * Draw the player on the screen
     * 
     * @param g Graphics2D object
     */
    @Override
    public void draw(Graphics2D g) {
        BufferedImage img = directionToImageMap.getOrDefault(direction, () -> null).get();
        g.drawImage(img, screenX, screenY, null);
    }

    /**
     * Get the player's amount of special item fragments
     * 
     * @param item the special item INDEX (CIGAR_INDEX, GUN_INDEX, CARDS_INDEX)
     * @return the amount of special item fragments
     */
    public int getSpecialItemsFragmentCount(int item) {
        switch (item) {
            case CIGAR_INDEX:
                return specialItemsFragnments[0];
            case GUN_INDEX:
                return specialItemsFragnments[1];
            case CARDS_INDEX:
                return specialItemsFragnments[2];
            default:
                return 0;
        }
    }

    /**
     * Set the player's inventory
     * 
     * @param item  the special item INDEX (CIGAR_INDEX, GUN_INDEX, CARDS_INDEX)
     * @param count the amount of special item fragments
     */
    public void setSpecialItemsFragmentCount(int item, int count) {
        switch (item) {
            case CIGAR_INDEX:
                specialItemsFragnments[0] = count;
                break;
            case GUN_INDEX:
                specialItemsFragnments[1] = count;
                break;
            case CARDS_INDEX:
                specialItemsFragnments[2] = count;
                break;
            default:
                break;
        }
    }

    /**
     * Add an item to the player's inventory
     * 
     * @param item the item to add
     */
    public void addItems(Items item) {
        items.add(item);
    }

    /**
     * Get the gun from the player's inventory
     * 
     * @return the Gun object, null if not found
     */
    public Gun getFirstGun() {
        for (Items item : items) {
            if (item instanceof Gun) {
                return (Gun) item;
            }
        }
        return null;
    }

    /**
     * Remove the gun from the player's inventory
     * 
     * @return true if the gun was removed, false otherwise
     */
    private boolean removeFirstGun() {
        for (Items item : items) {
            if (item instanceof Gun) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Use the gun from the player's inventory
     * 
     * @return Cigarette if is in inventory, null otherwise
     */
    private Cigarette getFirstCigarette() {
        for (Items item : items) {
            if (item instanceof Cigarette) {
                return (Cigarette) item;
            }
        }
        return null;
    }

    /**
     * Remove the cigarette from the player's inventory
     * 
     * @return true if the cigarette was removed, false otherwise
     */
    private boolean removeFirstCigarette() {
        for (Items item : items) {
            if (item instanceof Cigarette) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Use the cigarette from the player's inventory
     */
    public void useCigarette() {
        Cigarette cigarette = getFirstCigarette();
        if (cigarette != null && canSmoke()) {
            cigarette.use();
            gamePanel.getGameUI().setAnnounceMessage("You smoked a cigarette, your luck increased");
            removeFirstCigarette();
        } else {
            logger.log(Level.WARNING, "No cigarette found in inventory");
            gamePanel.getGameUI().setAnnounceMessage("You don't have a cigarette or you can't smoke here (Q)");
        }
    }

    /**
     * Get the cards from the player's inventory
     * 
     * @return the PlayingCards object
     */
    private PlayingCards getFirstCards() {
        for (Items item : items) {
            if (item instanceof PlayingCards) {
                return (PlayingCards) item;
            }
        }
        return null;
    }

    /**
     * Remove the cards from the player's inventory
     * 
     * @return true if the cards were removed, false otherwise
     */
    private boolean removeFirstCards() {
        for (Items item : items) {
            if (item instanceof PlayingCards) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Use the cards from the player's inventory
     *
     * @param item the item to find in the inventory
     * 
     * @return true if the item is in the inventory, false otherwise
     */
    public boolean isSpecialItemInInventory(int item) {
        switch (item) {
            case CIGAR_INDEX:
                return getFirstCigarette() != null;
            case GUN_INDEX:
                return getFirstGun() != null;
            case CARDS_INDEX:
                return getFirstCards() != null;
            default:
                return false;
        }
    }

    /**
     * Remove a special item from the player's inventory
     *
     * @param item the item to remove
     * @return true if the item was removed, false otherwise
     */
    public boolean removeSpecialItem(int item) {
        switch (item) {
            case CIGAR_INDEX:
                removeFirstCigarette();
                return true;
            case GUN_INDEX:
                return removeFirstGun();
            case CARDS_INDEX:
                return removeFirstCards();
            default:
                return false;
        }
    }

    /**
     * Get the number of instances of a class in the player's inventory
     * 
     * @param clazz the class to count instances of
     * @return the number of instances of the class in the player's inventory
     */
    public int countInstances(Class<?> clazz) {
        int count = 0;
        for (Items item : items) {
            if (clazz.isInstance(item)) {
                count++;
            }
        }
        return count;
    }

    public void addItem(Items item) {
        items.add(item);
    }
}
