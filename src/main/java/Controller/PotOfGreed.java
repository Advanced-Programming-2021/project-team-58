package Controller;

import Model.*;

public class PotOfGreed extends Effect {
    @Override
    public void activate(Game game) {
        for (int i = 0; i < 2; i++) {
            game.draw();
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return game.getTurnOfPlayer().getBoard().getMainDeck().size() >= 2;
    }
}
