
package cz.cvut.fel.pjv;

import javax.swing.JFrame;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.WindowConstants;

import javax.imageio.ImageIO;

/**
 * Main class of the game.
 * This class creates the window and starts the game.
 * 
 * @Author Minh Tu Pham
 */

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("No intern, No home");
        try {
            BufferedImage icon = ImageIO.read(Main.class.getResource("/icons/game_icon.png"));
            window.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        Logger logger = Logger.getLogger(Main.class.getName());
        logger.setLevel(Level.FINE);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        int desiredWidth = GamePanel.SCREEN_WIDTH;
        int desiredHeight = GamePanel.SCREEN_HEIGHT;
        window.pack(); // so I can see the game
        window.setLocationRelativeTo(null);
        window.setSize(desiredWidth, desiredHeight);
        window.getContentPane().setBackground(Color.RED);
        window.setVisible(true);

        gamePanel.setUpGame();

        gamePanel.startGameThread();
    }
}