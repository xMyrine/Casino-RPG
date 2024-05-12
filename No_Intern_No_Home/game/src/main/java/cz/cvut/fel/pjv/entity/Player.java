package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Inventory;
import cz.cvut.fel.pjv.KeyHandler;
import cz.cvut.fel.pjv.Toolbox;
import cz.cvut.fel.pjv.objects.*;
import cz.cvut.fel.pjv.objects.Alcohol.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Player extends Entity {

    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private Inventory inventory;
    private boolean showInventory = false;

    private int chipCount = 500;
    private int slotMachineCount = 0;
    private Random random = new Random();

    private float playerLuck = 1f;

    private int npcIndex = 69;
    private int[] specialItemsFragnments = { 0, 0, 0 };
    private int cigar;
    private int gun;
    private int cards;

    public static final int CIGAR_INDEX = 0;
    public static final int GUN_INDEX = 1;
    public static final int CARDS_INDEX = 2;

    private static final String PLAYER_LUCK_MESSAGE = "Player''s luck increased to {0}";

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        cigar = 0;
        gun = 1;
        cards = 0;

        screenX = gamePanel.screenWidth / 2 - GamePanel.TILE_SIZE / 2;
        screenY = gamePanel.screenHeight / 2 - GamePanel.TILE_SIZE / 2;

        collisionArea = new Rectangle(8, 16, 32, 32);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        inventory = new Inventory(this);

        logger = Logger.getLogger(Player.class.getName());
        logger.setLevel(Level.WARNING);

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

    public void setDefaultValues() {
        worldX = GamePanel.TILE_SIZE * 2;
        worldY = GamePanel.TILE_SIZE * 2;
        this.speed = 15;
        direction = "down"; // default direction
    }

    public void setSpeed(int speed) {
        if (speed < 0) {
            this.speed = 0;
        } else {
            this.speed = speed;
        }

    }

    public int getSpeed() {
        return speed;
    }

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

    private BufferedImage assignImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path +
                    ".png"));
            image = Toolbox.scaleImage(image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }

    /*
     * Update the player's position based on the key inputs
     */

    @Override
    public void update() {
        if (keyHandler.up || keyHandler.down || keyHandler.left || keyHandler.right) {
            updateDirection();
            collision = false;
            checkCollisions();
            movePlayer();
            updateSprite();
        }
    }

    private void updateDirection() {
        if (keyHandler.up) {
            direction = "up";
        }
        if (keyHandler.down) {
            direction = "down";
        }
        if (keyHandler.left) {
            direction = "left";
        }
        if (keyHandler.right) {
            direction = "right";
        }
    }

    private void checkCollisions() {
        gamePanel.collisionManager.checkTile(this);
        int objectIndex = gamePanel.collisionManager.checkObjectCollision(this, true);
        pickUp(objectIndex);
        npcIndex = gamePanel.collisionManager.checkEntityCollision(this, gamePanel.entities);
        interactWithNPC(npcIndex);
    }

    private void movePlayer() {
        if (!collision) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                default:
                    break;
            }
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 15) {
            spriteIndex = (spriteIndex == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    /*
     * Pick up an object
     */

    public void pickUp(int objectIndex) {
        if (objectIndex != 69) {
            String objectName = gamePanel.objects[objectIndex].name;

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

    private void pickUpChip(int objectIndex) {
        chipCount++;
        gamePanel.objects[objectIndex] = null;
        gamePanel.sound.playMusic(1);
    }

    private void pickUpSlotMachine(int objectIndex) {
        if (gamePanel.objects[objectIndex] instanceof SlotMachine &&
                !((SlotMachine) gamePanel.objects[objectIndex]).finished() &&
                chipCount > 0) {
            chipCount--;
            if (random.nextFloat() < playerLuck) {
                ((SlotMachine) gamePanel.objects[objectIndex]).setFinished(true);
                ((SlotMachine) gamePanel.objects[objectIndex]).changeState(true);
                slotMachineCount++;
                gamePanel.levelManager.checkLevelFinished();
                gamePanel.sound.playMusic(3);
            }
        }
    }

    private void pickUpBeer(int objectIndex) {
        if (gamePanel.objects[objectIndex] instanceof Beer) {
            this.playerLuck = ((Beer) gamePanel.objects[objectIndex]).increasePlayersLuck(this);
            logger.log(Level.INFO, PLAYER_LUCK_MESSAGE, this.playerLuck);
        }
        gamePanel.objects[objectIndex] = null;
    }

    private void pickUpChest(int objectIndex) {
        if (gamePanel.objects[objectIndex] instanceof Chest && gamePanel.keyHandler.interact
                && ((Chest) gamePanel.objects[objectIndex]).open()) {
            chipCount += 50;
            int randomInt = random.nextInt(3);
            specialItemsFragnments[randomInt]++;
            gamePanel.keyHandler.interact = false;
            gamePanel.sound.playMusic(5);
        }
    }

    private void pickUpVodka(int objectIndex) {
        if (gamePanel.objects[objectIndex] instanceof Vodka) {
            this.playerLuck = ((Vodka) gamePanel.objects[objectIndex]).increasePlayersLuck(this);
            logger.log(Level.INFO, PLAYER_LUCK_MESSAGE, this.playerLuck);
        }
        gamePanel.objects[objectIndex] = null;
    }

    private void pickUpDomPerignon(int objectIndex) {
        if (gamePanel.objects[objectIndex] instanceof DomPerignon) {
            this.playerLuck = ((DomPerignon) gamePanel.objects[objectIndex]).increasePlayersLuck(this);
            logger.log(Level.INFO, PLAYER_LUCK_MESSAGE, this.playerLuck);
        }
        gamePanel.objects[objectIndex] = null;
    }

    /*
     * Interact with an NPC
     */
    public void interactWithNPC(int npcIndex) {
        if (npcIndex != 69) {
            if (gamePanel.keyHandler.interact) {
                gamePanel.changeGameState(GamePanel.DIALOGUESCREEN);
                gamePanel.entities[npcIndex].talk();
            }
            gamePanel.keyHandler.interact = false;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage img = directionToImageMap.getOrDefault(direction, () -> null).get();
        g.drawImage(img, screenX, screenY, null);
    }

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

    public void setSpecialItem(int item, int count) {
        switch (item) {
            case CIGAR_INDEX:
                cigar = count;
                break;
            case GUN_INDEX:
                gun = count;
                break;
            case CARDS_INDEX:
                cards = count;
                break;
            default:
                break;
        }
    }

    public int getSpecialItem(int item) {
        switch (item) {
            case CIGAR_INDEX:
                return cigar;
            case GUN_INDEX:
                return gun;
            case CARDS_INDEX:
                return cards;
            default:
                return 0;
        }
    }

}
