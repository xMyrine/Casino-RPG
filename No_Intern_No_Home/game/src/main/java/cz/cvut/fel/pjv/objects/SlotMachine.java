package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;
import java.util.logging.Level;

import cz.cvut.fel.pjv.Toolbox;

/**
 * SlotMachine is a game object that the player can interact with.
 * Player has to interact with the slot machine to finish the first level.
 * 
 * @Author Minh Tu Pham
 */
public class SlotMachine extends GameObject implements InteractableObject {

    private boolean finished;

    public SlotMachine() {
        this.name = "slotMachine";
        this.collision = true;
        this.finished = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/slot_mach_l.png"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error loading image");
        }

    }

    public SlotMachine(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "slotMachine";
        this.collision = true;
        this.finished = false;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/slot_mach_l.png"));
            this.image = Toolbox.scaleImage(this.image, 48, 48);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error loading image");
        }
    }

    public boolean finished() {
        return finished;
    }

    public void setFinished(boolean fin) {
        this.finished = fin;
    }

    /**
     * Change the state of the slot machine.
     * Changes the image of the slot machine to the finished state.
     */
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
        logger.log(Level.FINE, "Slot machine state: {}", finished);
        return finished;
    }

}
