package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.Toolbox;

public class Chip extends GameObject {

    public Chip() {
        this.name = "chip";
        this.collision = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/chip.png"));
            this.image = Toolbox.scaleImage(this.image, 48, 48);
        } catch (Exception e) {
            e.printStackTrace();
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
        }
    }
}
