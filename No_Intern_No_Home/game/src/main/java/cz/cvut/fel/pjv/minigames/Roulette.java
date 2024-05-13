package cz.cvut.fel.pjv.minigames;

import java.util.Random;

import cz.cvut.fel.pjv.GamePanel;

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

    public void startR(int bettingNumber) {
        spin(bettingNumber);
    }

    public boolean end() {
        gamePanel.getLevelManager().firstLevel.setMiniGameFinished(completed);
        return completed;
    }

    private void spin(int bettingNumber) {
        gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
        winningNumber = rand.nextInt(37);
        validBet = true;
        evaluate(bettingNumber);
        bet += minimumBet;
    }

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

    public void bet() {
        if (this.bet + 5 > gamePanel.getPlayer().getChipCount()) {
            this.bet = gamePanel.getPlayer().getChipCount();
            return;
        }
        this.bet += 5;
    }

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
