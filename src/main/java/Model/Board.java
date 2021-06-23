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

    public boolean isMonsterZoneEmpty(){
        for (Position position : monsterCards) {
            if (!position.getStatus().equals(StatusOfPosition.EMPTY)) {
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

    public int getMinimumAttackPosition(){
        int min = 4000;
        int index = 0;
        int cardForce;
        for (int i = 0; i < monsterCards.size(); i++) {
            Position position = monsterCards.get(i);
            if(!position.getStatus().equals(StatusOfPosition.EMPTY)) {

                if(position.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)){
                    cardForce = ((MonsterCard) position.getCard()).getAttack();
                }
                else{
                    cardForce = ((MonsterCard) position.getCard()).getDefense();
                }
                if (cardForce < min) {
                    min = cardForce;
                    index = i;
                }
            }
        }
        return index;
    }

    public Position getMaximumPuver(){
        int max = 0;
        int cardAttack;
        Position bestPosition = monsterCards.get(0);
        for (int i = 0; i < monsterCards.size(); i++) {
            Position position = monsterCards.get(i);
            if(position.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)){
                cardAttack = ((MonsterCard)position.getCard()).getAttack();
                if(cardAttack >= max){
                    bestPosition = position;
                    max = cardAttack;
                }
            }
        }
        return bestPosition;
    }



    public void removeCard(int index){
        addToGraveyard(getMonsterCards().get(index).getCard());
        getMonsterCards().get(index).setCard(null);
        getMonsterCards().get(index).setStatus(StatusOfPosition.EMPTY);
    }
}
