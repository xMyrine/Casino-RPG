package cz.cvut.fel.pjv.minigames;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.minigames.assets.Card;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Blackjack is a minigame that the player plays in the second level.
 * Player cannot modify the bet amount yet. It will be implemented in the
 * future.
 * It follows regular blackjack rules.
 * 
 * This class was heavily inspired by the following tutorial:
 * https://www.youtube.com/watch?v=GMdgjaDdOjI
 * 
 * NOTE: This code was written even before the assignment was given as a side
 * project.
 * 
 * @Author Minh Tu Pham
 */
@Data
public class Blackjack {
    GamePanel gamePanel;
    ArrayList<Card> deck;
    Random random = new Random();
    private boolean finished = false;
    private int bet = 25;
    private boolean chipsDeducted = false;

    // Dealer
    private Card hiddenCard;
    private ArrayList<Card> dealerHand;
    private int dealerHandValue;
    private int dealerAceCount;

    // Player
    private ArrayList<Card> playerHand;
    private int playerHandValue;
    private int playerAceCount;

    private BufferedImage hitButton;
    private BufferedImage standButton;
    private BufferedImage cardBack;

    private boolean hitEnabled = true;
    private boolean standEnabled = true;

    public Blackjack(GamePanel gp) {
        startGame();
        this.gamePanel = gp;
        hitEnabled = true;
        try {
            hitButton = ImageIO.read(getClass().getResourceAsStream("/buttons/hit_button.png"));
            standButton = ImageIO.read(getClass().getResourceAsStream("/buttons/stand_button.png"));
            cardBack = ImageIO.read(getClass().getResourceAsStream("/cards/back_red_basic.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Card getHiddenCard() {
        return hiddenCard;
    }

    public List<Card> getDealerHand() {
        return dealerHand;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public BufferedImage getHitButton() {
        return hitButton;
    }

    public BufferedImage getStandButton() {
        return standButton;
    }

    public BufferedImage getCardBack() {
        return cardBack;
    }

    /**
     * Starts the game by building the deck, shuffling it, and dealing the first two
     * cards to the dealer and the player
     */
    public void startGame() {
        buildDeck();
        shuffleDeck();

        dealerHand = new ArrayList<>();
        dealerAceCount = 0;
        dealerHandValue = 0;

        hiddenCard = deck.remove(deck.size() - 1);
        dealerHandValue += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerHandValue += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        playerHand = new ArrayList<>();
        playerHandValue = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerHandValue += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }
    }

    /**
     * Builds a deck of 52 cards
     */
    private void buildDeck() {
        deck = new ArrayList<>();
        String[] suits = { "hearts", "diamonds", "clubs", "spades" };
        String[] values = { "ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king" };

        for (String suit : suits) {
            for (String value : values) {
                deck.add(new Card(value, suit));
            }
        }
    }

    /**
     * Shuffles the deck
     */
    private void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int randomIndex = random.nextInt(deck.size());
            Card temp = deck.get(i);
            deck.set(i, deck.get(randomIndex));
            deck.set(randomIndex, temp);
        }
    }

    /**
     * Reduces the value of the hand by 10 if the hand is over 21 and there is an
     * ace
     * 
     * @return the value of the hand
     */
    private int reduceAce() {
        while (playerHandValue > 21 && playerAceCount > 0) {
            playerHandValue -= 10;
            playerAceCount--;
        }
        return playerHandValue;

    }

    /**
     * Reduces the value of the dealer's hand by 10 if the hand is over 21 and there
     * is an ace
     * 
     * @return the value of the dealer's hand
     */
    private int reduceDealerAce() {
        while (dealerHandValue > 21 && dealerAceCount > 0) {
            dealerHandValue -= 10;
            dealerAceCount--;
        }
        return dealerHandValue;
    }

    /**
     * Player hits and draws a card
     */
    public void hit() {
        Card card = deck.remove(deck.size() - 1);
        playerHandValue += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        if (reduceAce() > 21) {
            hitEnabled = false;
        }
    }

    /**
     * Player stands and dealer draws cards until the value is 17 or higher
     */
    public void stand() {
        hitEnabled = false;
        standEnabled = false;
        while (dealerHandValue < 17) {
            Card card = deck.remove(deck.size() - 1);
            dealerHandValue += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);
        }
        evaluate();
    }

    public boolean getHitEnabled() {
        return hitEnabled;
    }

    public boolean getStandEnabled() {
        return standEnabled;
    }

    public boolean getFinished() {
        return finished;
    }

    /**
     * Evaluates the game and determines the winner
     */
    public void evaluate() {
        if (gamePanel.getPlayer().getChipCount() - bet < 0) {
            gamePanel.getGameUI().setAnnounceMessage("You don't have enough chips");
            return;
        }
        if (!chipsDeducted) {
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() - bet);
            chipsDeducted = true;
        }
        dealerHandValue = reduceDealerAce();
        playerHandValue = reduceAce();

        if (playerHandValue > 21) {
            gamePanel.getGameUI().setAnnounceMessage("You busted");
        } else if (dealerHandValue > 21) {
            gamePanel.getGameUI().setAnnounceMessage("Dealer busted");
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 2 * 25);
            gamePanel.getSound().playMusic(6);
            finished = true;
        } else if (playerHandValue > dealerHandValue) {
            gamePanel.getGameUI().setAnnounceMessage("You won " + 2 * 25 + " chips");
            gamePanel.getPlayer().setChipCount(gamePanel.getPlayer().getChipCount() + 2 * 25);
            gamePanel.getSound().playMusic(6);
            finished = true;
        } else if (playerHandValue < dealerHandValue) {
            gamePanel.getGameUI().setAnnounceMessage("You lost");
        } else {
            gamePanel.getGameUI().setAnnounceMessage("It's a tie");
        }
    }

    /**
     * Resets the game
     */
    public void reset() {
        hitEnabled = true;
        standEnabled = true;
        chipsDeducted = false;
        startGame();
    }

}
