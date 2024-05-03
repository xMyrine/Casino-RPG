/*
 * Main class of the game.
 * 
 * 
 * @Author Minh Tu Pham
 */

package cz.cvut.fel.pjv;

import javax.swing.JFrame;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame("No intern, No home");
        try {
            BufferedImage icon = ImageIO.read(Main.class.getResource("/icons/game_icon.png"));
            window.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        Logger logger = Logger.getLogger(Main.class.getName());
        logger.setLevel(Level.WARNING);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        int desiredWidth = gamePanel.screenWidth;
        int desiredHeight = gamePanel.screenHeight;
        window.pack(); // so I can see the gam e
        window.setLocationRelativeTo(null);
        window.setSize(desiredWidth, desiredHeight);
        window.getContentPane().setBackground(Color.RED);
        window.setVisible(true);

        gamePanel.setUpGame();

        gamePanel.startGameThread();
    }
}