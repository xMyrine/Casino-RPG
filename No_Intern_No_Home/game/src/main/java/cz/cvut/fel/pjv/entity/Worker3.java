package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Constants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Worker3 is an NPC that the player can interact with.
 * It is the third minigame npc the player meets in the game.
 * The player can play dice roll with the worker.
 * 
 * @Author Minh Tu Pham
 */
public class Worker3 extends Entity implements NPC {

    GamePanel gamePanel;

    public Worker3(GamePanel panel) {
        this.name = "Worker3";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public Worker3(GamePanel panel, int x, int y) {
        this.name = "Worker3";
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
        up1 = assignImage("/npc/worker3_up1");
        up2 = assignImage("/npc/worker3_up2");
        down1 = assignImage("/npc/worker3_down1");
        down2 = assignImage("/npc/worker3_down2");
        left1 = assignImage("/npc/worker3_left1");
        left2 = assignImage("/npc/worker3_left2");
        right1 = assignImage("/npc/worker3_right1");
        right2 = assignImage("/npc/worker3_right2");
    }

    /**
     * Worker3 has 4 directions and 2 sprites for each direction.
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
     * Sets the dialogue messages for the NPC.
     * Player can play dice roll with the worker, while in the dialogue.
     */
    @Override
    public void setDialogueMessage() {
        dialogues[0] = "Oh, hello there!";
        dialogues[1] = "Pretty nice day, isn't it?";
        dialogues[2] = "Also pretty impressive you made it here!";
        dialogues[3] = "Ohhh, what game do I play? \n Just a simple dice roll game. \n You can try it out if you want!";
        dialogues[4] = "You just bet higher or lower than the number I roll. \n If you win, you get double the money!";
        dialogues[5] = "Oh yeah, don't forget to buy some items from the shopkeeper! \n He has some pretty good stuff!";
        dialogues[6] = "Also your luck is pretty important in this game. \n The higher the luck, the higher the chance of winning!";
        dialogues[7] = "(Do you wish to challenge this NPC? (Y/N))";
        dialogues[8] = "Oh, you want to play? \n Great! Let's start!";
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

}
