package cz.cvut.fel.pjv.minigames;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Player;

public class Pokermon {
    private int playerHealth;
    private int enemyHealth;
    private short playerAttack;
    private short enemyAttack;
    private int mode;
    private boolean cheatDeath = false;
    private boolean finished = false;
    private float luck;

    private BufferedImage screenImage;
    private BufferedImage shootButton;
    private BufferedImage attackButtons;
    private BufferedImage winImage;

    private static Random RAND = new Random();

    private GamePanel gamePanel;

    public Pokermon(GamePanel gp) {
        playerHealth = 10;
        enemyHealth = 10;
        gamePanel = gp;
        mode = 0;
        playerAttack = 1;
        enemyAttack = 0;
        luck = gamePanel.player.getPlayerLuck();

        try {
            screenImage = ImageIO.read(getClass().getResourceAsStream("/screens/pokermonbattle.png"));
            attackButtons = ImageIO.read(getClass().getResourceAsStream("/buttons/pokermonattack.png"));
            shootButton = ImageIO.read(getClass().getResourceAsStream("/buttons/pokerman_shoot.png"));
            winImage = ImageIO.read(getClass().getResourceAsStream("/screens/pokermonbattlewin.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Player moves
    private void readAkla() {
        playerHealth += 2;
        if (playerHealth > 10) {
            playerHealth = 10;
        }
    }

    private void taylorsSeries() {
        enemyHealth -= playerAttack;
    }

    private void twoInches() {
        playerAttack += 1;
    }

    // Enemy moves

    private void lifesteal() {
        enemyAttack += 1;
        enemyHealth += 1;
        playerHealth -= 1;
    }

    private void smash() {
        playerHealth -= enemyAttack;
    }

    private void heal() {
        if (enemyHealth < 10) {
            enemyHealth += 2;
            float odds = gamePanel.player.getPlayerLuck();
            if (Math.random() > odds && enemyHealth < 10) {
                enemyHealth += 2;
            }
        } else {
            smash();
            if (Pokermon.RAND.nextDouble() < 0.1) {
                smash();
            }
        }
    }

    private void cheatDeath() {
        enemyHealth = 10;
        cheatDeath = true;
    }

    public boolean isFinished() {
        return finished;
    }

    // run away
    private void run() {
        setMode(0);
        gamePanel.changeGameState(GamePanel.GAMESCREEN);
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public int getMode() {
        return mode;
    }

    public int getPlayerAttack() {
        return playerAttack;
    }

    public int getEnemyAttack() {
        return enemyAttack;
    }

    public void setMode(int mode) {
        this.mode = mode;
        if (mode == 3) {
            run();
        }

    }

    public Image getScreenImage() {
        return screenImage;
    }

    public Image getAttackButtons() {
        return attackButtons;
    }

    public Image getShootButton() {
        return shootButton;
    }

    // Enemy attack

    private void enemyAttack() {
        int attack = RAND.nextInt(4);
        if (enemyHealth <= 0 && !cheatDeath) {
            cheatDeath();
        }
        switch (attack) {
            case 0:
                lifesteal();
                if (Pokermon.RAND.nextDouble() < 0.1) {
                    smash();
                }
                break;
            case 1:
                smash();
                if (Pokermon.RAND.nextDouble() < 0.1) {
                    smash();
                }
                break;
            case 2:
                heal();
                break;
            default:
                break;
        }

    }

    public void executeCommand(int command) {
        if (mode == 1) {
            switch (command) {
                case 0:
                    readAkla();
                    if (Pokermon.RAND.nextDouble() < luck) {
                        readAkla();
                    }
                    break;
                case 1:
                    taylorsSeries();
                    if (Pokermon.RAND.nextDouble() < luck) {
                        taylorsSeries();
                    }
                    break;
                case 2:
                    twoInches();
                    if (Pokermon.RAND.nextDouble() < luck) {
                        twoInches();
                    }
                    break;
                default:
                    break;
            }
            enemyAttack();
        } else if (mode == 2) {
            if (command == 0) {
                shoot();
                enemyAttack();
            }
        }

        if (playerHealth <= 0) {
            gamePanel.ui.setAnnounceMessage("You have been defeated press R to start again");
        }

        if (enemyHealth <= 0 && cheatDeath) {
            gamePanel.ui.setAnnounceMessage("You have defeated the enemy");
            finished = true;
            gamePanel.levelManager.checkLevelFinished();
            screenImage = winImage;
            gamePanel.sound.playMusic(6);
        }
    }

    public void shoot() {
        if (gamePanel.player.getSpecialItem(Player.GUN) > 0) {
            gamePanel.sound.playMusic(7);
            enemyHealth -= 10;
            gamePanel.player.setSpecialItem(Player.GUN, gamePanel.player.getSpecialItem(Player.GUN) - 1);
            mode = 0;
        } else {
            gamePanel.ui.setAnnounceMessage("You don't have a gun");

        }

    }

    public void reset() {
        playerHealth = 10;
        enemyHealth = 10;
        playerAttack = 1;
        enemyAttack = 0;
        mode = 0;
        cheatDeath = false;
        finished = false;
        try {
            screenImage = ImageIO.read(getClass().getResourceAsStream("/screens/pokermonbattle.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
