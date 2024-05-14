package cz.cvut.fel.pjv.minigames;

import java.util.Random;

import cz.cvut.fel.pjv.GamePanel;

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
        }
        determineWinner();
    }

    /**
     * Get the player and enemy choices
     * 
     * @param choice - the player's choice of Rock, Paper, or Scissors
     */
    public void getChoices(int choice) {
        playerChoice = choice;
        System.out.println("Player choice: " + playerChoice);
        enemyChoice = rand.nextInt(3);
        if (((playerChoice == ROCK && enemyChoice == SCISSORS) ||
                (playerChoice == PAPER && enemyChoice == ROCK) ||
                (playerChoice == SCISSORS && enemyChoice == PAPER)) &&
                rand.nextFloat() < gamePanel.getPlayer().getPlayerLuck()) {
            enemyChoice = rand.nextInt(3);
        }
        evaluate();
    }

    /**
     * Determine the winner of the game
     */
    private void determineWinner() {
        if (playerPoints == 5) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 10000);
            gamePanel.getGameUI().setAnnounceMessage("You won 10000 chips");
            completed = true;
        } else if (bossPoints == 5) {
            gamePanel.getGameUI().setAnnounceMessage("You lost");
            busted = true;
        }
    }

    public boolean getBusted() {
        return busted;
    }
}
