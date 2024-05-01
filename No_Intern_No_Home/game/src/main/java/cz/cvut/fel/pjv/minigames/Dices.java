package cz.cvut.fel.pjv.minigames;

public class Dices {
    private int winningNumber;
    public int playersNumber;
    private int bet;
    private int minimumBet = 5;
    public boolean validBet = true;
    private boolean completed = false;

    public Dices() {
        bet = minimumBet;
    }

    public void startD(int bettingNumber) {
        roll(bettingNumber);
    }

    public boolean end() {
        return completed;
    }

    private void roll(int bettingNumber) {
        winningNumber = (int) (Math.random() * 6) + 1;
        validBet = true;
        evaluate(bettingNumber);
        bet += minimumBet;
    }

    private void evaluate(int bettingNumber) {
        playersNumber = bettingNumber;
        if (playersNumber == winningNumber) {
            completed = true;
        }
    }
}
