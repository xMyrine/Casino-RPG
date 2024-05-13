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

    private static Random rand = new Random();

    private GamePanel gamePanel;

    public Pokermon(GamePanel gp) {
        playerHealth = 10;
        enemyHealth = 10;
        gamePanel = gp;
        mode = 0;
        playerAttack = 1;
        enemyAttack = 0;
        luck = gamePanel.getPlayer().getPlayerLuck();

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
            float odds = gamePanel.getPlayer().getPlayerLuck();
            if (Math.random() > odds && enemyHealth < 10) {
                enemyHealth += 2;
            }
        } else {
            smash();
            if (Pokermon.rand.nextDouble() < 0.1) {
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
        int attack = rand.nextInt(4);
        if (enemyHealth <= 0 && !cheatDeath) {
            gamePanel.getGameUI().setAnnounceMessage("Pickachu Used Cheat Death!");
            cheatDeath();
        }
        switch (attack) {
            case 0:
                lifesteal();
                if (Pokermon.rand.nextDouble() < 0.1) {
                    smash();
                }
                break;
            case 1:
                smash();
                if (Pokermon.rand.nextDouble() < 0.1) {
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
            executeModeOneCommand(command);
            enemyAttack();
        } else if (mode == 2 && command == 0) {
            shoot();
            enemyAttack();
        }
        checkPlayerHealth();
        checkEnemyHealth();
    }

    private void executeModeOneCommand(int command) {
        switch (command) {
            case 0:
                executeWithLuck(this::readAkla);
                break;
            case 1:
                executeWithLuck(this::taylorsSeries);
                break;
            case 2:
                executeWithLuck(this::twoInches);
                break;
            default:
                break;
        }
    }

    private void executeWithLuck(Runnable command) {
        command.run();
        if (Pokermon.rand.nextDouble() < luck) {
            command.run();
        }
    }

    private void checkPlayerHealth() {
        if (playerHealth <= 0) {
            gamePanel.getGameUI().setAnnounceMessage("You have been defeated press R to start again");
        }
    }

    private void checkEnemyHealth() {
        if (enemyHealth <= 0 && cheatDeath) {
            gamePanel.getGameUI().setAnnounceMessage("You have defeated the enemy");
            finished = true;
            gamePanel.getLevelManager().checkLevelFinished();
            screenImage = winImage;
            gamePanel.getSound().playMusic(6);
        }
    }

    public void shoot() {
        if (gamePanel.getPlayer().getSpecialItem(Player.GUN_INDEX) > 0) {
            gamePanel.getSound().playMusic(7);
            enemyHealth -= 10;
            gamePanel.getPlayer().setSpecialItem(Player.GUN_INDEX,
                    gamePanel.getPlayer().getSpecialItem(Player.GUN_INDEX) - 1);
            mode = 0;
        } else {
            gamePanel.getGameUI().setAnnounceMessage("You don't have a gun");

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
