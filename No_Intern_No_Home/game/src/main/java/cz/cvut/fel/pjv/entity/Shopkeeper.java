package cz.cvut.fel.pjv.entity;

import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

public class Shopkeeper extends Entity {

    private GamePanel gamePanel;
    private static Random random = new Random();

    public Shopkeeper(GamePanel panel) {
        this.name = "Shopkeeper";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = 10;
        this.worldY = 10;

        getNPCImage();
    }

    public Shopkeeper(GamePanel panel, int x, int y) {
        this.name = "Shopkeeper";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = x;
        this.worldY = y;

        getNPCImage();
    }

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

    protected BufferedImage assignImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
            image = Toolbox.scaleImage(image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage image = directionToImageMap.getOrDefault(direction, () -> null).get();
        int screenX = worldX - gamePanel.player.worldX + gamePanel.screenWidth - 8 * GamePanel.TILE_SIZE - 24;

        int screenY = worldY - gamePanel.player.worldY + gamePanel.screenHeight - 6 * GamePanel.TILE_SIZE - 24;
        // draw only objects that are in the screen
        if (worldX + GamePanel.TILE_SIZE > gamePanel.player.worldX - gamePanel.screenWidth
                && worldX - GamePanel.TILE_SIZE < gamePanel.player.worldX + gamePanel.screenWidth
                && worldY + GamePanel.TILE_SIZE > gamePanel.player.worldY - gamePanel.screenHeight
                && worldY - GamePanel.TILE_SIZE < gamePanel.player.worldY + gamePanel.screenHeight) {
            g.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        }
    }

    @Override
    public void talk() {
        gamePanel.changeGameState(6);
    }

    @Override
    public void move() {
        int i;

        if (actionCounter < ACTION_DELAY) {
            actionCounter++;

        } else {
            i = Entity.random.nextInt(4);

            if (i == 0) {
                direction = "up";
                worldY -= speed;
            } else if (i == 1) {
                direction = "down";
                worldY += speed;
            } else if (i == 2) {
                direction = "left";
                worldX -= speed;
            } else if (i == 3) {
                direction = "right";
                worldX += speed;
            }
            actionCounter = 0;

        }

    }

    private void increasePlayersLuck() {
        if (gamePanel.player.getChipCount() > 25) {
            gamePanel.player.setPlayerLuck(gamePanel.player.getPlayerLuck() + 0.05f);
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() - 25);
            gamePanel.ui.setAnnounceMessage("You have increased your luck by 4%!");
        } else {
            gamePanel.ui.setAnnounceMessage("You don't have enough chips!");
        }
    }

    private void increasePlayersChips() {
        gamePanel.player.setChipCount(gamePanel.player.getChipCount() + 10);
        gamePanel.player.setPlayerLuck(gamePanel.player.getPlayerLuck() - 0.01f);
        gamePanel.ui.setAnnounceMessage("You have received 5 chips!");
    }

    private void randomPlayerStats() {
        while (true) {
            if (random.nextFloat() < gamePanel.player.getPlayerLuck()) {
                gamePanel.player.setChipCount(gamePanel.player.getChipCount() + 10);
                gamePanel.player.setPlayerLuck(gamePanel.player.getPlayerLuck() + 0.01f);
                gamePanel.ui.setAnnounceMessage("You have received 10 chips!");
            } else {
                gamePanel.player.setPlayerLuck(gamePanel.player.getPlayerLuck() - 0.01f);
                gamePanel.player.setChipCount(gamePanel.player.getChipCount() - 10);
                gamePanel.ui.setAnnounceMessage("You have lost 5% of your luck!");
            }
            if (random.nextFloat() < gamePanel.player.getPlayerLuck()) {
                break;
            }
        }
    }

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
