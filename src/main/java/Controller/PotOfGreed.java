package Controller;

import Model.*;
import View.*;


import java.util.Random;

public class PotOfGreed extends Effect {
    @Override
    public void activate(Game game) {
        for (int i = 0; i < 2; i++) {
            game.draw();
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        if (game.getTurnOfPlayer().getBoard().getMainDeck().size()>=2)
            return true;
        return false;
    }
}
