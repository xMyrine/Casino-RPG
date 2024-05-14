package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Constants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Worker2 is an NPC that the player can interact with.
 * It is the second minigame npc the player meets in the game.
 * The player can play blackjack with the worker.
 * 
 * @Author Minh Tu Pham
 */
public class Worker2 extends Entity implements NPC {
    GamePanel gamePanel;

    public Worker2(GamePanel panel) {
        this.name = "Worker";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.LEFT;
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public Worker2(GamePanel panel, int x, int y) {
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
        up1 = assignImage("/npc/worker2_up_1");
        up2 = assignImage("/npc/worker2_up_2");
        down1 = assignImage("/npc/worker2_down_1");
        down2 = assignImage("/npc/worker2_down_2");
        left1 = assignImage("/npc/worker2_left_1");
        left2 = assignImage("/npc/worker2_left_2");
        right1 = assignImage("/npc/worker2_right_1");
        right2 = assignImage("/npc/worker2_right_2");

    }

    /**
     * Worker2 has 4 directions and 2 sprites for each direction.
     * Draws the NPC on the screen relative to the player's position.
     * 
     * @param g
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
     * Set the dialogue messages for worker.
     * Player can start blackjack with the worker, while the worker gives the player
     * some tips.
     */
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

    /**
     * Worker2 has a random movement pattern.
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
