package cz.cvut.fel.pjv.saves;

import java.io.Serializable;

/**
 * Storage is a class that stores the game state.
 * It stores the level, the number of chips, the player's position, the player's
 * fragments, and the player's items.
 * 
 * @Author Minh Tu Pham
 */
public class Storage implements Serializable {
    protected int level;
    protected int chips;
    protected float luck;
    protected int playerX;
    protected int playerY;
    protected int playerCigarFragment;
    protected int playerGunFragment;
    protected int playerCardsFragment;
    protected int gunCount;
    protected int cigaretteCount;
    protected int cardCount;
}
