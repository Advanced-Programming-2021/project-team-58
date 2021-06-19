package Controller;

import Model.*;
import View.*;

import java.util.Random;

public class addCardToHandEffect extends Effect {
//Effect of supply squad
    public void activate(Game game){
        int mainDeckSize = game.getTurnOfPlayer().getBoard().getMainDeck().size();
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        game.getTurnOfPlayer().getHand().add(game.getTurnOfPlayer().getBoard().getMainDeck().get(index));
        game.getTurnOfPlayer().getBoard().getMainDeck().remove(index);
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return false;
    }
}
