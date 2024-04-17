package cz.cvut.fel.pjv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.objects.*;

public class UI {

    private GamePanel gamePanel;
    private Graphics2D g;
    Font defaultFont;
    private int xStatsTextOffset = 36;
    private int yStatsTextOffset = 33;
    private boolean announceMessage = false;
    public String message = "";
    private static final int announceMessageDuration = 120;
    private int displayMessageCounter = 0;

    BufferedImage chipImage;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        defaultFont = new Font("Ink Free", Font.PLAIN, 25);
        Chip chip = new Chip();
        chipImage = chip.image;
    }

    public void setAnnounceMessage(String message) {
        this.message = message;
        announceMessage = true;
    }

    public boolean getAnnounceMessage() {
        return announceMessage;
    }

    /*
     * Draws the UI elements on the screen
     */

    public void draw(Graphics2D g) {
        g.setFont(defaultFont);
        g.setColor(Color.WHITE);
        g.drawImage(chipImage, 0, 0, 40, 40, null);
        g.drawString("" + gamePanel.player.getChipCount(), xStatsTextOffset, yStatsTextOffset);
        g.drawString("Luck: " + gamePanel.player.getPlayerLuck() * 100 + "%", 0, yStatsTextOffset * 2);

        if (announceMessage) {
            g.setFont(g.getFont().deriveFont(50.0f));
            g.drawString(message, gamePanel.screenWidth / 2 - 100, gamePanel.screenHeight / 2);

            displayMessageCounter++;

            if (displayMessageCounter > announceMessageDuration) {
                announceMessage = false;
                displayMessageCounter = 0;
                message = "";
            }
        }

        this.g = g;

        this.g.setFont(defaultFont);
        this.g.setColor(Color.WHITE);
        if (gamePanel.gameState == gamePanel.menuScreen) {
            // TODO drawMenu();
        } else if (gamePanel.gameState == gamePanel.pauseScreen) {
            drawPause();
        }
    }

    private void drawPause() {
        g.setFont(g.getFont().deriveFont(50.0f));
        g.drawString("PAUSED", gamePanel.screenWidth / 2 - 100, gamePanel.screenHeight / 2);
    }
}
