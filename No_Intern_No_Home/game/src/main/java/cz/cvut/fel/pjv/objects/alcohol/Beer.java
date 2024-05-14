package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.objects.GameObject;
import cz.cvut.fel.pjv.entity.Player;

import javax.imageio.ImageIO;

/**
 * Beer is an alcohol object in the game.
 * It increases the player's luck by 0.05.
 */
public class Beer extends GameObject implements Alcohol {

    private static final float LUCK = 0.05f;

    public Beer(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "beer";
        this.collision = false;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/beeru.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increase the player's luck by 0.05.
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
        }

        return newLuck;
    }
}
