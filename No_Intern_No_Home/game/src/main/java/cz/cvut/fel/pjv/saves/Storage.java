package cz.cvut.fel.pjv.saves;

import java.io.Serializable;
import java.util.ArrayList;

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

    ArrayList<String> items = new ArrayList<>();
}
