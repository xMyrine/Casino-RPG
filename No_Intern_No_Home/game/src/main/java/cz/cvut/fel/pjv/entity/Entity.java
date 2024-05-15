package cz.cvut.fel.pjv.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;
import lombok.Data;
import cz.cvut.fel.pjv.Constants;

/**
 * Abstract class for all entities in the game.
 * This class contains the basic methods and attributes for all entities.
 * Such as movement, collision, dialogue, etc.
 * 
 * @Author Minh Tu Pham
 */
public abstract class Entity {

    protected static Random random = new Random();
    protected static GamePanel gamePanel;

    protected int worldX;
    protected int worldY;
    protected int screenX;
    protected int screenY;
    protected int speed;

    protected String name;
    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;
    protected String direction;
    protected int spriteCounter = 0;
    protected int spriteIndex = 1;

    protected Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    protected int collisionAreaDefaultX;
    protected int collisionAreaDefaultY;
    protected boolean collision = false;

    protected int actionCounter = 0;
    protected static final int ACTION_DELAY = 120;
    protected static Logger logger;

    protected String[] dialogues = new String[20];
    protected int dialogueIndex = 0;

    protected Map<String, Supplier<BufferedImage>> directionToImageMap = new HashMap<>();
    {
        directionToImageMap.put(Constants.UP, () -> getSpriteImage(up1, up2));
        directionToImageMap.put(Constants.DOWN, () -> getSpriteImage(down1, down2));
        directionToImageMap.put(Constants.LEFT, () -> getSpriteImage(left1, left2));
        directionToImageMap.put(Constants.RIGHT, () -> getSpriteImage(right1, right2));
    }

    public static void getGamePanelInstance(GamePanel gP) {
        gamePanel = gP;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    public int getCollisionAreaDefaultX() {
        return collisionAreaDefaultX;
    }

    public int getCollisionAreaDefaultY() {
        return collisionAreaDefaultY;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public String getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    /**
     * Default update method for all entities. Will probably be overridden by
     * subclasses.
     * This method is used to update the entity in the game world.
     */
    public void update() {
        move();

        collision = false;
        gamePanel.getCollisionManager().checkTile(this);
        gamePanel.getCollisionManager().checkObjectCollision(this, false);
        gamePanel.getCollisionManager().checkEntityToPlayerCollision(this);

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

        spriteCounter++;
        if (spriteCounter > 15) {
            spriteIndex = (spriteIndex == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    /**
     * Default draw method for all entities.
     * This method is used to draw the entity on the screen.
     * 
     * @param g Graphics2D object
     */
    public void draw(Graphics2D g) {
        BufferedImage image1 = null;
        BufferedImage image2 = null;

        if (direction.equals(Constants.UP)) {
            image1 = up1;
            image2 = up2;
        } else if (direction.equals(Constants.DOWN)) {
            image1 = down1;
            image2 = down2;
        } else if (direction.equals(Constants.LEFT)) {
            image1 = left1;
            image2 = left2;
        } else if (direction.equals(Constants.RIGHT)) {
            image1 = right1;
            image2 = right2;
        }

        if (image1 != null && image2 != null) {
            if (spriteCounter % 20 < 10) {
                g.drawImage(image1, screenX, screenY, null);
            } else {
                g.drawImage(image2, screenX, screenY, null);
            }
        }
    }

    /**
     * Default assignImage method for all entities.
     * 
     * @param path path to the image
     * @return BufferedImage object
     */
    protected BufferedImage assignImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
            image = Toolbox.scaleImage(image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }

    /**
     * Default move method for all entities. Will probably be overridden by
     * subclasses.
     * This method is used to move the entity in the game world.
     */
    public void move() {
        if (direction.equals(Constants.UP)) {
            worldY -= speed;
        } else if (direction.equals(Constants.DOWN)) {
            worldY += speed;
        } else if (direction.equals(Constants.LEFT)) {
            worldX -= speed;
        } else if (direction.equals(Constants.RIGHT)) {
            worldX += speed;
        }
    }

    /**
     * Default talk method for all entities. Will probably be overridden by
     * subclasses.
     * This method is used to display dialogues when the player interacts with an
     * entity.
     */
    public void talk() {
        gamePanel.getGameUI().setDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;
        this.turnEntity(gamePanel.getPlayer().direction);
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        }
    }

    /**
     * Default getSpriteImage method for all entities.
     * This method is used to get the correct sprite image for the entity.
     * 
     * @param sprite1 first sprite image
     * @param sprite2 second sprite image
     */
    protected BufferedImage getSpriteImage(BufferedImage sprite1, BufferedImage sprite2) {
        if (spriteIndex == 1) {
            return sprite1;
        } else if (spriteIndex == 2) {
            return sprite2;
        } else {
            return down1;
        }
    }

    /**
     * Default turnEntity method for all entities.
     * Mainly used for turning the entity to face the player.
     * 
     * @param dir direction to turn to
     */
    protected void turnEntity(String dir) {
        switch (dir) {
            case Constants.UP:
                this.direction = Constants.DOWN;
                break;
            case Constants.DOWN:
                this.direction = Constants.UP;
                break;
            case Constants.LEFT:
                this.direction = Constants.RIGHT;
                break;
            case Constants.RIGHT:
                this.direction = Constants.LEFT;
                break;
            default:
                break;
        }
    }

    public void setWorldX(int x) {
        this.worldX = x;
    }

    public void setWorldY(int y) {
        this.worldY = y;
    }
}
