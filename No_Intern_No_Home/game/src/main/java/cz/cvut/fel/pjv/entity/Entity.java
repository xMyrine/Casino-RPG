package cz.cvut.fel.pjv.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.Logger;

import cz.cvut.fel.pjv.GamePanel;

import java.awt.Graphics2D;

public abstract class Entity {

    protected static Random random = new Random();
    public static GamePanel gamePanel;

    public int worldX, worldY; // player's position in the world
    public int screenX, screenY;
    public int speed;

    public String name;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteIndex = 1;

    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    public boolean collision = false;

    protected int actionCounter = 0;
    protected static final int ACTION_DELAY = 120;
    protected static Logger logger;

    protected String dialogues[] = new String[20];
    protected int dialogueIndex = 0;

    public static void getGamePanelInstance(GamePanel gP) {
        gamePanel = gP;
    }

    protected Map<String, Supplier<BufferedImage>> directionToImageMap = new HashMap<>();
    {
        directionToImageMap.put("up", () -> getSpriteImage(up1, up2));
        directionToImageMap.put("down", () -> getSpriteImage(down1, down2));
        directionToImageMap.put("left", () -> getSpriteImage(left1, left2));
        directionToImageMap.put("right", () -> getSpriteImage(right1, right2));
    }

    public void update() {
        move();

        collision = false;
        gamePanel.collisionManager.checkTile(this);
        gamePanel.collisionManager.checkObjectCollision(this, false);
        gamePanel.collisionManager.checkEntityToPlayerCollision(this);

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

    /*
     * Default draw method for all entities.
     */
    public void draw(Graphics2D g) {
        if (direction.equals("up")) {
            if (spriteCounter % 20 < 10) {
                g.drawImage(up1, screenX, screenY, null);
            } else {
                g.drawImage(up2, screenX, screenY, null);
            }
        } else if (direction.equals("down")) {
            if (spriteCounter % 20 < 10) {
                g.drawImage(down1, screenX, screenY, null);
            } else {
                g.drawImage(down2, screenX, screenY, null);
            }
        } else if (direction.equals("left")) {
            if (spriteCounter % 20 < 10) {
                g.drawImage(left1, screenX, screenY, null);
            } else {
                g.drawImage(left2, screenX, screenY, null);
            }
        } else if (direction.equals("right")) {
            if (spriteCounter % 20 < 10) {
                g.drawImage(right1, screenX, screenY, null);
            } else {
                g.drawImage(right2, screenX, screenY, null);
            }
        }
    }

    /*
     * Default move method for all entities. Will probably be overridden by
     * subclasses.
     */
    public void move() {
        if (direction.equals("up")) {
            worldY -= speed;
        } else if (direction.equals("down")) {
            worldY += speed;
        } else if (direction.equals("left")) {
            worldX -= speed;
        } else if (direction.equals("right")) {
            worldX += speed;
        }
    }

    public void talk() {
        gamePanel.ui.setDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;
        this.turnEntity(gamePanel.player.direction);
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        }
    }

    protected BufferedImage getSpriteImage(BufferedImage sprite1, BufferedImage sprite2) {
        if (spriteIndex == 1) {
            return sprite1;
        } else if (spriteIndex == 2) {
            return sprite2;
        } else {
            return down1;
        }
    }

    protected void turnEntity(String dir) {
        switch (dir) {
            case "up":
                this.direction = "down";
                break;
            case "down":
                this.direction = "up";
                break;
            case "left":
                this.direction = "right";
                break;
            case "right":
                this.direction = "left";
                break;
            default:
                break;
        }
    }

}
