package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

/**
 * Chest is a game object that can be opened by the player.
 * It gives the player a random reward when opened.
 * 
 * @Author Minh Tu Pham
 */
public class Chest extends GameObject {
    private boolean opened;

    public Chest(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "chest";
        this.collision = true;
        this.opened = false;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the chest.
     * 
     * @return true if the chest was opened, false if it was already opened
     */
    public boolean open() {
        if (!opened) {
            opened = true;
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/chest_open.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
