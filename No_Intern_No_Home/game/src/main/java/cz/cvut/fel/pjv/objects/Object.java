package cz.cvut.fel.pjv.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Sound;

public abstract class Object {

    public BufferedImage image;
    public String name;
    public boolean collision;
    public int worldX;
    public int worldY;
    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    public int collisionAreaDefaultX = 0;
    public int collisionAreaDefaultY = 0;

    protected static Logger logger = Logger.getLogger(Object.class.getName());
    protected static Sound sound = new Sound();

    public void draw(Graphics2D g, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.screenWidth - 8 * gamePanel.tileSize - 24;

        int screenY = worldY - gamePanel.player.worldY + gamePanel.screenHeight - 6 * gamePanel.tileSize - 24;

        // draw only objects that are in the screen
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.screenWidth
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.screenWidth
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.screenHeight
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.screenHeight) {
            g.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

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
}
