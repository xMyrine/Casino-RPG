package cz.cvut.fel.pjv.minigames.assets;

/**
 * Card is a class that represents a card in the blackjack minigame.
 * As already mention this code was heavily inspired by the code from the
 * following tutorial:
 * https://www.youtube.com/watch?v=GMdgjaDdOjI
 * 
 * @Author Minh Tu Pham
 */
public class Card {
    String value;
    String suit;
    public static final int CARD_WIDTH = 90;
    public static final int CARD_HEIGHT = 126;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Returns the string representation of the card.
     */
    @Override
    public String toString() {
        return value + "_" + suit;
    }

    /**
     * Returns the value of the card in a numerical representation.
     * 
     * @return the value of the card
     */
    public int getValue() {
        if ("acejackqueenking".contains(value)) {
            if (value.equals("ace")) {
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(value);
    }

    /**
     * Checks if the card is an ace.
     * 
     * @return true if the card is an ace, false otherwise
     */
    public boolean isAce() {
        return value.equals("ace");
    }

    /**
     * Returns the path to the image of the card.
     * 
     * @return the path to the image of the card
     */
    public String getImagePath() {
        return "/cards/" + toString() + ".png";
    }
}