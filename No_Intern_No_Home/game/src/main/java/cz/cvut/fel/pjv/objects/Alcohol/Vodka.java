package cz.cvut.fel.pjv.objects.Alcohol;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.objects.Object;

import javax.imageio.ImageIO;

public class Vodka extends Object implements Alcohol {

    private static final float LUCK = 0.07f;

    public Vodka(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "vodka";
        this.collision = false;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/vodka.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public float increasePlayersLuck(Player player) {
        float newLuck = player.getPlayerLuck();
        newLuck += LUCK;
        sound.playMusic(2);
        if (newLuck > 1) {
            newLuck = 0.3f;
            Object.logger.info("Player's luck was now at maximum. Reseting to 0.3.");
        }

        return newLuck;

    }

}
