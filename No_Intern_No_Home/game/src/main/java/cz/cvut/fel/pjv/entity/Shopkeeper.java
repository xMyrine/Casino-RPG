package cz.cvut.fel.pjv.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

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

        setDialogueMessage();
        getNPCImage();
    }

    public Shopkeeper(GamePanel panel, int x, int y) {
        this.name = "Shopkeeper";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
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
            image = Toolbox.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }

    private Map<String, Supplier<BufferedImage>> directionToImageMap = new HashMap<>();
    {
        directionToImageMap.put("up", () -> (spriteIndex == 1) ? up1 : (spriteIndex == 2) ? up2 : down1);
        directionToImageMap.put("down", () -> (spriteIndex == 1) ? down1 : (spriteIndex == 2) ? down2 : down1);
        directionToImageMap.put("left", () -> (spriteIndex == 1) ? left1 : (spriteIndex == 2) ? left2 : down1);
        directionToImageMap.put("right", () -> (spriteIndex == 1) ? right1 : (spriteIndex == 2) ? right2 : down1);
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage image = directionToImageMap.getOrDefault(direction, () -> null).get();
        int screenX = worldX - gamePanel.player.worldX + gamePanel.screenWidth - 8 * gamePanel.tileSize - 24;

        int screenY = worldY - gamePanel.player.worldY + gamePanel.screenHeight - 6 * gamePanel.tileSize - 24;
        // draw only objects that are in the screen
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.screenWidth
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.screenWidth
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.screenHeight
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.screenHeight) {
            g.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    public void setDialogueMessage() {

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
            gamePanel.ui.setAnnounceMessage("You have increased your luck by 5%!");
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
        System.out.println("Command: " + command);
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
