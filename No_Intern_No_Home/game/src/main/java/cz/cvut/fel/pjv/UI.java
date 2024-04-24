package cz.cvut.fel.pjv;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.minigames.assets.Card;
import cz.cvut.fel.pjv.objects.*;

public class UI {

    private GamePanel gamePanel;
    private Graphics2D g;
    Font defaultFont;
    private int xStatsTextOffset = 36;
    private int yStatsTextOffset = 33;
    private boolean announceMessage = false;
    public String message = "";
    private static final int ANNOUNCEMESSAGEDURATION = 120;
    private int displayMessageCounter = 0;
    private String dialogueText = "...";
    public static int command = 0;

    BufferedImage chipImage;
    BufferedImage titleImage;
    BufferedImage crossedButton;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        defaultFont = new Font("Ink Free", Font.PLAIN, 25);
        Chip chip = new Chip();
        chipImage = chip.image;
        try {
            titleImage = ImageIO.read(getClass().getResourceAsStream("/icons/TitleImage.jpg"));
            crossedButton = ImageIO.read(getClass().getResourceAsStream("/buttons/cross_button.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading image");
        }
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

        this.g = g;

        this.g.setFont(defaultFont);
        this.g.setColor(Color.WHITE);
        if (gamePanel.gameState == GamePanel.MENUSCREEN) {
            drawMenu();
        } else if (gamePanel.gameState == GamePanel.PAUSESCREEN) {
            drawPause();
        } else if (gamePanel.gameState == GamePanel.DIALOGUESCREEN) {
            drawDialogue();
        } else if (gamePanel.gameState == GamePanel.MINIGAMESCREEN) {
            drawMinigame();
            if (announceMessage) {
                announceMessage();
            }
        } else {
            drawStats();

        }
    }

    private void drawBlackjack() {
        g.setColor(new Color(123, 157, 134));
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g.drawImage(gamePanel.levelManager.blackjack.hitButton, gamePanel.tileSize * 3, gamePanel.tileSize * 9,
                gamePanel.tileSize * 5, gamePanel.tileSize * 2, null);

        g.drawImage(gamePanel.levelManager.blackjack.standButton, gamePanel.tileSize * 8, gamePanel.tileSize * 9,
                gamePanel.tileSize * 5, gamePanel.tileSize * 2, null);

        if (command == 0 && gamePanel.levelManager.blackjack.getHitEnabled()) {
            g.drawImage(chipImage, gamePanel.tileSize * 3 + 10, gamePanel.tileSize * 9 + 10, 40, 40, null);
        } else if (command == 1 && gamePanel.levelManager.blackjack.getStandEnabled()) {
            g.drawImage(chipImage, gamePanel.tileSize * 8 + 10, gamePanel.tileSize * 9 + 10, 40, 40, null);
        }

        // crossing out the hit button if it is not enabled
        if (!gamePanel.levelManager.blackjack.getHitEnabled()) {
            g.drawImage(crossedButton, gamePanel.tileSize * 3, gamePanel.tileSize * 9, gamePanel.tileSize * 5,
                    gamePanel.tileSize * 2, null);
        }
        // crossing out the stand button if it is not enabled
        if (!gamePanel.levelManager.blackjack.getStandEnabled()) {
            g.drawImage(crossedButton, gamePanel.tileSize * 8, gamePanel.tileSize * 9, gamePanel.tileSize * 5,
                    gamePanel.tileSize * 2, null);
        }
        if (gamePanel.levelManager.blackjack.getStandEnabled()) {
            g.drawImage(gamePanel.levelManager.blackjack.cardBack, gamePanel.tileSize * 1, 24,
                    Card.cardWidth, Card.cardHeight, null);
        } else {
            try {
                BufferedImage img = ImageIO.read(
                        getClass().getResourceAsStream(gamePanel.levelManager.blackjack.hiddenCard.getImagePath()));
                g.drawImage(img, gamePanel.tileSize * 1, 24, Card.cardWidth, Card.cardHeight, null);
            } catch (Exception e) {
                System.err.println("Error loading Card image");
            }
        }

        try {
            // Dealers Hand
            for (int i = 0; i < gamePanel.levelManager.blackjack.dealerHand.size(); i++) {
                Card card = gamePanel.levelManager.blackjack.dealerHand.get(i);
                BufferedImage img = ImageIO.read(getClass().getResourceAsStream(card.getImagePath()));
                g.drawImage(img, ((Card.cardWidth) * (i + 1)) + gamePanel.tileSize, 24, Card.cardWidth,
                        Card.cardHeight,
                        null);
            }

            // Players Hand
            for (int i = 0; i < gamePanel.levelManager.blackjack.playerHand.size(); i++) {
                Card card = gamePanel.levelManager.blackjack.playerHand.get(i);
                BufferedImage img = ImageIO.read(getClass().getResourceAsStream(card.getImagePath()));
                g.drawImage(img, ((Card.cardWidth) * (i)) + gamePanel.tileSize, gamePanel.tileSize * 5,
                        Card.cardWidth, Card.cardHeight, null);
            }
        } catch (Exception e) {
            System.err.println("Error loading Card image");
        }

        if (!gamePanel.levelManager.blackjack.getStandEnabled() && !gamePanel.levelManager.blackjack.getHitEnabled()) {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 50.0f));
            g.drawString("Press R to restart the game", gamePanel.tileSize * 1, gamePanel.tileSize * 8);
        }

    }

    private void drawRoulette() {
        // Background
        g.setColor(new Color(123, 157, 134));
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        // Squares
        g.setColor(Color.RED);
        g.fillRoundRect(gamePanel.tileSize * 1, gamePanel.tileSize * 1, gamePanel.tileSize * 3, 60, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRoundRect(gamePanel.tileSize * 1, gamePanel.tileSize * 1, gamePanel.tileSize * 3, 60, 30, 30);

        g.fillRoundRect(gamePanel.tileSize * 5, gamePanel.tileSize * 1, gamePanel.tileSize * 3, 60, 30, 30);
        g.setColor(Color.RED);
        g.drawRoundRect(gamePanel.tileSize * 5, gamePanel.tileSize * 1, gamePanel.tileSize * 3, 60, 30, 30);
        if (command == 0) {
            g.drawImage(chipImage, gamePanel.tileSize * 1 + 10, gamePanel.tileSize * 2, 40, 40, null);
        } else if (command == 1) {
            g.drawImage(chipImage, gamePanel.tileSize * 5 + 10, gamePanel.tileSize * 2, 40, 40, null);
        }

        // Text
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.drawString("BLACK", gamePanel.tileSize * 5 + 10, gamePanel.tileSize * 2);
        g.setColor(Color.BLACK);
        g.drawString("RED", gamePanel.tileSize * 1 + 30, gamePanel.tileSize * 2);

        // Drawing 0
        g.setColor(Color.GREEN);
        g.fillOval(gamePanel.tileSize * 3 + 5, gamePanel.tileSize * 3, 150, 75);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 50.0f));
        g.drawString("0", gamePanel.tileSize * 4 + 17, gamePanel.tileSize * 4);
        if (command == 2) {
            g.drawImage(chipImage, gamePanel.tileSize * 3 + 10, gamePanel.tileSize * 4, 40, 40, null);
        }
        // Numbers
        g.setFont(g.getFont().deriveFont(Font.BOLD, 30.0f));
        g.setColor(Color.WHITE);

        int number = 1;
        for (int y = 5; y <= 10; y++) {
            for (int x = 1; x <= 6; x++) {
                if (number % 2 == 0) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillOval(gamePanel.tileSize * x + 25, gamePanel.tileSize * y - 13, 50, 37);
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(number), gamePanel.tileSize * x + 30, gamePanel.tileSize * y + 15);
                number++;

                if (command == number + 1) {
                    g.drawImage(chipImage, gamePanel.tileSize * x + 10, gamePanel.tileSize * y - 10, 40, 40, null);
                }

            }
        }

        // Drawing Bet
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 70.0f));
        g.drawString("BET:" + gamePanel.levelManager.roulette.getBet(), gamePanel.tileSize * 10,
                gamePanel.tileSize * 1 + 24);

        // Winning number

        if (gamePanel.levelManager.roulette.getWinningNumber() == 0) {
            g.setColor(Color.GREEN);
        } else if (gamePanel.levelManager.roulette.getWinningNumber() % 2 == 0) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRoundRect(gamePanel.tileSize * 10 - 18, gamePanel.tileSize * 3, 250, 300, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRoundRect(gamePanel.tileSize * 10 - 18, gamePanel.tileSize * 3, 250, 300, 30, 30);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 150.0f));
        g.drawString("" + gamePanel.levelManager.roulette.getWinningNumber(), gamePanel.tileSize * 10,
                gamePanel.tileSize * 8);

        // Current Balance
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.drawString("BALANCE: " + gamePanel.player.getChipCount(), gamePanel.tileSize * 10 - 20,
                gamePanel.tileSize * 10 + 24);
        g.drawImage(chipImage, gamePanel.tileSize * 10 - 70, gamePanel.tileSize * 10 - 10, 50, 50, null);

        if (!gamePanel.levelManager.roulette.validBet) {
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 80.0f));
            g.drawString("Not enough chips", gamePanel.tileSize * 1,
                    gamePanel.tileSize * 5);
            g.setColor(Color.BLACK);
            g.drawString("Not enough chips", gamePanel.tileSize * 1 + 5,
                    gamePanel.tileSize * 5 + 5);
        }

    }

    private void drawMinigame() {
        if (gamePanel.levelManager.getLevelNumber() >= 1
                && gamePanel.player.npcIndex == 1) {
            drawRoulette();
        } else if (gamePanel.levelManager.getLevelNumber() >= 2 && gamePanel.player.npcIndex == 2) {
            drawBlackjack();
        }
    }

    private void drawStats() {
        g.setFont(defaultFont);
        g.setColor(Color.WHITE);
        g.drawImage(chipImage, 0, 0, 40, 40, null);
        g.drawString("" + gamePanel.player.getChipCount(), xStatsTextOffset, yStatsTextOffset);
        g.drawString("Luck: " + gamePanel.player.getPlayerLuck() * 100 + "%", 0, yStatsTextOffset * 2);

        if (announceMessage) {
            announceMessage();
        }
    }

    private void announceMessage() {
        g.setColor(Color.YELLOW);
        g.setFont(g.getFont().deriveFont(50.0f));
        g.drawString(message, gamePanel.screenWidth / 2 - 100, gamePanel.screenHeight / 2);

        displayMessageCounter++;

        if (displayMessageCounter > ANNOUNCEMESSAGEDURATION) {
            announceMessage = false;
            displayMessageCounter = 0;
            message = "";
        }
    }

    private void drawMenu() {
        g.setColor(new Color(212, 175, 55));
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        String title = "PLEASE HELP";
        g.setFont(g.getFont().deriveFont(Font.BOLD, 100.0f));
        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int x = (gamePanel.screenWidth - titleWidth) / 2;

        // Shadow
        g.setColor(Color.BLACK);
        g.drawString(title, x + 4, gamePanel.tileSize * 2 + 4);

        g.setColor(Color.decode("#DC143C"));
        g.drawString(title, x, gamePanel.tileSize * 2);

        g.drawImage(titleImage, gamePanel.tileSize * 2, gamePanel.screenHeight / 2 - 100,
                gamePanel.tileSize * 3, gamePanel.tileSize * 3, null);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 70.0f));
        title = "PLAY";
        g.setColor(Color.WHITE);
        g.drawString(title, gamePanel.tileSize * 8 - 100, gamePanel.tileSize * 4);
        if (command == 0) {
            g.drawImage(chipImage, gamePanel.tileSize * 8 - 150, gamePanel.tileSize * 4 - 40, 50, 50, null);
        }

        title = "EXIT";
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.setColor(Color.WHITE);
        g.drawString(title, gamePanel.tileSize * 8 - 65, gamePanel.tileSize * 5);
        if (command == 1) {
            g.drawImage(chipImage, gamePanel.tileSize * 8 - 110, gamePanel.tileSize * 5 - 25, 30, 30, null);
        }

        title = "TEST";
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.setColor(Color.WHITE);
        g.drawString(title, gamePanel.tileSize * 8 - 65, gamePanel.tileSize * 6);
        if (command == 2) {
            g.drawImage(chipImage, gamePanel.tileSize * 8 - 110, gamePanel.tileSize * 6 - 25, 30, 30, null);
        }

        g.setFont(g.getFont().deriveFont(Font.BOLD, 20.0f));
        g.setColor(Color.BLACK);
        g.drawString("Disclaimer: Gambling can be addictive. Please play responsibly.", gamePanel.tileSize * 2,
                gamePanel.tileSize * 11);

    }

    private void drawPause() {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g.setFont(g.getFont().deriveFont(80.0f));
        g.setColor(Color.WHITE);
        g.drawString("GAME PAUSED", gamePanel.tileSize * 2 + 30, gamePanel.tileSize * 3);
        g.setFont(g.getFont().deriveFont(20.0f));
        g.drawString("Press ESC to resume", 0, gamePanel.tileSize);

        g.setFont(g.getFont().deriveFont(50.0f));
        if (command == 0) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Main Menu", gamePanel.tileSize * 5 + 24, gamePanel.tileSize * 4 + 20);
        if (command == 1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Exit", gamePanel.tileSize * 7, gamePanel.tileSize * 5 + 20);

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
