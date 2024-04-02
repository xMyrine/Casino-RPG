package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        collisionArea = new Rectangle(8, 16, gamePanel.tileSize * (2 / 3), gamePanel.tileSize * (2 / 3));

        setDefaultValues();
        getPlayerImage();
    }

    /*
     * Update the player's position based on the key inputs
     */

    public void setDefaultValues() {
        worldX = gamePanel.worldWidth / 2 - gamePanel.tileSize / 2;
        worldY = gamePanel.worldHeight / 2 - gamePanel.tileSize / 2;
        speed = 4;
        direction = "down"; // default direction
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/pup.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/pup2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/pdown.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/pdown2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/pleft.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/pleft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/pright.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/pright2.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.up == true || keyHandler.down == true || keyHandler.left == true || keyHandler.right == true) {
            if (keyHandler.up == true) {
                direction = "up";
            }
            if (keyHandler.down == true) {
                direction = "down";
            }
            if (keyHandler.left == true) {
                direction = "left";
            }
            if (keyHandler.right == true) {
                direction = "right";
            }

            collision = false;
            gamePanel.collisionManager.checkTile(this);

            if (collision == false) {

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

    public void draw(Graphics2D g) {
        BufferedImage img = null;

        switch (direction) {
            case "up":
                img = (spriteIndex == 1) ? up1 : (spriteIndex == 2) ? up2 : img;
                break;
            case "down":
                img = (spriteIndex == 1) ? down1 : (spriteIndex == 2) ? down2 : img;
                break;
            case "left":
                img = (spriteIndex == 1) ? left1 : (spriteIndex == 2) ? left2 : img;
                break;
            case "right":
                img = (spriteIndex == 1) ? right1 : (spriteIndex == 2) ? right2 : img;
                break;
        }

        g.drawImage(img, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

    }

}
