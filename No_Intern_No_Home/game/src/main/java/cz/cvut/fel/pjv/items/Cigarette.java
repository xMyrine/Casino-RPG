package cz.cvut.fel.pjv.items;

import cz.cvut.fel.pjv.entity.Player;

/**
 * Cigarette is an item that the player can use to increase luck.
 * Cigarette can be smoked only in certain locations.
 * Cigarette can be crafted at the craftsmans.
 * 
 * @Author Minh Tu Pham
 */
public class Cigarette extends Items {

    private Player player;

    public Cigarette(Player player) {
        this.player = player;
    }

    /**
     * Uses the item.
     * Triggers the smoke method.
     */
    @Override
    public void use() {
        smoke();
    }

    /**
     * Increases player's luck by 0.1.
     */
    private void smoke() {
        player.setPlayerLuck(player.getPlayerLuck() + 0.1f);
    }

}