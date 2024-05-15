package cz.cvut.fel.pjv.saves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.LevelManager;

public class StorageLoader {
    protected GamePanel gamePanel;

    public StorageLoader(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream((new File("save.ser"))));
            Storage storage = new Storage();
            storage.level = LevelManager.getLevelNumber();
            storage.chips = gamePanel.getPlayer().getChipCount();
            storage.luck = gamePanel.getPlayer().getPlayerLuck();
            storage.playerX = gamePanel.getPlayer().getWorldX();
            storage.playerY = gamePanel.getPlayer().getWorldY();

            oos.writeObject(storage);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.ser")));
            Storage storage = (Storage) ois.readObject();
            LevelManager.setLevelNumber(storage.level);
            gamePanel.getPlayer().setChipCount(storage.chips);
            gamePanel.getPlayer().setPlayerLuck(storage.luck);
            gamePanel.getPlayer().setWorldX(storage.playerX);
            gamePanel.getPlayer().setWorldY(storage.playerY);

            gamePanel.getLevelManager().spawnObjectsAndNPC(gamePanel.getLevelManager().getLevelNumber());
            gamePanel.changeGameState(GamePanel.GAMESCREEN);

            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
