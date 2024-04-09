package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

public class SlotMachine extends Object {

    public SlotMachine() {
        this.name = "slotMachine";
        this.collision = true;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/slot_mach_l.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
        collision = true;
    }

    public SlotMachine(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "slotMachine";
        this.collision = true;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/slot_mach_l.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
        collision = true;
    }

}
