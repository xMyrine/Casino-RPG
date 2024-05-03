package cz.cvut.fel.pjv.entity;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

public class PokerManTrainer extends Entity {

    public PokerManTrainer(GamePanel panel, int x, int y) {
        this.name = "Trainer";
        this.gamePanel = panel;
        this.speed = 0;
        this.direction = "up";
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
            image = Toolbox.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
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
        super.talk();
    }

    @Override
    public void move() {
        actionCounter = 0;
    }

}
