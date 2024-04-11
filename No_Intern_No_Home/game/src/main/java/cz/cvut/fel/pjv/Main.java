/*
 * Main class of the game.
 * 
 * 
 * @Author Minh Tu Pham
 */

package cz.cvut.fel.pjv;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Main {
    public static void main(String[] args) {

        Image icon = new ImageIcon("src/main/resources/icons/game_icon.png").getImage();
        JFrame window = new JFrame("No intern, No home");
        window.setIconImage(icon);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        int desiredWidth = gamePanel.screenWidth;
        int desiredHeight = gamePanel.screenHeight;
        window.pack(); // so I can see the game
        window.setLocationRelativeTo(null);
        window.setSize(desiredWidth, desiredHeight);
        window.setVisible(true);

        gamePanel.setUpGame();

        gamePanel.startGameThread();
    }
}