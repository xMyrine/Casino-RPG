package cz.cvut.fel.pjv.entity;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;
import cz.cvut.fel.pjv.items.*;
import cz.cvut.fel.pjv.Constants;

public class Craftsman extends Entity {

    public Craftsman() {
        this.name = "Craftsman";
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = 10;
        this.worldY = 10;

        getNPCImage();
    }

    public Craftsman(int x, int y) {
        this.name = "Craftsman";
        this.speed = 1;
        this.direction = Constants.LEFT;
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
            image = Toolbox.scaleImage(image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }

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

    /*
     * Craftsman crafts special items for the player.
     */
    public void craft(int item) {
        int cigarCount = gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.CIGAR_INDEX);
        int gunIndex = gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.GUN_INDEX);
        int cardsIndex = gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.CARDS_INDEX);

        if (item == Player.CIGAR_INDEX && cigarCount >= 3 && gamePanel.getPlayer().getChipCount() >= 50) {
            gamePanel.getPlayer().setSpecialItemsFragmentCount(Player.CIGAR_INDEX,
                    gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.CIGAR_INDEX) - 3);
            Cigarette cigar = new Cigarette(gamePanel.getPlayer());
            gamePanel.getPlayer().addItems(cigar);
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - 50);
        } else if (item == Player.GUN_INDEX && gunIndex >= 3 && gamePanel.getPlayer().getChipCount() >= 250) {
            gamePanel.getPlayer().setSpecialItemsFragmentCount(Player.GUN_INDEX,
                    gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.GUN_INDEX) - 3);
            Gun gun = new Gun(gamePanel.getLevelManager().pokermon);
            gamePanel.getPlayer().addItems(gun);
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - 250);
        } else if (item == Player.CARDS_INDEX && cardsIndex >= 3 && gamePanel.getPlayer().getChipCount() >= 100) {
            gamePanel.getPlayer().setSpecialItemsFragmentCount(Player.CARDS_INDEX,
                    gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.CARDS_INDEX) - 3);
            gamePanel.getPlayer().setSpecialItem(Player.CARDS_INDEX,
                    gamePanel.getPlayer().getSpecialItem(Player.CARDS_INDEX) + 1);
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - 100);
        } else {
            gamePanel.getGameUI().setAnnounceMessage("Not enough fragments or unsufficient funds");
        }
    }
}
