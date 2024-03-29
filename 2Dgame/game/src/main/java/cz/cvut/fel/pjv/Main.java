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

        Image icon = new ImageIcon("phammin1\\2Dgame\\game\\src\\main\\resources\\icons\\game_icon.png").getImage();
        JFrame window = new JFrame("Casino Game");
        window.setIconImage(icon);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // so I can see the game
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}