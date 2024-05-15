package cz.cvut.fel.pjv.saves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.LevelManager;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.items.Cigarette;
import cz.cvut.fel.pjv.items.Gun;
import cz.cvut.fel.pjv.items.PlayingCards;

/**
 * StorageLoader is a class that saves and loads the game state.
 * It saves the level, the number of chips, the player's position, the player's
 * fragments, and the player's items.
 * 
 * @Author Minh Tu Pham
 */
public class StorageLoader {
    protected GamePanel gamePanel;

    public StorageLoader(GamePanel gp) {
        this.gamePanel = gp;
    }

    /**
     * Saves the game state to a file.
     * It saves it to the file save.ser.
     * 
     */
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.ser")))) {
            Storage storage = new Storage();
            storage.level = LevelManager.getLevelNumber();
            storage.chips = gamePanel.getPlayer().getChipCount();
            storage.luck = gamePanel.getPlayer().getPlayerLuck();
            storage.playerX = gamePanel.getPlayer().getWorldX();
            storage.playerY = gamePanel.getPlayer().getWorldY();
            storage.playerCigarFragment = gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.CIGAR_INDEX);
            storage.playerGunFragment = gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.GUN_INDEX);
            storage.playerCardsFragment = gamePanel.getPlayer().getSpecialItemsFragmentCount(Player.CARDS_INDEX);
            storage.gunCount = gamePanel.getPlayer().countInstances(Cigarette.class);
            storage.cigaretteCount = gamePanel.getPlayer().countInstances(Cigarette.class);
            storage.cardCount = gamePanel.getPlayer().countInstances(Cigarette.class);

            oos.writeObject(storage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game state from a file.
     * It loads it from the file save.ser.
     * If the file does not exist, it prints a stack trace.
     * 
     */
    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.ser")))) {
            Storage storage = (Storage) ois.readObject();
            LevelManager.setLevelNumber(storage.level);
            gamePanel.getPlayer().setChipCount(storage.chips);
            gamePanel.getPlayer().setPlayerLuck(storage.luck);
            gamePanel.getPlayer().setWorldX(storage.playerX);
            gamePanel.getPlayer().setWorldY(storage.playerY);
            gamePanel.getPlayer().setSpecialItemsFragmentCount(Player.CIGAR_INDEX, storage.playerCigarFragment);
            gamePanel.getPlayer().setSpecialItemsFragmentCount(Player.GUN_INDEX, storage.playerGunFragment);
            gamePanel.getPlayer().setSpecialItemsFragmentCount(Player.CARDS_INDEX, storage.playerCardsFragment);
            gamePanel.getLevelManager().spawnObjectsAndNPC(LevelManager.getLevelNumber());
            addItems(storage);
            gamePanel.changeGameState(GamePanel.GAMESCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the items to the player's inventory.
     * It adds the items based on the storage object.
     * 
     * @param storage
     */
    private void addItems(Storage storage) {
        for (int i = 0; i < storage.cigaretteCount; i++) {
            gamePanel.getPlayer()
                    .addItem(new Cigarette(gamePanel.getPlayer()));
        }
        for (int i = 0; i < storage.gunCount; i++) {
            gamePanel.getPlayer()
                    .addItem(new Gun(gamePanel.getLevelManager().getPokermon()));
        }
        for (int i = 0; i < storage.cardCount; i++) {
            gamePanel.getPlayer()
                    .addItem(new PlayingCards());
        }
    }

}
