import java.util.ArrayList;

public class Board {
    private ArrayList<Card> graveYard;
    private ArrayList<Position> monsterCards;
    private ArrayList<Position> spellCard;
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

}
