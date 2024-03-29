package cz.cvut.fel.pjv;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN settings
    final int originalTileSize = 16; // 16x16 pixels
    final int scale = 3; // 3x scale

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int fps = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // so that the panel can listen to key events
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
     * Sleep the game loop to achieve the desired FPS
     */
    // @Override
    // public void run() {
    // double drawInterval = 1000000000 / fps;
    // double nextDrawTime = System.nanoTime() + drawInterval;
    //
    // while (gameThread != null) {
    // try {
    // double waitTime = nextDrawTime - System.nanoTime();
    //
    // waitTime = waitTime / 1000000;
    // waitTime = waitTime < 0 ? 0 : waitTime;
    //
    // Thread.sleep((long) waitTime / 1000000);
    //
    // nextDrawTime += drawInterval;
    //
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // update();
    // repaint();
    // }
    // }

    /*
     * Delta accumulator method
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; // how much time has happened since the last update
            timer += currentTime - lastTime; // how much time has passed since the last update
            lastTime = currentTime; // update the last time

            if (delta >= 1) { // if enough time has passed
                update(); // update the game
                repaint(); // repaint the game
                delta--; // reset the delta
                drawCount++; // increment the draw count
            }

            if (timer >= 1000000000) { // if a second has passed
                System.out.println("FPS: " + drawCount); // print the draw count
                drawCount = 0; // reset the draw count
                timer = 0; // reset the timer
            }
        }
    }

    /*
     * This method is called by the game loop to update the game state
     * 
     */
    public void update() {
        player.update();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // draw the game
        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
