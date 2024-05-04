package cz.cvut.fel.pjv;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.entity.Player;

public class Inventory {
    public BufferedImage InventoryImage;
    public BufferedImage[] inventoryItems;

    public Inventory(Player player) {
        try {
            InventoryImage = Toolbox.scaleImage(
                    ImageIO.read(getClass().getResourceAsStream("/inventory/inventory.png")), 100,
                    100);
            inventoryItems = new BufferedImage[4];
            inventoryItems[2] = Toolbox.scaleImage(
                    ImageIO.read(getClass().getResourceAsStream("/inventory/card1.png")), 16, 16);
            inventoryItems[0] = Toolbox.scaleImage(
                    ImageIO.read(getClass().getResourceAsStream("/inventory/ciggarette.png")),
                    16, 16);
            inventoryItems[1] = Toolbox.scaleImage(
                    ImageIO.read(getClass().getResourceAsStream("/inventory/gun.png")),
                    16, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
