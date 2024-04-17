package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];
    ArrayList<String> tileNames = new ArrayList<>();
    ArrayList<String> tileCollision = new ArrayList<>();

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        InputStream in = getClass().getResourceAsStream("/maps/data_test.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line;

        try {
            while ((line = br.readLine()) != null) {
                tileNames.add(line);
                tileCollision.add(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tiles = new Tile[tileNames.size()];
        getTileImage();

        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        // getTileImage();
        loadMap("/maps/test.txt");

    }

    /*
     * Load the tile images.
     */
    public void getTileImage() {
        // assignTiles(0, "floor", false);
        // assignTiles(1, "wall", true);
        // assignTiles(2, "carpet", false);

        String name;
        boolean collision;

        for (int i = 0; i < tiles.length; i++) {

            name = tileNames.get(i);
            if (tileCollision.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }
            assignTiles(i, name, collision);
        }

    }

    /*
     * Loads map from a txt file
     */
    public void loadMap(String mapName) {

        try {
            InputStream in = getClass().getResourceAsStream(mapName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int row = 0;
            int col = 0;
            int num;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();
                while (col < gamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Assigns the tile image and collision
     */
    private void assignTiles(int index, String path, boolean collision) {
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + path));
            tiles[index].image = Toolbox.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Draw the map adjusted to the player's position
     */
    public void draw(Graphics2D g) {

        int worldCol = 0;
        int worldRow = 0;
        int tileNum = 0;

        int worldX;
        int worldY;

        int screenX;
        int screenY;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            tileNum = mapTileNum[worldCol][worldRow];

            worldX = worldCol * gamePanel.tileSize;
            worldY = worldRow * gamePanel.tileSize;
            screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            int screenTilesWidth = gamePanel.screenWidth / gamePanel.tileSize;
            int screenTilesHeight = gamePanel.screenHeight / gamePanel.tileSize;

            if (worldX - screenTilesWidth * gamePanel.tileSize < gamePanel.player.worldX + gamePanel.tileSize
                    && worldX + screenTilesWidth * gamePanel.tileSize > gamePanel.player.worldX - gamePanel.tileSize
                    && worldY - screenTilesHeight * gamePanel.tileSize < gamePanel.player.worldY + gamePanel.tileSize
                    && worldY + screenTilesHeight * gamePanel.tileSize > gamePanel.player.worldY - gamePanel.tileSize) {

                g.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }

            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
