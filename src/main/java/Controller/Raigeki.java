package Controller;

import Model.*;

public class Raigeki extends Effect {
    @Override
    public void activate(Game game) {
        for (int i = 0; i < 5; i++) {
            game.sendToGraveyard(game.getOpposition().getBoard().getMonsterCards().get(i), game.getOpposition());
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return !game.getOpposition().getBoard().isMonsterZoneEmpty();
    }
}
