package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.LevelManager;
import cz.cvut.fel.pjv.Toolbox;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

public class Worker extends Entity implements NPC {

    GamePanel gamePanel;

    public Worker(GamePanel panel) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 0;
        this.direction = "left";
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public Worker(GamePanel panel, int x, int y) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 0;
        this.direction = "left";
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {

        up1 = assignImage("/npc/guy_up_1");
        up2 = assignImage("/npc/guy_up_1");
        down1 = assignImage("/npc/worker");
        down2 = assignImage("/npc/worker");
        left1 = assignImage("/npc/wizard_left_1");
        left2 = assignImage("/npc/wizard_left_1");
        right1 = assignImage("/npc/wizard_right_1");
        right2 = assignImage("/npc/wizard_right_1");

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
        if (LevelManager.getLevelNumber() == 1) {
            System.out.println("Dialogue for Level 1");
            dialogues[0] = "See the slot machines around here? \n If you put enough chips into them,\n they will eventually pay out.";
            dialogues[1] = "Come back challenge me when you are ready.";
            dialogues[2] = "Are you ready to take me on? (Y/N)";
        } else if (LevelManager.getLevelNumber() == 2) {
            System.out.println("Dialogue for Level 2");
            dialogues[0] = "I see you have made it this far. \n I am impressed.";
            dialogues[1] = "See that prostitute walking over there?\n Bet you never saw one of these\n You study CS anyways LOL.";
            dialogues[2] = "Well, just get laid and come back to me.";
        }
    }

    @Override
    public void talk() {
        gamePanel.ui.setDialogue(dialogues[dialogueIndex]);
        this.turnEntity(gamePanel.player.direction);
        dialogueIndex++;
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
    }

    @Override
    public void move() {
        actionCounter = 0;
    }

}
