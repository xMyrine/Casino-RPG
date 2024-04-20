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
    private String dialogueText = "...";

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
        } else if (gamePanel.gameState == gamePanel.dialogueScreen) {
            drawDialogue();
        }
    }

    private void drawPause() {
        g.setFont(g.getFont().deriveFont(50.0f));
        g.drawString("PAUSED", gamePanel.screenWidth / 2 - 100, gamePanel.screenHeight / 2);
    }

    private void drawDialogue() {
        g.setFont(g.getFont().deriveFont(23.0f));
        int x = 72;
        int y = gamePanel.tileSize * 8;
        int width = gamePanel.screenWidth - gamePanel.tileSize * 3;
        int height = gamePanel.tileSize * 3;
        drawWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        for (String line : dialogueText.split("\n")) {
            g.drawString(line, x, y);
            y += 24;
        }
        // g.drawString(dialogueText, x, y);
    }

    private void drawWindow(int x, int y, int width, int height) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRoundRect(x, y, width, height, 20, 20);
        g.setColor(Color.WHITE);
        int thickness = 3; // Change this to change the border thickness
        g.setColor(Color.WHITE);
        for (int i = 0; i < thickness; i++) {
            g.drawRoundRect(x + i, y + i, width - 2 * i, height - 2 * i, 20, 20);
        }
    }

    public void setDialogue(String dialogues) {
        dialogueText = dialogues;
    }
}
