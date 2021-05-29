package Controller;

import Model.*;
import View.*;



import java.util.Random;
//effect of Card "Pot Of Greed"
public class DrawTwoCardsFromDeck {

    public static void activate(Game game) {
        for (int i = 0; i < 2; i++) {
            int mainDeckSize = game.getTurnOfPlayer().getBoard().getDeck().getMainDeck().size();
            Random rand = new Random();
            int index = rand.nextInt(mainDeckSize);
            game.getTurnOfPlayer().getHand().add(game.getTurnOfPlayer().getBoard().getDeck().getMainDeck().get(index));
            game.getTurnOfPlayer().getBoard().getDeck().getMainDeck().remove(index);
        }
    }
}
