package Model;

import Controller.Game;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        game.summon();

        game.showBoard();
        game.setPhase(Phase.BATTLE);
        if (game.getOpposition().getBoard().isMonsterZoneEmpty()) {
            game.setSelectedPosition(this.getBoard().getMonsterCards().get(Game.convertIndex(1)));
            game.directAttack();
        }
        game.showBoard();
    }

    public void chooseBestCardInHand(Game game) {
        int bestAttack = 0;
        for (Card card : getHand()) {
            if(card instanceof MonsterCard){

            }
            else{

            }




            if ((card instanceof MonsterCard) && (((MonsterCard) card).getCardLevel() < 5)
                    && (bestAttack < ((MonsterCard) card).getAttack())) {
                game.setSelectedCardHand(card);
                bestAttack = ((MonsterCard) card).getAttack();
            }
        }

    }

}
