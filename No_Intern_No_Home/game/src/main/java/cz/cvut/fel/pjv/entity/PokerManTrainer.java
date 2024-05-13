package cz.cvut.fel.pjv.entity;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;
import cz.cvut.fel.pjv.Constants;

public class PokerManTrainer extends Entity {

    public PokerManTrainer(int x, int y) {
        this.name = "Trainer";
        this.speed = 0;
        this.direction = Constants.UP;
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {

        up1 = assignImage("/npc/trainer_up");
        up2 = assignImage("/npc/trainer_up");
        down1 = assignImage("/npc/trainer_down");
        down2 = assignImage("/npc/trainer_down");
        left1 = assignImage("/npc/trainer_left");
        left2 = assignImage("/npc/trainer_left");
        right1 = assignImage("/npc/trainer_right");
        right2 = assignImage("/npc/trainer_right");

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

    public void setDialogueMessage() {
        dialogues[0] = "Welcome challenger! I am the Pokemon Gym Leader!";
        dialogues[1] = "I mean.... PokerMan Trainer!\nI assume you are here to challenge me?";
        dialogues[2] = "I am the best PokerMan Trainer in the world!\n And probably the only one...";
        dialogues[3] = "What do you mean you don't have a PokerMon?\n";
        dialogues[4] = "Well, I guess you can fight yourself then...\n Hopefully you have insurance.";
        dialogues[5] = "Are you ready to fight? (Y/N)";
        dialogues[6] = "";

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
    public void move() {
        actionCounter = 0;
    }

}
