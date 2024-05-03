package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

public class Worker2 extends Entity implements NPC {
    GamePanel gamePanel;

    public Worker2(GamePanel panel) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public Worker2(GamePanel panel, int x, int y) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "left";
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {

        up1 = assignImage("/npc/worker2_up_1");
        up2 = assignImage("/npc/worker2_up_2");
        down1 = assignImage("/npc/worker2_down_1");
        down2 = assignImage("/npc/worker2_down_2");
        left1 = assignImage("/npc/worker2_left_1");
        left2 = assignImage("/npc/worker2_left_2");
        right1 = assignImage("/npc/worker2_right_1");
        right2 = assignImage("/npc/worker2_right_2");

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
        dialogues[0] = "I see you have made it this far. \n I am impressed.";
        dialogues[1] = "See that prostitute walking over there?\n Bet you never saw one of these\n You study CS anyways LOL.";
        dialogues[2] = "Well, just get laid";
        dialogues[3] = "Also if you want to challenge me,\n you better know what BlackJack is.\n I am the master of it.";
        dialogues[4] = "(If you know the game press Y to start)\n (If you don't press E to learn the rules first)";
        dialogues[5] = "According to the rules of blackjack,\nthe goal is to get a hand score\n(combined value of the cards you hold) that is as close to 21\nas possible without going over it.";
        dialogues[6] = "Each card from 2 to 10 has a value equal to its number.\n Jack, Queen, and King all have a value of 10,\nwhile aces can be counted as either 1 or 11.";
        dialogues[7] = "You are competing against the dealer,\nso whoever has a better score wins. Going over 21 is a “bust” or an automatic loss,\nwhile hitting exactly 21 is an automatic win.";
        dialogues[8] = "";

    }

    // @Override
    // public void talk() {
    // gamePanel.ui.setDialogue(dialogues[dialogueIndex]);
    // this.turnEntity(gamePanel.player.direction);
    // dialogueIndex++;
    // if (dialogues[dialogueIndex] == null) {
    // dialogueIndex = 0;
    // }
    // }

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
