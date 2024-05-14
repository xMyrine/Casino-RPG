package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Constants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Worker is an NPC that the player can interact with.
 * It is the first minigame npc the player meets in the game.
 * The player can play roulette with the worker.
 * 
 * @Author Minh Tu Pham
 */
public class Worker extends Entity implements NPC {

    GamePanel gamePanel;

    public Worker(GamePanel panel) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public Worker(GamePanel panel, int x, int y) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    /**
     * Assigns images to the NPC.
     */
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

    /**
     * Worker has 4 directions and 2 sprites for each direction.
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
     * Dialogue messages for the worker.
     * Player can challenge the worker to play roulette, while in the dialogue.
     */
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
        dialogues[10] = "";

    }

    /**
     * Worker moves randomly.
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

}
