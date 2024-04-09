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
import java.awt.Color;

public class Main {
    public static void main(String[] args) {

        Image icon = new ImageIcon("phammin1\\2Dgame\\game\\src\\main\\resources\\icons\\game_icon.png").getImage();
        JFrame window = new JFrame("No intern, No home");
        window.setIconImage(icon);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.getContentPane().setBackground(new Color(139, 0, 0)); // Dark red color

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