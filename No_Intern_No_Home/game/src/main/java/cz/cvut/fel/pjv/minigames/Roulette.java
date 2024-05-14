package cz.cvut.fel.pjv.minigames;

import java.util.Random;

import cz.cvut.fel.pjv.GamePanel;

/**
 * Roulette is a minigame where the player bets on a number or a color.
 * The player can bet on red, black, green or a number from 0 to 36.
 * Player plays roulette in the first level.
 * Player can modify the bet amount and gets paid by the standard roulette
 * rules in the US.
 * 
 * @Author Minh Tu Pham
 */
public class Roulette {

    private int winningNumber;
    private int playersNumber;
    GamePanel gamePanel;
    private int bet;
    private int minimumBet = 5;
    private boolean validBet = true;
    private boolean completed = false;

    private static final int RED = 666;
    private static final int BLACK = 420;
    private Random rand = new Random();

    public Roulette(GamePanel gp) {
        this.gamePanel = gp;
        bet = minimumBet;
    }

    /**
     * Starts the roulette minigame.
     * 
     * @param bettingNumber the player's betting choice
     */
    public void startR(int bettingNumber) {
        spin(bettingNumber);
    }

    /**
     * Checks if the roulette minigame is finished.
     * 
     * @return true if the roulette minigame is finished
     */
    public boolean end() {
        gamePanel.getLevelManager().getFirstLevel().setMiniGameFinished(completed);
        return completed;
    }

    /**
     * Simulates the spinning of the roulette wheel.
     * Checks if the has enough chips to bet.
     * Everytime player spins the bet increases without the player's choice.
     * This simulates the immersive experience of playing roulette.
     * 
     * @param bettingNumber
     */
    private void spin(int bettingNumber) {
        gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
        winningNumber = rand.nextInt(37);
        validBet = true;
        evaluate(bettingNumber);
        bet += minimumBet;
    }

    /**
     * Evaluates the player's betting choice and the winning number.
     * Pays the player according to the standard roulette rules in the US.
     * 
     * @param bettingNumber the player's betting choice
     */
    private void evaluate(int bettingNumber) {
        if (bettingNumber == 0) {
            playersNumber = RED;
        } else if (bettingNumber == 1) {
            playersNumber = BLACK;
        } else {
            playersNumber = bettingNumber - 2;
        }

        if ((playersNumber == RED && winningNumber % 2 == 0) || (playersNumber == BLACK && winningNumber % 2 != 0)) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 2 * bet);
            completed = true;
            gamePanel.getGameUI().setAnnounceMessage("You won " + 2 * bet + " chips");
            gamePanel.getSound().playMusic(6);
            end();
        } else if (playersNumber == winningNumber) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 36 * bet);
            gamePanel.getGameUI().setAnnounceMessage("You won " + 36 * bet + " chips");
            completed = true;
            gamePanel.getSound().playMusic(6);
            end();
        }
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    /**
     * Increases the bet amount by 5.
     * If the player doesn't have enough chips, the bet is set to the player's
     * chip count.
     * 
     */
    public void bet() {
        if (this.bet + 5 > gamePanel.getPlayer().getChipCount()) {
            this.bet = gamePanel.getPlayer().getChipCount();
            return;
        }
        this.bet += 5;
    }

    /**
     * Reduces the bet amount by 5.
     * If the bet amount is less than the minimum bet, the bet is set to the
     * minimum bet.
     * 
     */
    public void reduceBet() {
        if (this.bet - 5 < minimumBet) {
            this.bet = minimumBet;
            return;
        }
        this.bet -= 5;
    }

    public int getBet() {
        return bet;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public boolean isValidBet() {
        return validBet;
    }

    public void setValidBet(boolean validBet) {
        this.validBet = validBet;
    }
}
