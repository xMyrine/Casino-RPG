package cz.cvut.fel.pjv.objects;

public class Coin extends Objects {

    public Coin(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "coin";
        this.collision = false;
    }
}
