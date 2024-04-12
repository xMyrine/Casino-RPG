package cz.cvut.fel.pjv.items;

public abstract class Items {

    public String name;
    private final int id;

    public Items(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected abstract void use();

}
