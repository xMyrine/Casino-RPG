package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Constants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Prostitute is an NPC that the player can interact with.
 * In order to pass through the second level, the player has to answer the
 * prostitute's questions.
 * Player has to have a certain amount of luck to pass the test and correct
 * answers increases the reputation.
 * 
 * @Author Minh Tu Pham
 */
public class Prostitute extends Entity implements NPC {
    private int reputation = 0;
    private float rizz = 0;

    public Prostitute(int x, int y) {
        this.name = "Prostitue";
        this.speed = 1;
        this.direction = Constants.DOWN;
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    /**
     * Assigns images to the NPC.
     */
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

    /**
     * Increase reputation by 1.
     */
    public void increaseReputation() {
        reputation++;
    }

    /**
     * Decrease reputation by 1.
     */
    public void decreaseReputation() {
        reputation--;
    }

    /**
     * Prostitute has 4 directions and 2 sprites for each direction.
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
     * Prostitute dialogue method
     * Compared to others NPCs, Prostitute has a unique dialogue system.
     * If the player answers the questions correctly, the reputation increases.
     * If the player answers the questions incorrectly, the reputation decreases.
     * 
     */
    @Override
    public void talk() {
        gamePanel.getGameUI().setDialogue(dialogues[dialogueIndex]);
        if (dialogueIndex == 0) {
            reputation = 0;
        }
        if (dialogueIndex == 16) {
            calculateRizz();
            if (rizz > 2) {
                dialogueIndex++;
                gamePanel.getLevelManager().getSecondLevel().getLaid();
                gamePanel.getLevelManager().checkLevelFinished();
            }
        }
        dialogueIndex++;
        this.turnEntity(gamePanel.getPlayer().direction);
        if (dialogues[dialogueIndex] == null || dialogueIndex >= 19) {
            dialogueIndex = 0;
        }
    }

    /**
     * Assigns dialogue messages to the NPC.
     * Prostitute has 20 dialogue messages, which is the maximum amount of
     * dialogue messages an NPC can have.
     */
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
        dialogues[15] = "69?";
        dialogues[16] = "Hmmmmm, let me think about it.";
        dialogues[17] = "I am sorry, YOU SUCK";
        dialogues[18] = "Alright, let's have some fun. <3";
        dialogues[19] = "";
    }

    /**
     * Calculates the rizz value.
     */
    private void calculateRizz() {
        rizz = gamePanel.getPlayer().getPlayerLuck() * reputation;
    }

    /**
     * Prostitute moves randomly.
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
