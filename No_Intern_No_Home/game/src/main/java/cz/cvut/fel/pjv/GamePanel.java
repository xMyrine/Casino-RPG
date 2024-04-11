package cz.cvut.fel.pjv;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.tile.TileManager;
import cz.cvut.fel.pjv.objects.Object;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN settings
    final int originalTileSize = 16; // 16x16 pixels
    final int scale = 3; // 3x scale

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    protected int fps = 60;

    transient KeyHandler keyHandler = new KeyHandler();
    transient Thread gameThread;
    public Player player = new Player(this, keyHandler);
    public CollisionManager collisionManager = new CollisionManager(this);
    protected TileManager tileManager = new TileManager(this);
    public Object objects[] = new Object[20];
    public ObjectsSpawner objectsSpawner = new ObjectsSpawner(this);
    public Sound sound = new Sound();
    public LevelManager levelManager = new LevelManager(this);
    public UI ui = new UI(this);

    private Logger logger = Logger.getLogger(GamePanel.class.getName());

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // so that the panel can listen to key events
    }

    public void setUpGame() {
        objectsSpawner.spawnObjects();
        sound.playMusic();
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
                logger.info(String.format("FPS: %d", drawCount)); // print the draw count
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

        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                objects[i].draw(g2, this);
            }
        }

        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }

    public int countObjectsByClass(Class<?> clazz) {
        int count = 0;
        for (Object obj : objects) {
            if (obj != null && obj.getClass().equals(clazz)) {
                count++;
                logger.finest(String.format("Object of class %s found", clazz.getName()));
            }
        }
        return count;
    }
}
