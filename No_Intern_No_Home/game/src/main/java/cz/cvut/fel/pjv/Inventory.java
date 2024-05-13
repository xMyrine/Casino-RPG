package cz.cvut.fel.pjv;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Inventory {
    private BufferedImage inventoryImage;
    private BufferedImage[] inventoryItems;

    public Inventory() {
        try {
            inventoryImage = Toolbox.scaleImage(
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

    public BufferedImage getInventoryImage() {
        return inventoryImage;
    }

    public BufferedImage[] getInventoryItems() {
        return inventoryItems;
    }

    public BufferedImage getInventoryItem(int i) {
        return inventoryItems[i];
    }
}
