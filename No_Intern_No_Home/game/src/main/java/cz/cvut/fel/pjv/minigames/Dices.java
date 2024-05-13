package cz.cvut.fel.pjv.minigames;

import java.util.Random;

import cz.cvut.fel.pjv.GamePanel;

public class Dices {
    private int rolledNumber1;
    private int rolledNumber2;
    private int bet;
    private int minimumBet = 5;
    private boolean completed = false;
    Random rand = new Random();
    private GamePanel gamePanel;

    public Dices(GamePanel gp) {
        bet = minimumBet;
        rolledNumber1 = rand.nextInt(6) + 1;
        this.gamePanel = gp;
    }

    public void startD(int bettingNumber) {
        if (getBet()) {
            roll(bettingNumber);
        }
    }

    public boolean end() {
        return completed;
    }

    public boolean getBet() {
        if (gamePanel.getPlayer().getChipCount() >= bet) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
            return true;
        } else {
            gamePanel.getGameUI().setAnnounceMessage("Not enough chips");
            return false;
        }
    }

    private void roll(int bettingChoice) {
        rolledNumber2 = rand.nextInt(6) + 1;
        if (bettingChoice == 0) {
            if (rolledNumber1 > rolledNumber2) {
                completed = true;
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 2 * bet);
            } else {
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
            }
        } else if (bettingChoice == 1) {
            if (rolledNumber1 < rolledNumber2) {
                completed = true;
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 2 * bet);
            } else {
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
            }
        } else if (bettingChoice == 2) {
            if (rolledNumber1 == rolledNumber2) {
                completed = true;
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 6 * bet);
            } else {
                gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
            }
        }
    }

    public void restart() {
        rolledNumber1 = rand.nextInt(6) + 1;
        completed = false;
        startD(bet);
    }

    public void increaseBet() {
        bet += minimumBet;
    }

    public void decreaseBet() {
        bet -= minimumBet;
    }

    public int getRolledNumber1() {
        return rolledNumber1;
    }

    public int getRolledNumber2() {
        return rolledNumber2;
    }

}
