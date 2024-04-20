package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;
import cz.cvut.fel.pjv.entity.Entity;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.awt.Graphics2D;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class IntroNPC extends Entity implements NPC {

    GamePanel gamePanel;

    public IntroNPC(GamePanel panel) {
        this.name = "IntroNPC";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "down";
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public IntroNPC(GamePanel panel, int x, int y) {
        this.name = "IntroNPC";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "down";
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {

        up1 = assignImage("/npc/guy_up_1");
        up2 = assignImage("/npc/guy_up_2");
        down1 = assignImage("/npc/guy_down_1");
        down2 = assignImage("/npc/guy_down_2");
        left1 = assignImage("/npc/guy_left_1");
        left2 = assignImage("/npc/guy_left_2");
        right1 = assignImage("/npc/guy_right_1");
        right2 = assignImage("/npc/guy_right_2");

    }

    protected BufferedImage assignImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
            image = Toolbox.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Assigning image successful" + path + ".png");
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
        super.talk();
    }

    public void setDialogueMessage() {
        dialogues[0] = "Ayo, you are new around here. \n Haven't seen you before.";
        dialogues[1] = "This place is fcked up. You need to get \n out of here. You need to gamble\n your way out.";
        dialogues[2] = "You will need to finish a task in the room to get a chance \nto challenge the room master.";
        dialogues[3] = "You will need to beat the room master to advance.";
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

}
