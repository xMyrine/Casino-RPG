package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;
import cz.cvut.fel.pjv.Toolbox;

/**
 * Door is a game object that is opened after completing the level.
 * 
 * @Author Minh Tu Pham
 */
public class Door extends GameObject implements InteractableObject {

    private boolean open;

    public Door(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "door";
        this.collision = true;
        this.open = false;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/doors.png"));
            this.image = Toolbox.scaleImage(this.image, 48, 48);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Change the state of the door.
     * 
     * @param check true if the door should be opened, false if it should be closed
     */
    @Override
    public void changeState(boolean check) {
        if (check) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/door_open.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            collision = false;
        }
    }

    public boolean getState() {
        return !collision;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}
