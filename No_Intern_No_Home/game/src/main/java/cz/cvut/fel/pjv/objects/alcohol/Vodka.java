package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.objects.GameObject;

import javax.imageio.ImageIO;

/**
 * Vodka is an alcohol object in the game.
 * Vodka is a strong alcohol that increases the player's luck by 0.07.
 * 
 * @Author Minh Tu Pham
 */
public class Vodka extends GameObject implements Alcohol {

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

    /**
     * Increase the player's luck by 0.07.
     * 
     * @param player the player object
     */
    @Override
    public float increasePlayersLuck(Player player) {
        float newLuck = player.getPlayerLuck();
        newLuck += LUCK;
        sound.playMusic(2);
        if (newLuck > 1) {
            newLuck = 0.3f;
            GameObject.logger.info("Player's luck was now at maximum. Reseting to 0.3.");
        }

        return newLuck;

    }

}
