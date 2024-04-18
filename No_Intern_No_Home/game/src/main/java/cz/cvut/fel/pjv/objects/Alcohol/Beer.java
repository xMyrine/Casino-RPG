package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.objects.Object;
import cz.cvut.fel.pjv.entity.Player;

import javax.imageio.ImageIO;

public class Beer extends Object implements Alcohol {

    public Beer(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "beer";
        this.collision = false;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/beeru.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
    }

    @Override
    public float increasePlayersLuck(Player player) {
        float newLuck = player.getPlayerLuck();
        newLuck += 0.05f;
        System.out.println("You drank a beer and your luck increased by 0.1");
        if (newLuck > 1) {
            newLuck = 0.3f;
            System.out.println("You are too drunk!");
        }

        return newLuck;
    }
}
