package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import cz.cvut.fel.pjv.Constants;

public class IntroNPC extends Entity implements NPC {

    GamePanel gamePanel;

    public IntroNPC(GamePanel panel) {
        this.name = "IntroNPC";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.DOWN;
        this.worldX = 10;
        this.worldY = 10;

        setDialogueMessage();
        getNPCImage();
    }

    public IntroNPC(GamePanel panel, int x, int y) {
        this.name = "IntroNPC";
        this.gamePanel = panel;
        this.speed = 1;
        this.direction = Constants.DOWN;
        this.worldX = x;
        this.worldY = y;

        setDialogueMessage();
        getNPCImage();
    }

    public void getNPCImage() {

        up1 = assignImage("/npc/guy_up_1");
        up2 = assignImage("/npc/guy_up_2");
        down1 = assignImage("/npc/guy_down_1");
        down2 = assignImage("/npc/guy_down_2");
        left1 = assignImage("/npc/guy_left_1");
        left2 = assignImage("/npc/guy_left_2");
        right1 = assignImage("/npc/guy_right_1");
        right2 = assignImage("/npc/guy_right_2");

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

    public void setDialogueMessage() {
        dialogues[0] = "Ayo, you are new around here. \n Haven't seen you before.";
        dialogues[1] = "I assume finding work, did not age well for you. \n You are in the right place.";
        dialogues[2] = "You can earn a hefty amount of money here \nif you play smart.But be careful, the house always wins.";
        dialogues[3] = "Unless luck is on your side? If you open your \ninventory with I,you can see how much money you have\n and whether luck is on your side ";
        dialogues[4] = "There are chest around here that you can open. \nThey might contain some money or items.";
        dialogues[5] = "You can also buy items from the shopkeeper.\nShe is a nice lady, but she is always busy.\nShe has a crazy deal though. If you feel lucky";
        dialogues[6] = "Craftsman is also around here. He can craft you items \nif you bring him the right materials or enough money.";
        dialogues[7] = "To enter a new room and unlock more games,\n you need to beat the worker running around here.";
        dialogues[8] = "He is a tough one, also he might tell you\n to do something around the room as well,\n so make sure to do that.";
        dialogues[9] = "Oh, who am I? Hmm, Let's just say I am a friend. \nI am here to help you out.";
        dialogues[10] = "";
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
