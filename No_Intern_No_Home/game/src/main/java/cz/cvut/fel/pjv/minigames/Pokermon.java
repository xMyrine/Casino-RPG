package cz.cvut.fel.pjv.minigames;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Player;

/**
 * Pokermon is a minigame that the player plays in the third level.
 * The player has to defeat the enemy to finish the level.
 * The player can use special items to defeat the enemy.
 * 
 * Pokermon is inspired by the Pokemon game:
 * https://en.wikipedia.org/wiki/Pok%C3%A9mon_(video_game_series)
 * 
 * @Author Minh Tu Pham
 */
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

    /**
     * readAkla - Increase the player health by 2
     * Increase the player health by 2
     * If the player health is greater than 10
     * Set the player health to 10
     */
    private void readAkla() {
        playerHealth += 2;
        if (playerHealth > 10) {
            playerHealth = 10;
        }
    }

    /**
     * taylorsSeries - Increase the player attack by 1
     * Increase the player attack by 1
     */
    private void taylorsSeries() {
        enemyHealth -= playerAttack;
    }

    /**
     * twoInches - Increase the player attack by 1
     * Increase the player attack by 1
     */
    private void twoInches() {
        playerAttack += 1;
    }

    // Enemy moves

    /**
     * Attack that increases the enemy attack and health
     */
    private void lifesteal() {
        enemyAttack += 1;
        enemyHealth += 1;
        playerHealth -= 1;
    }

    /**
     * Attack that reduces the player health by the enemy attack
     */
    private void smash() {
        playerHealth -= enemyAttack;
    }

    /**
     * Attack that heals the enemy
     */
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

    /**
     * Run from the battle
     * Set the mode to 0
     * reset the fight
     */
    private void run() {
        setMode(0);
        reset(false);
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

    /**
     * Enemy attack
     * Based on the random number generated
     * Execute the attack (lifesteal, smash, heal)
     */
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

    /**
     * Execute the command based on the mode
     * 
     * @param command - the command to execute
     */
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

    /**
     * Execute the command based on the mode
     * 
     * @param command - the command to execute
     */
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

    /**
     * Execute the command with luck
     * 
     * @param command - the command to execute
     */
    private void executeWithLuck(Runnable command) {
        command.run();
        if (Pokermon.rand.nextDouble() < luck) {
            command.run();
        }
    }

    /**
     * Check if the player health is less than 0
     */
    private void checkPlayerHealth() {
        if (playerHealth <= 0) {
            gamePanel.getGameUI().setAnnounceMessage("You have been defeated press R to start again");
        }
    }

    /**
     * Check if the enemy health is less than 0
     */
    private void checkEnemyHealth() {
        if (enemyHealth <= 0 && cheatDeath) {
            gamePanel.getGameUI().setAnnounceMessage("You have defeated the enemy");
            finished = true;
            gamePanel.getLevelManager().checkLevelFinished();
            screenImage = winImage;
            gamePanel.getSound().playMusic(6);
        }
    }

    /**
     * Shoot the enemy
     * Reduce the enemy health by 10
     * Reduce the gun count by 1
     * Set the mode to 0
     * If the player doesn't have a gun
     * Display a message
     */
    public void shoot() {
        if (gamePanel.getPlayer().isSpecialItemInInventory(Player.GUN_INDEX)) {
            gamePanel.getSound().playMusic(7);
            enemyHealth -= 10;
            gamePanel.getPlayer().removeSpecialItem(Player.GUN_INDEX);
            mode = 0;
        } else {
            gamePanel.getGameUI().setAnnounceMessage("You don't have a gun");

        }

    }

    /**
     * Reset the game
     * Set the player health to 10
     * Set the enemy health to 10
     * Set the player attack to 1
     * Set the enemy attack to 0
     * Set the mode to 0
     * Set the cheat
     * Set the finished
     * Load the screen image
     */
    public void reset(boolean sound) {
        if (sound) {
            gamePanel.getSound().playMusic(9);
        }
        playerHealth = 10;
        enemyHealth = 10;
        playerAttack = 1;
        enemyAttack = 0;
        mode = 0;
        cheatDeath = false;
        try {
            screenImage = ImageIO.read(getClass().getResourceAsStream("/screens/pokermonbattle.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
