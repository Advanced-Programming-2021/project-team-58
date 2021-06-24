package Controller;

import Model.*;
import View.*;


public class Raigeki extends Effect {
    //effect of card "Raigeki"
    @Override
    public void activate(Game game) {
        for (int i = 0; i < 5; i++) {
            game.getOpposition().getBoard().removeCardFromMonsterCards(i);
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return !game.getOpposition().getBoard().isMonsterZoneEmpty();
    }
}
