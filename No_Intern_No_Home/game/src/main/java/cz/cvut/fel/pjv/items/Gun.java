package cz.cvut.fel.pjv.items;

import cz.cvut.fel.pjv.minigames.Pokermon;

/**
 * Gun is an item that the player can use to shoot pokachu in the Pokermon
 * minigame.
 * Gun can be used only in the Pokermon minigame.
 * Gun can be crafted at the craftsmans.
 * 
 * @Author Minh Tu Pham
 */
public class Gun extends Items {
    private Pokermon pokermon;

    public Gun(Pokermon pokermon) {
        this.pokermon = pokermon;
    }

    /**
     * Uses the item.
     * Shoots pokachu in the Pokermon minigame.
     */
    @Override
    public void use() {
        pokermon.shoot();
    }

}
