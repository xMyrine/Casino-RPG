package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.entity.Player;

/**
 * Interface for all Alcohol objects in the game.
 * 
 * @Author Minh Tu Pham
 */
public interface Alcohol {

    /**
     * Method for the player to drink the alcohol.
     * This method will be called when the player collides with the alcohol object.
     * This method will always increase the player's luck.
     * Will reset the player's luck to 0.3 if the player drinks too much.
     * 
     * @param player
     * @return
     */
    public float increasePlayersLuck(Player player);

}
