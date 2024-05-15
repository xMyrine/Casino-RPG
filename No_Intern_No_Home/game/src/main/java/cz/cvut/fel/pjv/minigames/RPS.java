package cz.cvut.fel.pjv.minigames;

import java.util.Random;

import cz.cvut.fel.pjv.GamePanel;
import lombok.Data;

/**
 * RPS is a minigame where the player plays Rock, Paper, Scissors against the
 * boss.
 * It is the final minigame of the game.
 * Whatever the outcome is the game ends and endscreen is displayed.
 * 
 * @Author Minh Tu Pham
 */
@Data
public class RPS {

    public static final int ROCK = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;

    private int playerChoice;
    private int enemyChoice;
    private boolean completed = false;
    private boolean busted = false;

    private int bossPoints = 0;
    private int playerPoints = 0;

    private GamePanel gamePanel;
    private Random rand = new Random();

    public RPS(GamePanel gp) {
        playerChoice = -1;
        enemyChoice = -1;
        gamePanel = gp;
    }

    public boolean end() {
        return completed;
    }

    /**
     * Evaluate the player and enemy choices and determine the winner
     */
    private void evaluate() {
        if (playerChoice == enemyChoice) {
            gamePanel.getGameUI().setAnnounceMessage("Draw");
        } else if ((playerChoice == ROCK && enemyChoice == SCISSORS) ||
                (playerChoice == PAPER && enemyChoice == ROCK) ||
                (playerChoice == SCISSORS && enemyChoice == PAPER)) {
            playerPoints++;
        } else {
            bossPoints++;
        }
        determineWinner();
    }

    /**
     * Get the player and enemy choices
     * If the player would win, the boss will randomly choose again based on the
     * player's luck and then
     * evaluate the choices
     * 
     * @param choice - the player's choice of Rock, Paper, or Scissors
     */
    public void getChoices(int choice) {
        playerChoice = choice;
        enemyChoice = rand.nextInt(3);
        if (((playerChoice == ROCK && enemyChoice == SCISSORS) ||
                (playerChoice == PAPER && enemyChoice == ROCK) ||
                (playerChoice == SCISSORS && enemyChoice == PAPER)) &&
                rand.nextFloat() > gamePanel.getPlayer().getPlayerLuck()) {
            enemyChoice = rand.nextInt(3);
        }
        evaluate();
    }

    /**
     * Determine the winner of the game
     * If the player wins, the player receives 10000 chips and the completed boolean
     * is set to true
     * If the boss wins, the player loses and the busted boolean is set to true
     */
    private void determineWinner() {
        if (playerPoints == 5) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 10000);
            gamePanel.getGameUI().setAnnounceMessage("You won 10000 chips");
            gamePanel.getSound().playNewSound(10);
            completed = true;
            gamePanel.getLevelManager().checkLevelFinished();
        } else if (bossPoints == 5) {
            gamePanel.getGameUI().setAnnounceMessage("You lost");
            gamePanel.getSound().playNewSound(10);
            busted = true;
            gamePanel.getLevelManager().checkLevelFinished();
        }
    }

    public boolean getBusted() {
        return busted;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public int getBossPoints() {
        return bossPoints;
    }
}
