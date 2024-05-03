package cz.cvut.fel.pjv.objects.Alcohol;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.objects.Object;

import javax.imageio.ImageIO;

public class DomPerignon extends Object implements Alcohol {

    private final float luck = 0.2f;

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
        newLuck += luck;
        sound.playMusic(2);
        if (newLuck > 1) {
            newLuck = 0.3f;
        }

        return newLuck;
    }

}
