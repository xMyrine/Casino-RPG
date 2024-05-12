package cz.cvut.fel.pjv.items;

import cz.cvut.fel.pjv.entity.Player;

public class Cigarette extends Items {

    private Player player;

    public Cigarette(Player player) {
        this.player = player;
    }

    @Override
    public void use() {
        smoke();
    }

    private void smoke() {
        player.setPlayerLuck(player.getPlayerLuck() + 0.1f);
        System.out.println("You smoked a cigarette. Your luck increased by 0.1.");
    }

}