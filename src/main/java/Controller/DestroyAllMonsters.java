package Controller;

import Model.*;
import View.*;


public class DestroyAllMonsters extends Effect{
//Effect of "Dark Hole"
    public void activate(Game game){
        for (int i = 0; i < 5; i++) {

            Position position1 = game.getTurnOfPlayer().getBoard().getMonsterCards().get(i);
            Position position2 = game.getOpposition().getBoard().getMonsterCards().get(i);

            if(!position1.getStatus().equals(StatusOfPosition.EMPTY)){
                game.getTurnOfPlayer().getBoard().addToGraveyard(position1.getCard());
                position1.setCard(null);
                position1.setStatus(StatusOfPosition.EMPTY);
            }
            if(!position2.getStatus().equals(StatusOfPosition.EMPTY)) {
                game.getOpposition().getBoard().addToGraveyard(position2.getCard());
                position2.setCard(null);
                position2.setStatus(StatusOfPosition.EMPTY);
            }
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return false;
    }
}
