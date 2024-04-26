package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Prostitute extends Entity implements NPC {
    private int reputation = 0;
    private float rizz = 0;

    public Prostitute(GamePanel panel, int x, int y) {
        this.name = "Prostitue";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = "down";
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {

        up1 = assignImage("/npc/prost_up_1");
        up2 = assignImage("/npc/prost_up_2");
        down1 = assignImage("/npc/prost_down_1");
        down2 = assignImage("/npc/prost_down_2");
        left1 = assignImage("/npc/prost_left_1");
        left2 = assignImage("/npc/prost_left_2");
        right1 = assignImage("/npc/prost_right_1");
        right2 = assignImage("/npc/prost_right_2");

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

    public void increaseReputation() {
        reputation++;
        System.out.println("Reputation increased to: " + reputation);
    }

    public void decreaseReputation() {
        reputation--;
        System.out.println("Reputation decreased to: " + reputation);
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
        gamePanel.ui.setDialogue(dialogues[dialogueIndex]);
        if (dialogueIndex == 0) {
            reputation = 0;
            System.out.println("Reputation reset to: " + reputation);
        }
        if (dialogueIndex == 16) {
            calculateRizz();
            if (rizz > 1.75) {
                dialogueIndex++;
                gamePanel.levelManager.secondLevel.getLaid();
            }
        }
        dialogueIndex++;
        this.turnEntity(gamePanel.player.direction);
        if (dialogues[dialogueIndex] == null || dialogueIndex >= 19) {
            dialogueIndex = 0;
        }
    }

    public void setDialogueMessage() {
        dialogues[0] = "Oh, Hi there handsome. You look like you \n could use some company.";
        dialogues[1] = "Before we get to have fun, I will need\nyou to show me you are master RIZZLER.";
        dialogues[2] = "Just answer my question and we can have some fun.\nPress Y for yes/option 1 and X for no/option 2.";
        dialogues[3] = "Is your RICE purity test lower than 50?";
        dialogues[4] = "Do you have a girlfriend/wife?";
        dialogues[5] = "Are Asians superior race?";
        dialogues[6] = "Is 2 inches enough?";
        dialogues[7] = "Is it about the motion of the ocean?";
        dialogues[8] = "Do you have a condom?";
        dialogues[9] = "Little spoon or big spoon?";
        dialogues[10] = "Do you like to be dominated?";
        dialogues[11] = "Do you like to dominate?";
        dialogues[12] = "Do you like to be choked?";
        dialogues[13] = "Do you like to choke?";
        dialogues[14] = "Do you like to be slapped?";
        dialogues[15] = "Do you support LGBTQ+?";
        dialogues[16] = "69?";
        dialogues[17] = "Hmmmmm, let me think about it.";
        dialogues[18] = "I am sorry, YOU SUCK";
        dialogues[19] = "Alright, let's have some fun. <3";

    }

    public void calculateRizz() {
        rizz = gamePanel.player.getPlayerLuck() * reputation;
        System.out.println("Rizz: " + rizz);
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
