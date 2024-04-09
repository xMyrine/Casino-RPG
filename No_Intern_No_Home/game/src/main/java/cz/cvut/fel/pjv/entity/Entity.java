package cz.cvut.fel.pjv.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY; // player's position in the world
    public int screenX, screenY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteIndex = 1;

    public Rectangle collisionArea;
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    public boolean collision = false;
}
