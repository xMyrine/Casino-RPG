package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

public class Chest extends Object {

    public Chest(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "chest";
        this.collision = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
