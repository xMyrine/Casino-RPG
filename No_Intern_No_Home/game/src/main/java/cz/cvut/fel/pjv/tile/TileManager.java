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
    private Tile[] tiles;
    private int[][] mapTileNum;
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

        mapTileNum = new int[GamePanel.MAX_WORLD_COL][GamePanel.MAX_WORLD_COL];

        loadMap("/maps/test.txt");

    }

    /**
     * Get the tile number at the given index.
     * 
     * @param x
     * @param y
     * @return Tile number at the given index
     */
    public int getMapTileNum(int x, int y) {
        return mapTileNum[x][y];
    }

    /**
     * Get the tile at the given index.
     * 
     * @param index
     * @return Tile at the given index
     */
    public Tile getTile(int index) {
        return tiles[index];
    }

    /**
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

    /**
     * Loads map from a txt file
     * This method reads the map from a txt file and assigns the tile number to the
     * mapTileNum array
     * 
     * @param mapName Name of the map in the resources folder
     */
    public void loadMap(String mapName) {

        try {
            InputStream in = getClass().getResourceAsStream(mapName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int row = 0;
            int col = 0;
            int num;

            while (col < GamePanel.MAX_WORLD_COL && row < GamePanel.MAX_WORLD_COL) {
                String line = br.readLine();
                while (col < GamePanel.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == GamePanel.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns the tile image and collision
     * This method assigns the tile image and collision to the tile array
     * 
     * @param index     Index of the tile
     * @param path      Path to the image
     * @param collision Boolean value if the tile is solid
     */
    private void assignTiles(int index, String path, boolean collision) {
        try {
            tiles[index] = new Tile();
            tiles[index].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/" + path)));
            tiles[index]
                    .setImage(Toolbox.scaleImage(tiles[index].getImage(), GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
            tiles[index].setSolid(collision);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the map adjusted to the player's position
     * 
     * @param g Graphics2D
     */
    public void draw(Graphics2D g) {

        int worldCol = 0;
        int worldRow = 0;
        int tileNum = 0;

        int worldX;
        int worldY;

        int screenX;
        int screenY;

        while (worldCol < GamePanel.MAX_WORLD_COL && worldRow < GamePanel.MAX_WORLD_COL) {
            tileNum = mapTileNum[worldCol][worldRow];

            worldX = worldCol * GamePanel.TILE_SIZE;
            worldY = worldRow * GamePanel.TILE_SIZE;

            screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

            int screenTilesWidth = GamePanel.SCREEN_WIDTH / GamePanel.TILE_SIZE;
            int screenTilesHeight = GamePanel.SCREEN_HEIGHT / GamePanel.TILE_SIZE;

            if (worldX - screenTilesWidth * GamePanel.TILE_SIZE < gamePanel.getPlayer().getWorldX()
                    + GamePanel.TILE_SIZE
                    && worldX + screenTilesWidth * GamePanel.TILE_SIZE > gamePanel.getPlayer().getWorldX()
                            - GamePanel.TILE_SIZE
                    && worldY - screenTilesHeight * GamePanel.TILE_SIZE < gamePanel.getPlayer().getWorldY()
                            + GamePanel.TILE_SIZE
                    && worldY + screenTilesHeight * GamePanel.TILE_SIZE > gamePanel.getPlayer().getWorldY()
                            - GamePanel.TILE_SIZE) {

                g.drawImage(tiles[tileNum].getImage(), screenX, screenY, null);
            }

            worldCol++;
            if (worldCol == GamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
