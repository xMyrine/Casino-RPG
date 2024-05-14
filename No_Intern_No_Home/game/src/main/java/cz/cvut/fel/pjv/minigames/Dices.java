package cz.cvut.fel.pjv.minigames;

import java.util.Random;

import cz.cvut.fel.pjv.GamePanel;

public class Dices {
    private int rolledNumber1;
    private int rolledNumber2;
    private int bet;
    private int minimumBet = 5;
    private boolean completed = false;
    private Random rand = new Random();
    private GamePanel gamePanel;

    public Dices(GamePanel gp) {
        bet = minimumBet;
        rolledNumber1 = rand.nextInt(6) + 1;
        this.gamePanel = gp;
    }

    public void startD(int bettingNumber) {
        if (getBettingStatus()) {
            roll(bettingNumber);
        }
    }

    public boolean end() {
        return completed;
    }

    public int getBet() {
        return bet;
    }

    public boolean getBettingStatus() {
        if (gamePanel.getPlayer().getChipCount() >= bet) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
            return true;
        } else {
            gamePanel.getGameUI().setAnnounceMessage("Not enough chips");
            return false;
        }
    }

    private void roll(int bettingChoice) {
        gamePanel.getSound().playMusic(8);
        rolledNumber1 = rand.nextInt(6) + 1;
        rolledNumber2 = rand.nextInt(6) + 1;
        if (((bettingChoice == 1 && rolledNumber1 > rolledNumber2)
                || (bettingChoice == 0 && rolledNumber1 < rolledNumber2))
                && rand.nextFloat() < gamePanel.getPlayer().getPlayerLuck()) {
            rolledNumber1 = rand.nextInt(6) + 1;
            rolledNumber2 = rand.nextInt(6) + 1;
        }

        if ((bettingChoice == 1 && rolledNumber1 > rolledNumber2)
                || (bettingChoice == 0 && rolledNumber1 < rolledNumber2)) {
            completed = true;
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 2 * bet);
            gamePanel.getLevelManager().checkLevelFinished();
            gamePanel.getSound().playMusic(6);
        } else {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
        }
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

    public int getRolledNumber1() {
        return rolledNumber1;
    }

    public int getRolledNumber2() {
        return rolledNumber2;
    }

}
