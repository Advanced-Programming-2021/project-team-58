package Model;

import Controller.*;
import View.*;

import java.util.ArrayList;

public class Board {
    private ArrayList<Card> graveYard = new ArrayList<Card>();
    private ArrayList<Position> monsterCards = new ArrayList<Position>();
    private ArrayList<Position> trapAndSpellCard = new ArrayList<Position>();
    private ArrayList<Card> mainDeck = new ArrayList<>();

    public Board() {
        createMonsterCardPosition();
        createSpellCardPosition();
    }

    public ArrayList<Position> getMonsterCards() {
        return monsterCards;
    }

    public ArrayList<Position> getTrapAndSpellCard() {
        return trapAndSpellCard;
    }

    public void createMonsterCardPosition() {
        for (int i = 0; i < 5; i++) {
            Position x = new Position(StatusOfPosition.EMPTY, i);
            monsterCards.add(x);
        }
    }

    public void createSpellCardPosition() {
        for (int i = 0; i < 5; i++) {
            Position x = new Position(StatusOfPosition.EMPTY, i);
            trapAndSpellCard.add(x);
        }
    }

    public boolean isMonsterZoneFull() {
        for (Position position : monsterCards) {
            if (position.getStatus().equals(StatusOfPosition.EMPTY)) {
                return false;
            }
        }
        return true;
    }

    public int cardsInMonsterZone() {
        int i = 0;
        for (Position position : monsterCards) {
            if (!position.getStatus().equals(StatusOfPosition.EMPTY)) {
                i++;
            }
        }
        return i;
    }

    public boolean isTrapAndSpellZoneFull() {
        for (Position position : trapAndSpellCard) {
            if (position.getStatus().equals(StatusOfPosition.EMPTY)) {
                return false;
            }
        }
        return true;
    }

    public void addToGraveyard(Card card) {
        graveYard.add(card);
    }

//    public void setDeck(Deck deck) {
//        this.deck = deck;
//    }
//
//    public Deck getDeck() {
//        return deck;
//    }

    public ArrayList<Card> getMainDeck() {
        return this.mainDeck;
    }

    public void setMainDeck(ArrayList<Card> mainDeck) {
         this.mainDeck = mainDeck;
    }

    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }

    public void clearBoard(){
        graveYard.clear();
        for (Position position : monsterCards) {
            position.setStatus(StatusOfPosition.EMPTY);
            position.setCard(null);
        }
        for (Position position : trapAndSpellCard) {
            position.setStatus(StatusOfPosition.EMPTY);
            position.setCard(null);
        }
    }
}
