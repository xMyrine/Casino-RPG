package cz.cvut.fel.pjv.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Sound;

public abstract class GameObject {

    protected BufferedImage image;
    protected String name;
    protected boolean collision;
    protected int worldX;
    protected int worldY;
    protected Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    protected int collisionAreaDefaultX = 0;
    protected int collisionAreaDefaultY = 0;

    protected static Logger logger = Logger.getLogger(GameObject.class.getName());
    protected static Sound sound = new Sound();

    /**
     * Draw the object
     * 
     * @param g         Graphics2D
     * @param gamePanel GamePanel
     */
    public void draw(Graphics2D g, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.getWorldX() + GamePanel.SCREEN_WIDTH - 8 * GamePanel.TILE_SIZE - 24;

        int screenY = worldY - gamePanel.player.getWorldY() + GamePanel.SCREEN_HEIGHT - 6 * GamePanel.TILE_SIZE - 24;

        // draw only objects that are in the screen
        if (worldX + GamePanel.TILE_SIZE > gamePanel.player.getWorldX() - GamePanel.SCREEN_WIDTH
                && worldX - GamePanel.TILE_SIZE < gamePanel.player.getWorldX() + GamePanel.SCREEN_WIDTH
                && worldY + GamePanel.TILE_SIZE > gamePanel.player.getWorldY() - GamePanel.SCREEN_HEIGHT
                && worldY - GamePanel.TILE_SIZE < gamePanel.player.getWorldY() + GamePanel.SCREEN_HEIGHT) {
            g.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        }
    }

    /**
     * Change the object's picture
     * 
     * @param filepath String of the file path
     */
    public void changePicture(String filepath) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSolid() {
        return collision;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean getCollision() {
        return collision;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public int getCollisionAreaDefaultX() {
        return collisionAreaDefaultX;
    }

    public int getCollisionAreaDefaultY() {
        return collisionAreaDefaultY;
    }
}
