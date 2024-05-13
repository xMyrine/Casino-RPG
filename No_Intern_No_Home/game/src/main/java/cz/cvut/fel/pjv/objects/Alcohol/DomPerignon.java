package cz.cvut.fel.pjv.objects.alcohol;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.objects.GameObject;

import javax.imageio.ImageIO;

public class DomPerignon extends GameObject implements Alcohol {

    private static final float LUCK = 0.2f;

    public DomPerignon(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = "domperignon";
        this.collision = false;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/objects/dom_per.png"));
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
        }

        return newLuck;
    }

}
