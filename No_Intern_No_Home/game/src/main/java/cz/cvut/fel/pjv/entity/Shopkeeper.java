package cz.cvut.fel.pjv.entity;

import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Constants;

/**
 * Shopkeeper is an NPC that the player can interact with in the first level.
 * The player can increase their luck, receive chips or have random stats
 * changes.
 * Shopkeeper also opens the shop screen.
 * 
 * @Author Minh Tu Pham
 */
public class Shopkeeper extends Entity {

    private GamePanel gamePanel;
    private static Random random = new Random();

    public Shopkeeper(GamePanel panel) {
        this.name = "Shopkeeper";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = 10;
        this.worldY = 10;

        getNPCImage();
    }

    public Shopkeeper(GamePanel panel, int x, int y) {
        this.name = "Shopkeeper";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = x;
        this.worldY = y;

        getNPCImage();
    }

    /**
     * Assigns images to the NPC.
     */
    public void getNPCImage() {
        up1 = assignImage("/npc/shop_up_1");
        up2 = assignImage("/npc/shop_up_2");
        down1 = assignImage("/npc/shop_down_1");
        down2 = assignImage("/npc/shop_down_2");
        left1 = assignImage("/npc/shop_left_1");
        left2 = assignImage("/npc/shop_left_2");
        right1 = assignImage("/npc/shop_right_1");
        right2 = assignImage("/npc/shop_right_2");

    }

    /**
     * Shopkeeper has 4 directions and 2 sprites for each direction.
     * Draws the NPC on the screen relative to the player's position.
     * 
     * @param g Graphics2D object
     */
    @Override
    public void draw(Graphics2D g) {
        BufferedImage image = directionToImageMap.getOrDefault(direction, () -> null).get();
        int screenX = worldX - gamePanel.getPlayer().worldX + GamePanel.SCREEN_WIDTH - 8 * GamePanel.TILE_SIZE - 24;

        int screenY = worldY - gamePanel.getPlayer().worldY + GamePanel.SCREEN_HEIGHT - 6 * GamePanel.TILE_SIZE - 24;
        // draw only objects that are in the screen
        if (worldX + GamePanel.TILE_SIZE > gamePanel.getPlayer().worldX - GamePanel.SCREEN_WIDTH
                && worldX - GamePanel.TILE_SIZE < gamePanel.getPlayer().worldX + GamePanel.SCREEN_WIDTH
                && worldY + GamePanel.TILE_SIZE > gamePanel.getPlayer().worldY - GamePanel.SCREEN_HEIGHT
                && worldY - GamePanel.TILE_SIZE < gamePanel.getPlayer().worldY + GamePanel.SCREEN_HEIGHT) {
            g.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        }
    }

    /**
     * Instead of dialogue Shopkeeper opens the shop screen.
     */
    @Override
    public void talk() {
        gamePanel.changeGameState(6);
    }

    /**
     * Shopkeeper moves randomly.
     */
    @Override
    public void move() {
        int i;
        if (actionCounter < ACTION_DELAY) {
            actionCounter++;

        } else {
            i = Entity.random.nextInt(4);

            if (i == 0) {
                direction = Constants.UP;
                worldY -= speed;
            } else if (i == 1) {
                direction = Constants.DOWN;
                worldY += speed;
            } else if (i == 2) {
                direction = Constants.LEFT;
                worldX -= speed;
            } else if (i == 3) {
                direction = Constants.RIGHT;
                worldX += speed;
            }
            actionCounter = 0;

        }

    }

    /**
     * Increases the player's luck by 4% at the cost of 25 chips.
     */
    private void increasePlayersLuck() {
        if (gamePanel.getPlayer().getChipCount() > 25) {
            gamePanel.getPlayer().setPlayerLuck(gamePanel.getPlayer().getPlayerLuck() + 0.05f);
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - 25);
            gamePanel.getGameUI().setAnnounceMessage("You have increased your luck by 4%!");
        } else {
            gamePanel.getGameUI().setAnnounceMessage("You don't have enough chips!");
        }
    }

    /**
     * Increases the player's chips by 10 at the cost of 1% of their luck.
     */
    private void increasePlayersChips() {
        gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 10);
        gamePanel.getPlayer().setPlayerLuck(gamePanel.getPlayer().getPlayerLuck() - 0.01f);
        gamePanel.getGameUI().setAnnounceMessage("You have received 5 chips!");
    }

    /**
     * Randomly increases or decreases the player's luck and chips.
     */
    private void randomPlayerStats() {
        while (true) {
            if (random.nextFloat() < gamePanel.getPlayer().getPlayerLuck()) {
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 10);
                gamePanel.getPlayer().setPlayerLuck(gamePanel.getPlayer().getPlayerLuck() + 0.01f);
                gamePanel.getGameUI().setAnnounceMessage("You have received 10 chips!");
            } else {
                gamePanel.getPlayer().setPlayerLuck(gamePanel.getPlayer().getPlayerLuck() - 0.01f);
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - 10);
                gamePanel.getGameUI().setAnnounceMessage("You have lost 5% of your luck!");
            }
            if (random.nextFloat() < gamePanel.getPlayer().getPlayerLuck()) {
                break;
            }
        }
    }

    /**
     * Executes the command given by the player.
     * Depending on the command, the player's stats are changed.
     * Either (1) increase luck, (2) increase chips or (3) random stats change.
     * 
     * @param command
     */
    public void executeCommand(int command) {
        switch (command) {
            case 0:
                increasePlayersLuck();
                break;
            case 1:
                increasePlayersChips();
                break;
            case 2:
                randomPlayerStats();
                break;
            default:
                break;
        }
    }
}
