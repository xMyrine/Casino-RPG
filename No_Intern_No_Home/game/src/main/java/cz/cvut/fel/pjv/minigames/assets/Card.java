package cz.cvut.fel.pjv.minigames.assets;

public class Card {
    String value;
    String suit;
    public static final int cardWidth = 90;
    public static final int cardHeight = 126;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public String toString() {
        return value + "_" + suit;
    }

    public int getValue() {
        if ("acejackqueenking".contains(value)) {
            if (value.equals("ace")) {
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(value);
    }

    public boolean isAce() {
        return value.equals("ace");
    }

    public String getImagePath() {
        return "/cards/" + toString() + ".png";
    }
}