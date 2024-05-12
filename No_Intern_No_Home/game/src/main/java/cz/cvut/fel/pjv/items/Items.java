package cz.cvut.fel.pjv.items;

public abstract class Items {

    private String name;
    private final int id;

    protected Items(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected abstract void use();

}
