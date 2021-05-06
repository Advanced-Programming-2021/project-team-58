import java.util.ArrayList;
import java.util.HashMap;

public class Deck implements Comparable<Deck> {
    private static ArrayList<Deck> allDecks = new ArrayList<Deck>();
    private static HashMap<Player, Deck> playersAndTheirActivatedDeck = new HashMap<Player, Deck>();
    private String deckName;
    private boolean isValid;
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private ArrayList<Card> mainDeck = new ArrayList<Card>();
    private ArrayList<Card> sideDeck = new ArrayList<Card>();
    private HashMap<Card, Integer> cardsAndTheirAmountInThisDeck = new HashMap<Card, Integer>();

    public Deck(String deckName, String playerName) {
        setDeckName(deckName);
        Player.getPlayerByUsername(playerName).addDeckToAllDecks(this);
        allDecks.add(this);
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }

    public boolean isValid() {
        if (this.mainDeck.size() >= 40 && this.mainDeck.size() <= 60 && this.sideDeck.size() <= 15) return true;
        else return false;
    }

    public int getDeckSize() {
        return this.allCards.size();
    }

    public int getMainDeckSize() {
        return this.mainDeck.size();
    }

    public int getSideDeckSize() {
        return this.sideDeck.size();
    }

    public void addCardToMainDeck(Card card) {
        this.mainDeck.add(card);
        this.allCards.add(card);
        increaseNumOfCardInDeck(card, 1);
    }

    public void removeCardFromMainDeck(Card card) {
        this.mainDeck.remove(card);
        this.allCards.remove(card);
        decreaseNumOfCardInDeck(card, 1);
    }

    public void addCardToSideDeck(Card card) {
        this.sideDeck.add(card);
        this.allCards.add(card);
        increaseNumOfCardInDeck(card, 1);
    }

    public void removeCardFromSideDeck(Card card) {
        this.sideDeck.remove(card);
        this.allCards.remove(card);
        decreaseNumOfCardInDeck(card, 1);
    }

    public static void addDeckToAllDecks(String deckName, String playerName) {
        allDecks.add(new Deck(deckName, playerName));
    }

    public static void removeDeckFromAllDecks(String deckName) {
        allDecks.remove(getDeckByName(deckName));
    }

    public static Deck getDeckByName(String deckName) {
        for (Deck deck : allDecks)
            if (deck.getDeckName().equals(deckName))
                return deck;
        return null;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public int getNumOfCardInDeck(Card card) {
        return cardsAndTheirAmountInThisDeck.get(card);
    }

    public void increaseNumOfCardInDeck(Card card, int howMany) {
        int theExistingNum = cardsAndTheirAmountInThisDeck.get(card);
        cardsAndTheirAmountInThisDeck.remove(card);
        cardsAndTheirAmountInThisDeck.put(card, theExistingNum + howMany);
    }

    public void decreaseNumOfCardInDeck(Card card, int howMany) {
        int theExistingNum = cardsAndTheirAmountInThisDeck.get(card);
        cardsAndTheirAmountInThisDeck.remove(card);
        cardsAndTheirAmountInThisDeck.put(card, theExistingNum - howMany);
    }

    public int compareTo(Deck anotherDeck) {
        return this.getDeckName().compareTo(anotherDeck.getDeckName());
    }
}
