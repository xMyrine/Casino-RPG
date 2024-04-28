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
        this.speed = 1;
        this.direction = "left";
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public Worker(GamePanel panel, int x, int y) {
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

        up1 = assignImage("/npc/worker1_up_1");
        up2 = assignImage("/npc/worker1_up_2");
        down1 = assignImage("/npc/worker1_down_1");
        down2 = assignImage("/npc/worker1_down_2");
        left1 = assignImage("/npc/worker1_left_1");
        left2 = assignImage("/npc/worker1_left_2");
        right1 = assignImage("/npc/worker1_right_1");
        right2 = assignImage("/npc/worker1_right_2");

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
        dialogues[0] = "The slot machines around here could use some love.\n I'm not a programmer though.";
        dialogues[1] = "Or if you want to you can just \ngamble with me first.";
        dialogues[2] = "I'm not very good at it though. I always lose.";
        dialogues[3] = "Once you are ready to challenge me\n just talk to me again.";
        dialogues[4] = "We are gonna play roulette. Rules are as follows:\n You can bet on a number or a color.";
        dialogues[5] = "If you bet on a number and it comes up\n you win 36 times your bet.";
        dialogues[6] = "If you bet on a color and it comes up\n you win 2 times your bet.";
        dialogues[7] = "You can adjust your bet with Q/E but beware,\n if you dont pay attention the bet\n might be rising on its own :)";
        dialogues[8] = "Use W/S to navigate the roulette table and\n press ENTER to place your bet. Good luck!";
        dialogues[9] = "(Do you wish to challenge this worker?) (Y/N)";

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
