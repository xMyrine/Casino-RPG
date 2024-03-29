package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles;
    protected int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];

        getTileImage();
        loadMap("/maps/map_layout1.txt");

    }

    /*
     * Load the tile images.
     */
    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_1.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_mid.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava.png"));
        } catch (Exception e) {
            e.printStackTrace();
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

            while (row < gamePanel.maxScreenRow) {
                String line = br.readLine();
                String[] tokens = line.split(" "); // split the line by spaces
                for (int x = 0; x < tokens.length; x++) { // for each number in the line
                    mapTileNum[x][row] = Integer.parseInt(tokens[x]);
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        int tileNum = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            tileNum = mapTileNum[col][row];
            g.drawImage(tiles[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;
            if (col == gamePanel.maxScreenCol) {
                col = 0;
                row++;
                x = 0;
                y += gamePanel.tileSize;
            }
        }
    }
}
