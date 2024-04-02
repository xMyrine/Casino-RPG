package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[11];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");

    }

    /*
     * Load the tile images.
     */
    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall2.png"));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_d.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_tl.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_tr.png"));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_t.png"));

            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_bl.png"));

            tiles[7] = new Tile();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_br.png"));

            tiles[8] = new Tile();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_b.png"));

            tiles[9] = new Tile();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_l.png"));

            tiles[10] = new Tile();
            tiles[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/carp_r_r.png"));

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

    // public void loadMap(String mapName) {
    // try {
    // InputStream in = getClass().getResourceAsStream//(mapName);
    // BufferedReader br = new BufferedReader(new //InputStreamReader(in));
    // int row = 0;
    //
    // String line;
    // while ((line = br.readLine()) != null && row < //gamePanel.maxWorldRow) {
    // String[] numbers = line.split(" ");
    // for (int col = 0; col < gamePanel.maxWorldCol && //col < numbers.length;
    // col++) {
    // int num = Integer.parseInt(numbers[col]);
    // mapTileNum[col][row] = num;
    // }
    // row++;
    // }
    //
    // br.close();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

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

            // if (worldX - 7 * gamePanel.tileSize < gamePanel.player.worldX +
            // gamePanel.tileSize
            // && worldX + 7 * gamePanel.tileSize > gamePanel.player.worldX -
            // gamePanel.tileSize
            // && worldY - 5 * gamePanel.tileSize < gamePanel.player.worldY +
            // gamePanel.tileSize
            // && worldY + 5 * gamePanel.tileSize > gamePanel.player.worldY -
            // gamePanel.tileSize) {
            //
            // g.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize,
            // gamePanel.tileSize,
            // null);
            // }

            int screenTilesWidth = gamePanel.screenWidth / gamePanel.tileSize;
            int screenTilesHeight = gamePanel.screenHeight / gamePanel.tileSize;

            if (worldX - screenTilesWidth * gamePanel.tileSize < gamePanel.player.worldX + gamePanel.tileSize
                    && worldX + screenTilesWidth * gamePanel.tileSize > gamePanel.player.worldX - gamePanel.tileSize
                    && worldY - screenTilesHeight * gamePanel.tileSize < gamePanel.player.worldY + gamePanel.tileSize
                    && worldY + screenTilesHeight * gamePanel.tileSize > gamePanel.player.worldY - gamePanel.tileSize) {

                g.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            // g.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize,
            // gamePanel.tileSize, null);
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
