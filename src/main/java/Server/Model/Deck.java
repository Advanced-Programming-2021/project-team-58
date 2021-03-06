package Server.Model;

import java.util.ArrayList;

public class Deck implements Comparable<Deck>, Cloneable {

    private static ArrayList<Deck> allDecks = new ArrayList<Deck>();
    private String deckName;
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private ArrayList<Card> mainDeck = new ArrayList<Card>();
    private ArrayList<Card> sideDeck = new ArrayList<Card>();

    public Deck(String deckName) {
        setDeckName(deckName);
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
        return false;
    }

    public void addCardToMainDeck(Card card) {
        this.mainDeck.add(card);
        this.allCards.add(card);
    }

    public void addCardToSideDeck(Card card) {
        this.sideDeck.add(card);
        this.allCards.add(card);
    }


    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public int compareTo(Deck anotherDeck) {
        return this.getDeckName().compareTo(anotherDeck.getDeckName());
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("hi");
        }
        return null;
    }
}
