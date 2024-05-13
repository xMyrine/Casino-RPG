package cz.cvut.fel.pjv;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Toolbox {

    private Toolbox() {
        throw new UnsupportedOperationException("Toolbox is a utility class and should not be instantiated");
    }

    public static BufferedImage scaleImage(BufferedImage image, int x, int y) {
        BufferedImage img = new BufferedImage(x, y, image.getType());
        Graphics g = img.createGraphics();
        g.drawImage(image, 0, 0, x, y, null);
        g.dispose();

        return img;
    }
}
