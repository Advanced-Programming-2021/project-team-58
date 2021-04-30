import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private static ArrayList<Card> allDecks;
    private String deckName;
    private HashMap<Player,Deck> playersAndTheirActivatedDeck;

    public Deck(String deckName, String playerName) {
        this.deckName = deckName;
    }
    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public Deck getDeckByName(String deckName){

    }

    public ArrayList<Card> getMainDeck(){
        return this.mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

}
