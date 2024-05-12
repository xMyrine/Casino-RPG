package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.Toolbox;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];
    ArrayList<String> tileNames = new ArrayList<>();
    ArrayList<String> tileCollision = new ArrayList<>();

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        InputStream in = getClass().getResourceAsStream("/maps/data_test2.txt");
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

        loadMap("/maps/test.txt");

    }

    /*
     * Load the tile images.
     */
    public void getTileImage() {
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
                    String[] numbers = line.split(" ");

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
            tiles[index].image = Toolbox.scaleImage(tiles[index].image, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
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

            worldX = worldCol * GamePanel.TILE_SIZE;
            worldY = worldRow * GamePanel.TILE_SIZE;

            screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            int screenTilesWidth = gamePanel.screenWidth / GamePanel.TILE_SIZE;
            int screenTilesHeight = gamePanel.screenHeight / GamePanel.TILE_SIZE;

            if (worldX - screenTilesWidth * GamePanel.TILE_SIZE < gamePanel.player.worldX + GamePanel.TILE_SIZE
                    && worldX + screenTilesWidth * GamePanel.TILE_SIZE > gamePanel.player.worldX - GamePanel.TILE_SIZE
                    && worldY - screenTilesHeight * GamePanel.TILE_SIZE < gamePanel.player.worldY + GamePanel.TILE_SIZE
                    && worldY + screenTilesHeight * GamePanel.TILE_SIZE > gamePanel.player.worldY
                            - GamePanel.TILE_SIZE) {

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
