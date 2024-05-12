package cz.cvut.fel.pjv.items;

import cz.cvut.fel.pjv.minigames.Pokermon;

public class Gun extends Items {
    private Pokermon pokermon;

    public Gun(Pokermon pokermon) {
        this.pokermon = pokermon;
    }

    @Override
    public void use() {
        pokermon.shoot();
    }

}
