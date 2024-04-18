package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.objects.Object;
import cz.cvut.fel.pjv.entity.Player;

import javax.imageio.ImageIO;

public class Beer extends Object implements Alcohol {

    private static final float luck = 0.05f;

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
        newLuck += luck;
        sound.playMusic(2);
        if (newLuck > 1) {
            newLuck = 0.3f;
        }

        return newLuck;
    }
}
