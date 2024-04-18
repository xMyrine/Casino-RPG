package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.KeyHandler;
import cz.cvut.fel.pjv.Toolbox;
import cz.cvut.fel.pjv.objects.alcohol.*;
import cz.cvut.fel.pjv.objects.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    private int chipCount = 100;
    private int slotMachineCount = 0;
    private Random random = new Random();

    public final int screenX;
    public final int screenY;
    private float playerLuck = 0.1f;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        collisionArea = new Rectangle(8, 16, 32, 32);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        logger = Logger.getLogger(Player.class.getName());
        logger.setLevel(Level.WARNING);

        setDefaultValues();
        getPlayerImage();
    }

    public int getSlotMachineCount() {
        return slotMachineCount;
    }

    public int getChipCount() {
        return chipCount;
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
        worldX = gamePanel.tileSize * 2;
        worldY = gamePanel.tileSize * 2;
        this.speed = 10;
        direction = "down"; // default direction
    }

    public void getPlayerImage() {

        up1 = assignImage("pup");
        up2 = assignImage("pup2");
        down1 = assignImage("pdown");
        down2 = assignImage("pdown2");
        left1 = assignImage("pleft");
        left2 = assignImage("pleft2");
        right1 = assignImage("pright");
        right2 = assignImage("pright2");

    }

    private BufferedImage assignImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + path + ".png"));
            image = Toolbox.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }

    /*
     * Update the player's position based on the key inputs
     */

    public void update() {
        if (keyHandler.up | keyHandler.down || keyHandler.left || keyHandler.right) {
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

            collision = false;
            gamePanel.collisionManager.checkTile(this);

            int objectIndex = gamePanel.collisionManager.checkObjectCollision(this, true);
            pickUp(objectIndex);

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

            spriteCounter++;
            if (spriteCounter > 15) {
                spriteIndex = (spriteIndex == 1) ? 2 : 1;
                spriteCounter = 0;
            }
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
                    chipCount++;
                    gamePanel.objects[objectIndex] = null;
                    gamePanel.sound.playMusic(1);
                    break;
                case "slotMachine":
                    if (gamePanel.objects[objectIndex] instanceof SlotMachine &&
                            !((SlotMachine) gamePanel.objects[objectIndex]).finished() &&
                            chipCount > 0) {
                        chipCount--;
                        if (random.nextFloat() < playerLuck) {
                            ((SlotMachine) gamePanel.objects[objectIndex]).setFinished(true);
                            ((SlotMachine) gamePanel.objects[objectIndex]).changeState(true);
                            slotMachineCount++;
                            gamePanel.sound.playMusic(3);
                        }
                        break;
                    }
                    break;
                case "beer":
                    this.speed = 10;
                    gamePanel.sound.playMusic(2);
                    if (gamePanel.objects[objectIndex] instanceof Beer) {
                        this.playerLuck = ((Beer) gamePanel.objects[objectIndex]).increasePlayersLuck(this);
                        logger.info(String.format("Player's luck increased to %f", this.playerLuck));
                    }
                    gamePanel.objects[objectIndex] = null;
                    break;
                case "door":
                    if (gamePanel.objects[objectIndex] instanceof Door) {
                        System.out.println("Level state: " + gamePanel.levelManager.checkLevelFirstFinished());
                        ((Door) gamePanel.objects[objectIndex])
                                .changeState(gamePanel.levelManager
                                        .checkLevelFirstFinished());
                        logger.log(Level.INFO, "Door state changed");
                        if (((Door) gamePanel.objects[objectIndex]).getState()
                                && !((Door) gamePanel.objects[objectIndex]).open) {
                            ((Door) gamePanel.objects[objectIndex]).open = true;
                            gamePanel.sound.playMusic(4);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private Map<String, Supplier<BufferedImage>> directionToImageMap = new HashMap<>();
    {
        directionToImageMap.put("up", () -> (spriteIndex == 1) ? up1 : (spriteIndex == 2) ? up2 : null);
        directionToImageMap.put("down", () -> (spriteIndex == 1) ? down1 : (spriteIndex == 2) ? down2 : null);
        directionToImageMap.put("left", () -> (spriteIndex == 1) ? left1 : (spriteIndex == 2) ? left2 : null);
        directionToImageMap.put("right", () -> (spriteIndex == 1) ? right1 : (spriteIndex == 2) ? right2 : null);
    }

    public void draw(Graphics2D g) {
        BufferedImage img = directionToImageMap.getOrDefault(direction, () -> null).get();
        g.drawImage(img, screenX, screenY, null);
    }

}
