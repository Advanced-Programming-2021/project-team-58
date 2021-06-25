package Controller;

import Model.*;
import View.*;


public class Raigeki extends Effect {
    //effect of card "Raigeki"
    @Override
    public void activate(Game game) {
        for (int i = 0; i < 5; i++) {
            game.sendToGraveyard(game.getOpposition().getBoard().getMonsterCards().get(i),game.getOpposition());
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return !game.getOpposition().getBoard().isMonsterZoneEmpty();
    }
}
