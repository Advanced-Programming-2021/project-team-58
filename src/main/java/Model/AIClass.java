package Model;

import Controller.Game;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AIClass extends Player {

    public AIClass() {
        setUsername("AI");
        setPassword("*****");
        setNickname("AI");
        setBoard(new Board());
        createAnActiveDeck();
    }

    public void createAnActiveDeck() {

        String str = null;
        try {
            str = new String(Files.readAllBytes(Paths.get("AIDeck.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Deck> decks = new YaGson().fromJson(str, new TypeToken<ArrayList<Deck>>() {
        }.getType());
        setActiveDeck(decks.get(0));
    }

    public void play(Game game) {
        game.draw();
        game.setPhase(Phase.MAIN);
        chooseBestCardInHand(game);
        if(game.getSelectedCardHand() != null) {
            if (game.getSelectedCardHand() instanceof MonsterCard)
                game.summon();
            else
                game.set();
        }

        game.showBoard();
        game.setPhase(Phase.BATTLE);
        if (game.getOpposition().getBoard().isMonsterZoneEmpty()) {
            game.setSelectedPosition(this.getBoard().getMonsterCards().get(Game.convertIndex(1)));
            game.directAttack();
        }
        game.showBoard();
    }

    public void chooseBestCardInHand(Game game) {
        Collections.sort(getHand(), new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                if (card1 instanceof MonsterCard && card2 instanceof MonsterCard)
                    return ((MonsterCard) card2).getAttack() - ((MonsterCard) card1).getAttack();
                if (card2 instanceof TrapAndSpellCard && card1 instanceof MonsterCard) return -1;
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        for (int i = 0; i < getHand().size(); i++) {
            if(getHand().get(i) instanceof TrapAndSpellCard){
                game.setSelectedCardHand(getHand().get(i));
                break;
            }
            else{
                if(((MonsterCard) getHand().get(i)).getCardLevel() < 5){
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                }
                else if (isSuitableForTribute((MonsterCard) getHand().get(i))) {
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                }
            }
        }
    }

    public boolean isSuitableForTribute(MonsterCard card) {
        if (card.getCardLevel() < 7) {
            return (getBoard().cardsInMonsterZone() >= 1);
        } else {
            return (getBoard().cardsInMonsterZone() >= 2);
        }
    }

}
