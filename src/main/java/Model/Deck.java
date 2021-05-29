package Model;

import Controller.*;
import View.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck implements Comparable<Deck> {

    private static ArrayList<Deck> allDecks = new ArrayList<Deck>();
    private String deckName;
    private boolean isValid;
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private ArrayList<Card> mainDeck = new ArrayList<Card>();
    private ArrayList<Card> sideDeck = new ArrayList<Card>();

    public Deck(String deckName) {
        setDeckName(deckName);
        allDecks.add(this);
    }

//    public static void addDeckToAllDecks(Deck deck) {
//        allDecks.add(deck);
//    }

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
    }

    public void removeCardFromMainDeck(Card card) {
        this.mainDeck.remove(card);
        this.allCards.remove(card);
    }

    public void addCardToSideDeck(Card card) {
        this.sideDeck.add(card);
        this.allCards.add(card);
    }

    public void removeCardFromSideDeck(Card card) {
        this.sideDeck.remove(card);
        this.allCards.remove(card);
    }

    public static void removeDeckFromAllDecks(Deck deck) {
        allDecks.remove(deck);
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
        int counter = 0;
        for (Card c : allCards)
            if (c == card)
                counter++;
        return counter;
    }

    public int compareTo(Deck anotherDeck) {
        return this.getDeckName().compareTo(anotherDeck.getDeckName());
    }
}
