package cz.cvut.fel.pjv.minigames;

import cz.cvut.fel.pjv.GamePanel;

public class Roulette extends Minigame {

    private int number;
    private static int cost;
    private String colour;
    GamePanel gamePanel;

    public Roulette(GamePanel gp, int bet) {
        this.gamePanel = gp;
        Roulette.cost = 5;
    }

    @Override
    public void start() {
        gamePanel.player.setChipCount(gamePanel.player.getChipCount() - cost);
        Roulette.cost += 5;
        spin();
    }

    @Override
    public boolean end() {
        // TODO: implement
        return true;
    }

    private void spin() {
        number = (int) (Math.random() * 37);
        if (number == 0) {
            colour = "green";
        } else if (number % 2 == 0) {
            colour = "red";
        } else {
            colour = "black";
        }
    }

    public int getNumber() {
        return number;
    }

}
