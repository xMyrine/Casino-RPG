package cz.cvut.fel.pjv.minigames;

public abstract class Minigame {

    private boolean completed;

    public Minigame() {
        this.completed = false;
    }

    public abstract void start();

    public abstract boolean end();

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        if (!this.completed) {
            this.completed = completed;
        }
    }

}
