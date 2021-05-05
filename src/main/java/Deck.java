import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private static ArrayList<Deck> allDecks;
    private static HashMap<Player, Deck> playersAndTheirActivatedDeck;
    private String deckName;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;

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

    public static void addDeckToAllDecks(String deckName, String playerName){
        allDecks.add(new Deck(deckName,playerName));
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

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public void addCardToMainDeck(Card card){
        mainDeck.add (card);
    }

    public void addCardToSideDeck(Card card){
        sideDeck.add (card);
    }
}
