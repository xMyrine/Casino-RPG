package cz.cvut.fel.pjv.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

public class Craftsman extends Entity {

    public Craftsman(GamePanel panel) {
        this.name = "Craftsman";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = 10;
        this.worldY = 10;

        getNPCImage();
    }

    public Craftsman(GamePanel panel, int x, int y) {
        this.name = "Craftsman";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = x;
        this.worldY = y;

        getNPCImage();
    }

    public void getNPCImage() {
        up1 = assignImage("/npc/craftsman_up_1");
        up2 = assignImage("/npc/craftsman_up_2");
        down1 = assignImage("/npc/craftsman_down_1");
        down2 = assignImage("/npc/craftsman_down_2");
        left1 = assignImage("/npc/craftsman_left_1");
        left2 = assignImage("/npc/craftsman_left_2");
        right1 = assignImage("/npc/craftsman_right_1");
        right2 = assignImage("/npc/craftsman_right_2");

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

    @Override
    public void talk() {
        gamePanel.changeGameState(GamePanel.CRAFTSCREEN);
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

    /*
     * Craftsman crafts special items for the player.
     */
    public void craft(int item) {
        int cigarCount = gamePanel.player.getSpecialItemsFragmentCount(Player.CIGAR);
        int gunCount = gamePanel.player.getSpecialItemsFragmentCount(Player.GUN);
        int cardsCount = gamePanel.player.getSpecialItemsFragmentCount(Player.CARDS);

        if (item == Player.CIGAR && cigarCount >= 3 && gamePanel.player.getChipCount() >= 50) {
            gamePanel.player.setSpecialItemsFragmentCount(Player.CIGAR,
                    gamePanel.player.getSpecialItemsFragmentCount(Player.CIGAR) - 3);
            gamePanel.player.setSpecialItem(Player.CIGAR, gamePanel.player.getSpecialItem(Player.CIGAR) + 1);
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() - 50);
        } else if (item == Player.GUN && gunCount >= 3 && gamePanel.player.getChipCount() >= 250) {
            gamePanel.player.setSpecialItemsFragmentCount(Player.GUN,
                    gamePanel.player.getSpecialItemsFragmentCount(Player.GUN) - 3);
            gamePanel.player.setSpecialItem(Player.GUN, gamePanel.player.getSpecialItem(Player.GUN) + 1);
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() - 250);
        } else if (item == Player.CARDS && cardsCount >= 3 && gamePanel.player.getChipCount() >= 100) {
            gamePanel.player.setSpecialItemsFragmentCount(Player.CARDS,
                    gamePanel.player.getSpecialItemsFragmentCount(Player.CARDS) - 3);
            gamePanel.player.setSpecialItem(Player.CARDS, gamePanel.player.getSpecialItem(Player.CARDS) + 1);
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() - 100);
        } else {
            gamePanel.ui.setAnnounceMessage("Not enough fragments or unsufficient funds");
        }
    }
}
