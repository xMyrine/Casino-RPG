package cz.cvut.fel.pjv.objects;

import javax.imageio.ImageIO;

public class Door extends Object implements InteractableObject {

    public boolean open;

    public Door(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "door";
        this.collision = true;
        this.open = false;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/doors.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void changeState(boolean check) {
        if (check) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/bomb_f1.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            collision = false;
        }
    }

    public boolean getState() {
        return !collision;
    }

}
