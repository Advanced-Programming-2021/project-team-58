import java.util.ArrayList;

public class Board {
    private ArrayList<Card> graveYard = new ArrayList<>();
    private ArrayList<Position> monsterCards = new ArrayList<>();
    private ArrayList<Position> spellCard = new ArrayList<>();
    private Deck deck;

    public Board(){
        createMonsterCardPosition();
        createSpellCardPosition();
    }

    public ArrayList<Position> getMonsterCards() {
        return monsterCards;
    }

    public ArrayList<Position> getSpellCard() {
        return spellCard;
    }

    public void createMonsterCardPosition(){
        for(int i=0 ; i<5 ; i++){
            Position x = new Position(StatusOfPosition.EMPTY , i );
            monsterCards.add(x);
        }
    }

    public void createSpellCardPosition(){
        for(int i=0 ; i<5 ; i++){
            Position x = new Position(StatusOfPosition.EMPTY , i );
            spellCard.add(x);
        }
    }

    public void addToGraveyard(Card card){
        graveYard.add(card);
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }
}
