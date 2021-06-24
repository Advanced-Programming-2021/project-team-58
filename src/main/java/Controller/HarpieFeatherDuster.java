package Controller;

import Model.*;


public class HarpieFeatherDuster extends Effect{
    @Override
    public void activate(Game game){
        for (int i = 0; i < 5; i++) {
            Position position = game.getOpposition().getBoard().getTrapAndSpellCards().get(i);
            if(!(position.getStatus().equals(StatusOfPosition.EMPTY))) {
                game.getOpposition().getBoard().removeCardFromSpellAndTrapCards(i);
            }
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
      if (!game.getOpposition().getBoard().getTrapAndSpellCards().isEmpty()) return true;
      return false;
    }
}
