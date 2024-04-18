package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.Sound;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.objects.Object;

import javax.imageio.ImageIO;

public class Vodka extends Object implements Alcohol {

    private static final float luck = 0.1f;

    public Vodka(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "vodka";
        this.collision = false;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/vodka.png"));
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
