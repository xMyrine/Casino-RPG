package cz.cvut.fel.pjv.minigames;

public class Dices {
    private int rolledNumber1;
    public int rolledNumber2;
    private int bet;
    private int minimumBet = 5;
    public boolean validBet = true;
    private boolean completed = false;

    public Dices() {
        bet = minimumBet;
    }

    public void startD(int bettingNumber) {
    }

    public boolean end() {
        return completed;
    }

}
