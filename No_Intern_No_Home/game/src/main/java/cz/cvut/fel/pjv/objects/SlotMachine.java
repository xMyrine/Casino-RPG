package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

public class SlotMachine extends Object implements InteractableObject {

    private boolean Finished;

    public SlotMachine() {
        this.name = "slotMachine";
        this.collision = true;
        this.Finished = false;
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
        this.Finished = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/slot_mach_l.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
        collision = true;
    }

    public boolean Finished() {
        return Finished;
    }

    public void setFinished(boolean Finished) {
        this.Finished = Finished;
    }

    @Override
    public void changeState(boolean check) {
        if (check) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/slot_mach_w.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getState() {
        System.out.println("Slot machine is finished");
        return Finished;
    }

}
