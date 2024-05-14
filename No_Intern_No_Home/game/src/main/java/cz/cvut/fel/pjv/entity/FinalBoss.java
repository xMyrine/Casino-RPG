package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;
import cz.cvut.fel.pjv.Constants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FinalBoss extends Entity implements NPC {

    GamePanel gamePanel;
    private int directionIndex = 0;
    private static final String[] DIRECTIONS = { Constants.UP, Constants.RIGHT, Constants.DOWN, Constants.LEFT };
    private static final int STEPS_PER_DIRECTION = 5;
    private int steps = 0;

    public FinalBoss(GamePanel panel) {
        this.name = "FinalBoss";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.RIGHT;
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public FinalBoss(GamePanel panel, int x, int y) {
        this.name = "FinalBoss";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.RIGHT;
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {
        up1 = assignImage("/npc/finalboss_up1");
        up2 = assignImage("/npc/finalboss_up2");
        down1 = assignImage("/npc/finalboss_down1");
        down2 = assignImage("/npc/finalboss_down2");
        left1 = assignImage("/npc/finalboss_left1");
        left2 = assignImage("/npc/finalboss_left2");
        right1 = assignImage("/npc/finalboss_right1");
        right2 = assignImage("/npc/finalboss_right2");
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
    public void setDialogueMessage() {
        dialogues[0] = "Welcome challenger! Prepare to face your doom!";
        dialogues[1] = "You will never defeat me!\n I am the person that haunts your dreams";
        dialogues[2] = "I AM WHAT PEOPLE CALL:";
        dialogues[3] = "THE TECH INTERVIEWER!";
        dialogues[4] = "In order to challenge me,\n you need to have 1000 chips!";
        dialogues[5] = "We will a game, complex, difficult,\n and full of surprises!";
        dialogues[6] = "The pinnacle of the games offered here!";
        dialogues[7] = "The game that will test your skills\n and your luck!";
        dialogues[8] = "The game that will determine\n if you are worthy of the title of intern!";
        dialogues[9] = "The ONE AND ONLY!\n";
        dialogues[10] = "ROCK, PAPER, SCISSORS!";
        dialogues[11] = "Prepare yourself!";
        dialogues[12] = "(Do you wish to challenge the Final Boss?)\n (Y/N)";
    }

    @Override
    public void move() {
        if (actionCounter < ACTION_DELAY) {
            actionCounter++;
        } else {
            direction = DIRECTIONS[directionIndex];
            switch (direction) {
                case Constants.UP:
                    worldY -= speed;
                    break;
                case Constants.RIGHT:
                    worldX += speed;
                    break;
                case Constants.DOWN:
                    worldY += speed;
                    break;
                case Constants.LEFT:
                    worldX -= speed;
                    break;
                default:
                    break;
            }
            steps++;
            if (steps >= STEPS_PER_DIRECTION) {
                directionIndex = (directionIndex + 1) % DIRECTIONS.length;
                steps = 0;
            }
            actionCounter = 0;
        }
    }
}
