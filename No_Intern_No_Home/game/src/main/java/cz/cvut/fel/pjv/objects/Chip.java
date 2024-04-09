package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

public class Chip extends Object {

    public Chip() {
        this.name = "chip";
        this.collision = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chip.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
    }

    public Chip(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "chip";
        this.collision = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chip.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
    }
}
