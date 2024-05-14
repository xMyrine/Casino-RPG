package cz.cvut.fel.pjv;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.logging.Logger;

import cz.cvut.fel.pjv.minigames.assets.Card;
import cz.cvut.fel.pjv.objects.*;

/**
 * UI class is responsible for drawing the UI elements on the screen.
 * 
 * @Author Minh Tu Pham
 */
public class UI {

    private GamePanel gamePanel;
    private Graphics2D g;
    private Font defaultFont;
    private int xStatsTextOffset = 36;
    private int yStatsTextOffset = 33;
    private boolean announceMessage = false;
    private String message = "";
    private static final int ANNOUNCEMESSAGEDURATION = 120;
    private int displayMessageCounter = 0;
    private String dialogueText = "...";
    private static int command = 0;
    private static final Logger logger = Logger.getLogger(UI.class.getName());

    private BufferedImage chipImage;
    private BufferedImage titleImage;
    private BufferedImage crossedButton;
    private BufferedImage shopScreen;
    private BufferedImage craftingScreen;
    private BufferedImage chosenButton;
    private BufferedImage highButton;
    private BufferedImage lowButton;
    private BufferedImage rpsScreen;
    private BufferedImage rock;
    private BufferedImage paper;
    private BufferedImage scissors;
    private BufferedImage endScreen;
    private BufferedImage menuBG;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        defaultFont = new Font("Ink Free", Font.PLAIN, 25);
        Chip chip = new Chip();
        chipImage = chip.getImage();
        try {
            titleImage = ImageIO.read(getClass().getResourceAsStream("/icons/TitleImage.jpg"));
            crossedButton = ImageIO.read(getClass().getResourceAsStream("/buttons/cross_button.png"));
            shopScreen = ImageIO.read(getClass().getResourceAsStream("/screens/shop.png"));
            chosenButton = ImageIO.read(getClass().getResourceAsStream("/buttons/chosen_button.png"));
            craftingScreen = ImageIO.read(getClass().getResourceAsStream("/screens/Crafting.png"));
            highButton = ImageIO.read(getClass().getResourceAsStream("/buttons/high_button.png"));
            lowButton = ImageIO.read(getClass().getResourceAsStream("/buttons/low_button.png"));
            rpsScreen = ImageIO.read(getClass().getResourceAsStream("/screens/RPC_arena.jpg"));
            rock = ImageIO.read(getClass().getResourceAsStream("/objects/rock.png"));
            paper = ImageIO.read(getClass().getResourceAsStream("/objects/paper.png"));
            scissors = ImageIO.read(getClass().getResourceAsStream("/objects/scissors.png"));
            endScreen = ImageIO.read(getClass().getResourceAsStream("/screens/endgame.jpg"));
            menuBG = ImageIO.read(getClass().getResourceAsStream("/screens/menubg.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the message to be displayed on the screen
     */
    public void setAnnounceMessage(String message) {
        this.message = message;
        announceMessage = true;
    }

    public boolean getAnnounceMessage() {
        return announceMessage;
    }

    /**
     * Sets the dialogue text to be displayed on the scree
     * 
     * @param mod the number of commands
     */
    public static void increaseCommand(int mod) {
        command++;
        command = command % mod;
    }

    /**
     * Decreases the command
     * 
     * @param mod the number of commands
     */
    public static void decreaseCommand(int mod) {
        command--;
        if (command < 0) {
            command = mod - 1;
        }
    }

    public static int getCommand() {
        return command;
    }

    /**
     * Sets the command
     * Command is used to navigate through the UI
     * Command cannot be negative
     * 
     * @param code the command
     */
    public static void setCommand(int code) {
        if (command < 0) {
            command = 0;
        }
        command = code;
    }

    /**
     * Draws the UI elements on the screen
     */
    public void draw(Graphics2D g) {
        this.g = g;
        this.g.setFont(defaultFont);
        this.g.setColor(Color.WHITE);
        int state = gamePanel.getGameState();
        if (state == GamePanel.MENUSCREEN) {
            drawMenu();
        } else if (state == GamePanel.PAUSESCREEN) {
            drawPause();
        } else if (state == GamePanel.DIALOGUESCREEN) {
            drawDialogue();
        } else if (state == GamePanel.MINIGAMESCREEN) {
            drawMinigame();
            if (announceMessage) {
                announceMessage();
            }
        } else if (state == GamePanel.CONTROLSSCREEN) {
            drawControls();
        } else if (state == GamePanel.SHOPSCREEN) {
            drawShop();
        } else if (state == GamePanel.CRAFTSCREEN) {
            drawCrafting();
        } else if (state == GamePanel.ENDSCREEN) {
            drawENDSCREEN();
        } else {
            drawStats();
            if (gamePanel.player.isInventoryVisible()) {
                drawInventory();
            }
        }
    }

    /**
     * Draws the ending Screen
     */
    private void drawENDSCREEN() {
        g.drawImage(endScreen, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT - 24, null);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(40.0f));
        if (gamePanel.getLevelManager().getRps().end()) {
            g.drawString("Congratulations! You have finished the game!", GamePanel.TILE_SIZE * 2,
                    GamePanel.TILE_SIZE * 2);
        } else if (gamePanel.getLevelManager().getRps().getBusted()) {
            g.drawString("Unlucky, you lost! Maybe next time!", GamePanel.TILE_SIZE * 2,
                    GamePanel.TILE_SIZE * 2);
        }
        g.drawString("Total chip count:" + gamePanel.getPlayer().getChipCount(), GamePanel.TILE_SIZE * 2,
                GamePanel.TILE_SIZE * 4);
        g.drawString("Thank you for playing!", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 6);

        g.setFont(g.getFont().deriveFont(30.0f));
        g.drawString("Press ESC to exit", GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 10);
    }

    private void drawRPC() {
        g.drawImage(rpsScreen, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT - 24, null);
        if (command == 0) {
            g.drawImage(rock, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 3, 200,
                    229, null);
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 9, 80 * 2, 32 * 2, null);
        } else if (command == 1) {
            g.drawImage(paper, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 4, 200,
                    143, null);
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 6, GamePanel.TILE_SIZE * 9, 80 * 2, 32 * 2, null);
        } else if (command == 2) {
            g.drawImage(scissors, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 4, 200,
                    176, null);
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 12, GamePanel.TILE_SIZE * 9, 80 * 2, 32 * 2, null);
        }

        g.setColor(Color.RED);
        g.setFont(g.getFont().deriveFont(30.0f));
        g.drawString("Boss points: " + gamePanel.levelManager.getRps().getBossPoints(), GamePanel.TILE_SIZE * 1,
                GamePanel.TILE_SIZE * 1);

        g.setColor(Color.GREEN);
        g.drawString("Player points: " + gamePanel.levelManager.getRps().getPlayerPoints(), GamePanel.TILE_SIZE * 10,
                GamePanel.TILE_SIZE * 5);

        if (gamePanel.getPlayer().getChipCount() < 1000) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
            g.setColor(Color.WHITE);
            g.drawString("You need at least 1000 chips to play", GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 4);
        }
    }

    /**
     * Draw craftsman screen
     */
    private void drawCrafting() {
        g.drawImage(craftingScreen, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT - 24, null);
        int x = 72;
        int y = 24;
        int width = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE * 3;
        int height = GamePanel.TILE_SIZE;
        drawWindow(x, y, width, height);
        if (command == 0) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 7, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 5,
                    GamePanel.TILE_SIZE * 2, null);
            g.drawString("Craft Gun for 3 gun fragments", GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE + 10);
        } else if (command == 1) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 12 + 24, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 3,
                    GamePanel.TILE_SIZE * 2, null);
            g.drawString("Craft Cigar for 3 cigar fragments", GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE + 10);
        } else if (command == 2) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 12 + 24, GamePanel.TILE_SIZE * 7 + 24,
                    GamePanel.TILE_SIZE * 3,
                    GamePanel.TILE_SIZE * 2, null);
            g.drawString("Craft Cards for 3 card fragments", GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE + 10);
        }
    }

    /**
     * Draws the shop screen
     */
    private void drawShop() {
        g.drawImage(shopScreen, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT - 24, null);
        g.setFont(g.getFont().deriveFont(40.0f));
        int x = 72;
        int y = GamePanel.TILE_SIZE * 6;
        int width = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE * 3;
        int height = GamePanel.TILE_SIZE * 2;
        drawWindow(x, y, width, height);
        if (command == 0) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 1 + 24, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 4,
                    GamePanel.TILE_SIZE * 2, null);
            g.drawString("Buy 5 Luck for 25 chips", GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 7);
        } else if (command == 1) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 7, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 5,
                    GamePanel.TILE_SIZE * 2, null);
            g.drawString("Buy 10 chips for 1 luck", GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 7);
        } else if (command == 2) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 12 + 24, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 3,
                    GamePanel.TILE_SIZE * 2, null);
            g.drawString("Jesus take the wheel", GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 7);
        }
    }

    /**
     * Draws the controls screen
     */
    public void drawControls() {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(20.0f));
        g.drawString("Press ESC to resume", 0, GamePanel.TILE_SIZE);
        g.drawString("W/A/S/D to move", 0, GamePanel.TILE_SIZE * 2);
        g.drawString("E to interact", 0, GamePanel.TILE_SIZE * 3);
        g.drawString("Enter to confirm", 0, GamePanel.TILE_SIZE * 4);
        g.drawString("Press Q/E to increase or decrease your bet", 0, GamePanel.TILE_SIZE * 5);
        g.drawString("W/S to navigate around buttons during minigame", 0, GamePanel.TILE_SIZE * 6);
        g.drawString("Press I to toggle inventory", 0, GamePanel.TILE_SIZE * 7);
        g.drawString("Press F1 to mute the sound", 0, GamePanel.TILE_SIZE * 8);
    }

    /**
     * Draws a window with a border
     */
    public void drawInventory() {
        Inventory inventory = gamePanel.player.getInventory();
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        g.drawImage(inventory.getInventoryImage(), GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 2,
                GamePanel.TILE_SIZE * 8, GamePanel.TILE_SIZE * 8, null);
        for (int i = 0; i < inventory.getInventoryItems().length; i++) {
            if (inventory.getInventoryItem(i) == null) {
                break;
            }
            if (gamePanel.player.isSpecialItemInInventory(i)) {
                g.drawImage(inventory.getInventoryItem(i), GamePanel.TILE_SIZE * 4 + 30,
                        (GamePanel.TILE_SIZE + 13) * (2 + i),
                        GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30.0f));
        g.drawString("Luck:" + gamePanel.player.getPlayerLuck() * 100 + "%", GamePanel.TILE_SIZE * 8,
                GamePanel.TILE_SIZE * 3);
        g.drawString("Chips:" + gamePanel.player.getChipCount(), GamePanel.TILE_SIZE * 8, GamePanel.TILE_SIZE * 4);
        g.drawString("Speed:" + gamePanel.player.getSpeed(), GamePanel.TILE_SIZE * 8, GamePanel.TILE_SIZE * 5);
        g.drawString("" + gamePanel.player.getSpecialItemsFragmentCount(0), GamePanel.TILE_SIZE * 7,
                GamePanel.TILE_SIZE * 9);
        g.drawString("" + gamePanel.player.getSpecialItemsFragmentCount(1), GamePanel.TILE_SIZE * 9,
                GamePanel.TILE_SIZE * 9);
        g.drawString("" + gamePanel.player.getSpecialItemsFragmentCount(2), GamePanel.TILE_SIZE * 10 + 10,
                GamePanel.TILE_SIZE * 9);
    }

    /**
     * Draws a Pokermon minigame
     */
    private void drawPokermon() {

        g.drawImage(gamePanel.levelManager.getPokermon().getScreenImage(), 0, 0, GamePanel.SCREEN_WIDTH,
                GamePanel.SCREEN_HEIGHT - 36,
                null);
        if (gamePanel.levelManager.getPokermon().getMode() == 1) {
            g.drawImage(gamePanel.levelManager.getPokermon().getAttackButtons(), 0, 0, GamePanel.SCREEN_WIDTH,
                    GamePanel.SCREEN_HEIGHT - 36,
                    null);
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRoundRect(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, 300, 100, 30, 30);
            g.setColor(Color.WHITE);
            g.drawRoundRect(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, 300, 100, 30, 30);
            if (command == 0) {
                g.drawString("Heals player for +2", GamePanel.TILE_SIZE + 24, GamePanel.TILE_SIZE * 4);
            }
            if (command == 1) {
                g.drawString("Deals damage to enemy", GamePanel.TILE_SIZE + 24, GamePanel.TILE_SIZE * 4);
            }
            if (command == 2) {
                g.drawString("Increases you damage +1", GamePanel.TILE_SIZE + 24, GamePanel.TILE_SIZE * 4);
            }
        }

        if (command == 0) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 4,
                    GamePanel.TILE_SIZE * 2, null);
        }
        if (command == 1) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 6, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 4,
                    GamePanel.TILE_SIZE * 2, null);
        }
        if (command == 2) {
            g.drawImage(chosenButton, GamePanel.TILE_SIZE * 11, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 4,
                    GamePanel.TILE_SIZE * 2, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(30.0f));
        g.drawString(String.format("%d", gamePanel.levelManager.getPokermon().getPlayerAttack()),
                GamePanel.TILE_SIZE * 15 + 10,
                GamePanel.TILE_SIZE * 6 + 30);

        g.drawString(String.format("%d", gamePanel.levelManager.getPokermon().getEnemyAttack()),
                GamePanel.TILE_SIZE * 4,
                GamePanel.TILE_SIZE);

        for (int i = 0; i < gamePanel.levelManager.getPokermon().getEnemyHealth(); i++) {
            g.setColor(Color.RED);
            g.fillRect(16 + (i * 21), GamePanel.TILE_SIZE * 1 + 10, 14, 14);
            g.setColor(Color.BLACK);
            g.drawRect(16 + (i * 21), GamePanel.TILE_SIZE * 1 + 10, 14, 14);
        }

        for (int i = 0; i < gamePanel.levelManager.getPokermon().getPlayerHealth(); i++) {
            g.setColor(Color.GREEN);
            g.fillRect(GamePanel.TILE_SIZE * 11 + 16 + (i * 21), GamePanel.TILE_SIZE * 7, 14, 14);
            g.setColor(Color.BLACK);
            g.drawRect(GamePanel.TILE_SIZE * 11 + 16 + (i * 21), GamePanel.TILE_SIZE * 7, 14, 14);
        }

        if (gamePanel.levelManager.getPokermon().getMode() == 2) {
            g.drawImage(gamePanel.levelManager.getPokermon().getShootButton(), 0, 0, GamePanel.SCREEN_WIDTH,
                    GamePanel.SCREEN_HEIGHT - 36,
                    null);
            g.setColor(Color.WHITE);
            g.drawString("PRESS (W) TO SHOOT", GamePanel.TILE_SIZE * 4, GamePanel.TILE_SIZE * 4);
        }
    }

    /**
     * draws dice minigame
     */
    private void drawDices() {
        g.setColor(new Color(123, 157, 134));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

        g.setFont(defaultFont.deriveFont(50.0f));
        g.setColor(Color.WHITE);
        g.fillRect(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 3);
        g.drawRect(GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3,
                GamePanel.TILE_SIZE * 3);

        g.setColor(Color.BLACK);
        g.fillRect(GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3,
                GamePanel.TILE_SIZE * 3);
        g.drawRect(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 3);
        g.drawString(String.valueOf(gamePanel.levelManager.getDices().getRolledNumber1()), GamePanel.TILE_SIZE * 2,
                GamePanel.TILE_SIZE * 3);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(gamePanel.levelManager.getDices().getRolledNumber2()), GamePanel.TILE_SIZE * 12,
                GamePanel.TILE_SIZE * 3);

        g.drawImage(highButton, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 9, 80 * 3,
                32 * 3, null);

        g.drawImage(lowButton, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 9, 80 * 3, 32 * 3, null);

        g.drawString("Your chips: ", GamePanel.TILE_SIZE * 6 - 24, GamePanel.TILE_SIZE * 1);
        g.drawString(String.valueOf(gamePanel.getPlayer().getChipCount()), GamePanel.TILE_SIZE * 6,
                GamePanel.TILE_SIZE * 2);

        g.drawString("Your bet: ", GamePanel.TILE_SIZE * 6 - 24, GamePanel.TILE_SIZE * 3);
        g.drawString(String.valueOf(gamePanel.levelManager.getDices().getBet()), GamePanel.TILE_SIZE * 6,
                GamePanel.TILE_SIZE * 4);

        if (command == 0) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 9, 80 * 3, 32 * 3, null);
        } else if (command == 1) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 9, 80 * 3, 32 * 3, null);
        }
    }

    /**
     * Draws Blackjack minigame
     */
    private void drawBlackjack() {
        g.setColor(new Color(123, 157, 134));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

        g.drawImage(gamePanel.levelManager.getBlackjack().getHitButton(), GamePanel.TILE_SIZE * 3,
                GamePanel.TILE_SIZE * 9,
                GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 2, null);

        g.drawImage(gamePanel.levelManager.getBlackjack().getStandButton(), GamePanel.TILE_SIZE * 8,
                GamePanel.TILE_SIZE * 9,
                GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 2, null);

        if (command == 0 && gamePanel.levelManager.getBlackjack().getHitEnabled()) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 3 + 10, GamePanel.TILE_SIZE * 9 + 10, 40, 40, null);
        } else if (command == 1 && gamePanel.levelManager.getBlackjack().getStandEnabled()) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 8 + 10, GamePanel.TILE_SIZE * 9 + 10, 40, 40, null);
        }

        // crossing out the hit button if it is not enabled
        if (!gamePanel.levelManager.getBlackjack().getHitEnabled()) {
            g.drawImage(crossedButton, GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 5,
                    GamePanel.TILE_SIZE * 2, null);
        }
        // crossing out the stand button if it is not enabled
        if (!gamePanel.levelManager.getBlackjack().getStandEnabled()) {
            g.drawImage(crossedButton, GamePanel.TILE_SIZE * 8, GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 5,
                    GamePanel.TILE_SIZE * 2, null);
        }
        if (gamePanel.levelManager.getBlackjack().getStandEnabled()) {
            g.drawImage(gamePanel.levelManager.getBlackjack().getCardBack(), GamePanel.TILE_SIZE * 1, 24,
                    Card.CARD_WIDTH, Card.CARD_HEIGHT, null);
        } else {
            try {
                BufferedImage img = ImageIO.read(
                        getClass()
                                .getResourceAsStream(
                                        gamePanel.levelManager.getBlackjack().getHiddenCard().getImagePath()));
                g.drawImage(img, GamePanel.TILE_SIZE * 1, 24, Card.CARD_WIDTH, Card.CARD_HEIGHT, null);
            } catch (Exception e) {
                logger.warning("Error loading Card image");
            }
        }

        try {
            // Dealers Hand
            for (int i = 0; i < gamePanel.levelManager.getBlackjack().getDealerHand().size(); i++) {
                Card card = gamePanel.levelManager.getBlackjack().getDealerHand().get(i);
                BufferedImage img = ImageIO.read(getClass().getResourceAsStream(card.getImagePath()));
                g.drawImage(img, ((Card.CARD_WIDTH) * (i + 1)) + GamePanel.TILE_SIZE, 24, Card.CARD_WIDTH,
                        Card.CARD_HEIGHT,
                        null);
            }

            // Players Hand
            for (int i = 0; i < gamePanel.levelManager.getBlackjack().getPlayerHand().size(); i++) {
                Card card = gamePanel.levelManager.getBlackjack().getPlayerHand().get(i);
                BufferedImage img = ImageIO.read(getClass().getResourceAsStream(card.getImagePath()));
                g.drawImage(img, ((Card.CARD_WIDTH) * (i)) + GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 5,
                        Card.CARD_WIDTH, Card.CARD_HEIGHT, null);
            }
        } catch (Exception e) {
            logger.warning("Error loading Card image");
        }

        if (!gamePanel.levelManager.getBlackjack().getStandEnabled()
                && !gamePanel.levelManager.getBlackjack().getHitEnabled()) {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 50.0f));
            g.drawString("Press R to restart the game", GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 8);
        }

    }

    /**
     * Draws the Roulette minigame
     */
    private void drawRoulette() {
        // Background
        g.setColor(new Color(123, 157, 134));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        // Squares
        g.setColor(Color.RED);
        g.fillRoundRect(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, 60, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRoundRect(GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, 60, 30, 30);

        g.fillRoundRect(GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, 60, 30, 30);
        g.setColor(Color.RED);
        g.drawRoundRect(GamePanel.TILE_SIZE * 5, GamePanel.TILE_SIZE * 1, GamePanel.TILE_SIZE * 3, 60, 30, 30);
        if (command == 0) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 1 + 10, GamePanel.TILE_SIZE * 2, 40, 40, null);
        } else if (command == 1) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 5 + 10, GamePanel.TILE_SIZE * 2, 40, 40, null);
        }

        // Text
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.drawString("BLACK", GamePanel.TILE_SIZE * 5 + 10, GamePanel.TILE_SIZE * 2);
        g.setColor(Color.BLACK);
        g.drawString("RED", GamePanel.TILE_SIZE * 1 + 30, GamePanel.TILE_SIZE * 2);

        // Drawing 0
        g.setColor(Color.GREEN);
        g.fillOval(GamePanel.TILE_SIZE * 3 + 5, GamePanel.TILE_SIZE * 3, 150, 75);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 50.0f));
        g.drawString("0", GamePanel.TILE_SIZE * 4 + 17, GamePanel.TILE_SIZE * 4);
        if (command == 2) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 3 + 10, GamePanel.TILE_SIZE * 4, 40, 40, null);
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
                g.fillOval(GamePanel.TILE_SIZE * x + 25, GamePanel.TILE_SIZE * y - 13, 50, 37);
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(number), GamePanel.TILE_SIZE * x + 30, GamePanel.TILE_SIZE * y + 15);
                number++;

                if (command == number + 1) {
                    g.drawImage(chipImage, GamePanel.TILE_SIZE * x + 10, GamePanel.TILE_SIZE * y - 10, 40, 40, null);
                }

            }
        }

        // Drawing Bet
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 70.0f));
        g.drawString("BET:" + gamePanel.levelManager.getRoulette().getBet(), GamePanel.TILE_SIZE * 10,
                GamePanel.TILE_SIZE * 1 + 24);

        // Winning number

        if (gamePanel.levelManager.getRoulette().getWinningNumber() == 0) {
            g.setColor(Color.GREEN);
        } else if (gamePanel.levelManager.getRoulette().getWinningNumber() % 2 == 0) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRoundRect(GamePanel.TILE_SIZE * 10 - 18, GamePanel.TILE_SIZE * 3, 250, 300, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRoundRect(GamePanel.TILE_SIZE * 10 - 18, GamePanel.TILE_SIZE * 3, 250, 300, 30, 30);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 150.0f));
        g.drawString("" + gamePanel.levelManager.getRoulette().getWinningNumber(), GamePanel.TILE_SIZE * 10,
                GamePanel.TILE_SIZE * 8);

        // Current Balance
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.drawString("BALANCE: " + gamePanel.player.getChipCount(), GamePanel.TILE_SIZE * 10 - 20,
                GamePanel.TILE_SIZE * 10 + 24);
        g.drawImage(chipImage, GamePanel.TILE_SIZE * 10 - 70, GamePanel.TILE_SIZE * 10 - 10, 50, 50, null);

        if (!gamePanel.levelManager.getRoulette().isValidBet()) {
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 80.0f));
            g.drawString("Not enough chips", GamePanel.TILE_SIZE * 1,
                    GamePanel.TILE_SIZE * 5);
            g.setColor(Color.BLACK);
            g.drawString("Not enough chips", GamePanel.TILE_SIZE * 1 + 5,
                    GamePanel.TILE_SIZE * 5 + 5);
        }

    }

    /**
     * Draws minigame depending on the level and npc
     */
    private void drawMinigame() {
        if (LevelManager.getLevelNumber() >= 1
                && gamePanel.player.getNpcIndex() == 1) {
            drawRoulette();
        } else if (LevelManager.getLevelNumber() >= 2 && gamePanel.player.getNpcIndex() == 2) {
            drawBlackjack();
        } else if (LevelManager.getLevelNumber() >= 3 && gamePanel.player.getNpcIndex() == 5) {
            drawPokermon();
        } else if (LevelManager.getLevelNumber() >= 3 && gamePanel.player.getNpcIndex() == 7) {
            drawDices();
        } else if (LevelManager.getLevelNumber() >= 4 && gamePanel.player.getNpcIndex() == 8) {
            drawRPC();
        } else {
            drawENDSCREEN();
        }
    }

    /**
     * Draws stats on the screen
     */
    private void drawStats() {
        g.setFont(defaultFont);
        g.setColor(Color.WHITE);
        g.drawImage(chipImage, 0, 0, 40, 40, null);
        g.drawString("" + gamePanel.player.getChipCount(), xStatsTextOffset, yStatsTextOffset);
        if (gamePanel.getKeyHandler().getInteract()) {
            g.drawString("Interact is ON", xStatsTextOffset, yStatsTextOffset + 40);
        }
        if (announceMessage) {
            announceMessage();
        }
    }

    /**
     * Draws a message on the screen
     */
    private void announceMessage() {
        g.setColor(Color.BLUE);
        g.setFont(g.getFont().deriveFont(35.0f));
        g.drawString(message, GamePanel.TILE_SIZE, GamePanel.SCREEN_HEIGHT / 2);

        displayMessageCounter++;

        if (displayMessageCounter > ANNOUNCEMESSAGEDURATION) {
            announceMessage = false;
            displayMessageCounter = 0;
            message = "";
        }
    }

    /**
     * Draws the main menu
     */
    private void drawMenu() {
        g.drawImage(menuBG, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        String title = "No Intern, No home";
        g.setFont(g.getFont().deriveFont(Font.BOLD, 65.0f));
        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int x = (GamePanel.SCREEN_WIDTH - titleWidth) / 2;

        // Shadow
        g.setColor(Color.BLACK);
        g.drawString(title, x + 4, GamePanel.TILE_SIZE * 2 + 4);

        g.setColor(Color.decode("#DC143C"));
        g.drawString(title, x, GamePanel.TILE_SIZE * 2);

        g.drawImage(titleImage, GamePanel.TILE_SIZE * 2, GamePanel.SCREEN_HEIGHT / 2 - 100,
                GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 3, null);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 70.0f));
        title = "PLAY";
        g.setColor(Color.WHITE);
        g.drawString(title, GamePanel.TILE_SIZE * 8 - 100, GamePanel.TILE_SIZE * 4);
        if (command == 0) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 8 - 150, GamePanel.TILE_SIZE * 4 - 40, 50, 50, null);
        }

        title = "EXIT";
        g.setFont(g.getFont().deriveFont(Font.BOLD, 40.0f));
        g.setColor(Color.WHITE);
        g.drawString(title, GamePanel.TILE_SIZE * 8 - 65, GamePanel.TILE_SIZE * 5);
        if (command == 1) {
            g.drawImage(chipImage, GamePanel.TILE_SIZE * 8 - 110, GamePanel.TILE_SIZE * 5 - 25, 30, 30, null);
        }

        g.setFont(g.getFont().deriveFont(Font.BOLD, 20.0f));
        g.setColor(Color.GRAY);
        g.drawString("Disclaimer: Gambling can be addictive. Please play responsibly.", GamePanel.TILE_SIZE * 2,
                GamePanel.TILE_SIZE * 11);
    }

    /**
     * Draws the pause screen
     */
    private void drawPause() {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        g.setFont(g.getFont().deriveFont(80.0f));
        g.setColor(Color.WHITE);
        g.drawString("GAME PAUSED", GamePanel.TILE_SIZE * 2 + 30, GamePanel.TILE_SIZE * 3);
        g.setFont(g.getFont().deriveFont(20.0f));
        g.drawString("Press ESC to resume", 0, GamePanel.TILE_SIZE);

        g.setFont(g.getFont().deriveFont(50.0f));
        if (command == 0) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Main Menu", GamePanel.TILE_SIZE * 5 + 24, GamePanel.TILE_SIZE * 4 + 20);
        if (command == 1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Controls", GamePanel.TILE_SIZE * 7, GamePanel.TILE_SIZE * 5 + 20);

        if (command == 2) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("Exit", GamePanel.TILE_SIZE * 8 + 24, GamePanel.TILE_SIZE * 6 + 20);

    }

    /**
     * Draws the dialogue screen
     */
    private void drawDialogue() {
        g.setFont(g.getFont().deriveFont(23.0f));
        int x = 72;
        int y = GamePanel.TILE_SIZE * 8;
        int width = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE * 3;
        int height = GamePanel.TILE_SIZE * 3;
        drawWindow(x, y, width, height);

        x += GamePanel.TILE_SIZE;
        y += GamePanel.TILE_SIZE;
        for (String line : dialogueText.split("\n")) {
            g.drawString(line, x, y);
            y += 24;
        }
    }

    /**
     * Draws a window with a border
     */
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
