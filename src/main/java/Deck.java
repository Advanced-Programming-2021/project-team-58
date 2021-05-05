import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static ArrayList<Deck> allDecks;
    private static HashMap<Player, Deck> playersAndTheirActivatedDeck;
    private String deckName;
    private ArrayList<Card> mainDeck = new ArrayList<Card>();
    private ArrayList<Card> sideDeck = new ArrayList<Card>();
    private int deckSize;
    private int mainDeckSize;
    private int sideDeckSize;

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

    public int getDeckSize() {
        return this.deckSize;
    }

    public void increaseDeckSize(int howMany) {
        this.deckSize += howMany;
    }

    public void decreaseDeckSize(int howMany) {
        this.deckSize -= howMany;
    }
    public int getMainDeckSize() {
        return this.mainDeckSize;
    }

    public void increaseMainDeckSize(int howMany) {
        this.mainDeckSize += howMany;
    }

    public void decreaseMainDeckSize(int howMany) {
        this.mainDeckSize -= howMany;
    }

    public int getSideDeckSize() {
        return this.sideDeckSize;
    }

    public void increaseSideDeckSize(int howMany) {
        this.sideDeckSize += howMany;
    }

    public void decreaseSideDeckSize(int howMany) {
        this.sideDeckSize -= howMany;
    }

    public void addCardToMainDeck(Card card) {
        this.mainDeck.add(card);
        increaseMainDeckSize(1);
    }

    public void removeCardFromMainDeck(Card card) {
        this.mainDeck.remove(card);
        decreaseMainDeckSize(1);
    }

    public void addCardToSideDeck(Card card) {
        this.sideDeck.add(card);
        increaseSideDeckSize(1);
    }

    public void removeCardFromSideDeck(Card card) {
        this.sideDeck.remove(card);
        decreaseSideDeckSize(1);
    }

    public static void addDeckToAllDecks(String deckName, String playerName) {
        allDecks.add(new Deck(deckName, playerName));
    }

    public static void removeDeckFromAllDecks(String deckName){
        allDecks.remove(getDeckByName(deckName));
    }

    public static Deck getDeckByName(String deckName) {
        for (Deck deck : allDecks)
            if (deck.getDeckName().equals(deckName))
                return deck;
        return null;
    }
    //dsf
    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }


}
