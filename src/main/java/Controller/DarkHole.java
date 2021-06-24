package Controller;

import Model.*;


public class DarkHole extends Effect {
    @Override
    public void activate(Game game) {
        for (int i = 0; i < 5; i++) {
            game.getTurnOfPlayer().getBoard().removeCardFromMonsterCards(i);
            game.getOpposition().getBoard().removeCardFromMonsterCards(i);
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return true;
    }
}
