package cz.cvut.fel.pjv.levels;

import cz.cvut.fel.pjv.GamePanel;

public class SecondLevel extends Level {

    private GamePanel gamePanel;
    private boolean miniGameFinished;
    public boolean gotLaid;

    public SecondLevel(GamePanel gamePanel) {
        this.miniGameFinished = true; // !TO BE CHANGED BECASE I DONT HAVE MINIGAME YET
        this.levelFinished = false;
        this.gamePanel = gamePanel;
        this.gotLaid = false;
        System.out.println("Second level created");
    }

    public void getLaid() {
        gotLaid = true;
        gamePanel.levelManager.checkLevelFinished();
        System.out.println("Got laid");
    }

    @Override
    public boolean checkLevelFinished() {
        if (gotLaid && gamePanel.levelManager.blackjack.getFinished()) {
            return true;
        }
        return false;
    }

}
