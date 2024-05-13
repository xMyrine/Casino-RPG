package cz.cvut.fel.pjv;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;
import java.util.logging.Level;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.tile.TileManager;
import cz.cvut.fel.pjv.objects.GameObject;

public class GamePanel extends JPanel implements Runnable {
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;

    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;

    protected int fps = 60;

    protected transient KeyHandler keyHandler = new KeyHandler(this);
    private transient Thread gameThread;
    protected transient Player player = new Player(this, keyHandler);
    protected transient CollisionManager collisionManager = new CollisionManager(this);
    protected transient TileManager tileManager = new TileManager(this);
    protected transient ObjectsSpawner objectsSpawner = new ObjectsSpawner(this);
    protected transient Sound sound = new Sound();
    protected transient LevelManager levelManager = new LevelManager(this);
    protected transient UI gameUI = new UI(this);
    protected transient NPCManager npcManager = new NPCManager(this);

    private transient Logger logger = Logger.getLogger(GamePanel.class.getName());

    // Game objects
    protected transient GameObject[] objects = new GameObject[50];
    protected transient Entity[] entities = new Entity[20];

    // ! Game State
    private int gameState;
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;
    public static final int PAUSESCREEN = 2;
    public static final int DIALOGUESCREEN = 3;
    public static final int MINIGAMESCREEN = 4;
    public static final int CONTROLSSCREEN = 5;
    public static final int SHOPSCREEN = 6;
    public static final int CRAFTSCREEN = 7;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        logger.setLevel(Level.FINEST);
    }

    /**
     * Sets up the game
     * This method is used to set up the game
     * It spawns objects, plays music and sets the game state to the menu screen
     */
    public void setUpGame() {
        objectsSpawner.spawnObjects();
        sound.playMusic();
        gameState = MENUSCREEN;
        npcManager.spawnNPC();
        Entity.getGamePanelInstance(this);
    }

    /**
     * Starts the game thread
     * This method is used to start the game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public int getGameState() {
        return gameState;
    }

    /**
     * Delta accumulator method
     */
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / fps;
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
                logger.log(Level.INFO, "FPS: {0}", drawCount); // print the draw count
                drawCount = 0; // reset the draw count
                timer = 0; // reset the timer
            }
        }
    }

    /**
     * This method is called by the game loop to update the game state
     * 
     */
    public void update() {
        if (gameState == GAMESCREEN) {
            player.update();
            objectsSpawner.update();
            for (int i = 0; i < entities.length; i++) {
                if (entities[i] != null) {
                    entities[i].update();
                }
            }
            npcManager.update();
        } else if (gameState == PAUSESCREEN) {
            // do nothing
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == MENUSCREEN) {
            gameUI.draw(g2);
        } else {
            tileManager.draw(g2);
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] != null) {
                    objects[i].draw(g2, this);
                }
            }

            player.draw(g2);

            for (int i = 0; i < entities.length; i++) {
                if (entities[i] != null) {
                    entities[i].draw(g2);
                }
            }

            gameUI.draw(g2);
        }
        // draw the game

        g2.dispose();

    }

    /**
     * Counts the number of objects of a given class
     * 
     * @param clazz class of objects
     * @return count of objects
     */
    public int countObjectsByClass(Class<?> clazz) {
        int count = 0;
        for (GameObject obj : objects) {
            if (obj != null && obj.getClass().equals(clazz)) {
                count++;
                logger.log(Level.FINEST, "Object of class {0} found", clazz.getName());
            }
        }
        return count;
    }

    /**
     * Changes the game state
     * This method is used to change the game state
     * Every game state has a different behaviour and different objects are drawn
     * 
     * @param i new game state
     */
    public void changeGameState(int i) {
        gameState = i;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Player getPlayer() {
        return player;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public Sound getSound() {
        return sound;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public UI getGameUI() {
        return gameUI;
    }

    public GameObject getGameObject(int i) {
        return objects[i];
    }

    public void setGameObject(int i, GameObject obj) {
        objects[i] = obj;
    }

    public Entity getEntity(int i) {
        return entities[i];
    }

    public void setEntity(int i, Entity entity) {
        entities[i] = entity;
    }

    public Entity[] getEntities() {
        return entities;
    }
}
