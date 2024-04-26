package cz.cvut.fel.pjv.minigames;

import cz.cvut.fel.pjv.GamePanel;

public class Roulette {

    private int winningNumber;
    public int playersNumber;
    GamePanel gamePanel;
    private int bet;
    private int minimumBet = 5;
    public boolean validBet = true;
    private boolean completed = false;

    private static final int RED = 666;
    private static final int BLACK = 420;

    public Roulette(GamePanel gp) {
        this.gamePanel = gp;
        bet = minimumBet;
    }

    public void startR(int bettingNumber) {
        spin(bettingNumber);
    }

    public boolean end() {
        gamePanel.levelManager.firstLevel.setMiniGameFinished(completed);
        return completed;
    }

    private void spin(int bettingNumber) {
        gamePanel.player.setChipCount(gamePanel.player.getChipCount() - bet);
        winningNumber = (int) (Math.random() * 37);
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

        if (playersNumber == RED && winningNumber % 2 == 0) {
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() + 2 * bet);
            completed = true;
            gamePanel.ui.setAnnounceMessage("You won " + 2 * bet + " chips");
            end();
        } else if (playersNumber == BLACK && winningNumber % 2 != 0) {
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() + 2 * bet);
            completed = true;
            gamePanel.ui.setAnnounceMessage("You won " + 2 * bet + " chips");
            end();
        } else if (playersNumber == winningNumber) {
            gamePanel.player.setChipCount(gamePanel.player.getChipCount() + 36 * bet);
            gamePanel.ui.setAnnounceMessage("You won " + 36 * bet + " chips");
            completed = true;
            end();
        }
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    public void bet() {
        if (this.bet + 5 > gamePanel.player.getChipCount()) {
            this.bet = gamePanel.player.getChipCount();
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

}
