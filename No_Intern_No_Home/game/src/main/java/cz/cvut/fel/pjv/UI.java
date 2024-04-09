package cz.cvut.fel.pjv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.objects.*;

public class UI {

    private GamePanel gamePanel;
    Font defaultFont;
    private int xStatsTextOffset = 36;
    private int yStatsTextOffset = 33;

    BufferedImage chipImage;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        defaultFont = new Font("Ink Free", Font.PLAIN, 25);
        Chip chip = new Chip();
        chipImage = chip.image;
    }

    public void draw(Graphics2D g) {
        g.setFont(defaultFont);
        g.setColor(Color.WHITE);
        g.drawImage(chipImage, 0, 0, 40, 40, null);
        g.drawString("" + gamePanel.player.getChipCount(), xStatsTextOffset, yStatsTextOffset);
        g.drawString("Luck: " + gamePanel.player.getPlayerLuck() * 100 + "%", 0, yStatsTextOffset * 2);
    }
}
