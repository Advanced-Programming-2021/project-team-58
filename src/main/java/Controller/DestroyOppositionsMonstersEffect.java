package Controller;

import Model.*;
import View.*;



public class DestroyOppositionsMonstersEffect {
    //effect of card "Raigeki"
    public static void activate(Game game) {
        for (int i = 0; i < 5; i++) {
            Position position = game.getOpposition().getBoard().getMonsterCards().get(i);
            if(!(position.getStatus().equals(StatusOfPosition.EMPTY))) {
                game.getOpposition().getBoard().addToGraveyard(position.getCard());
                position.setCard(null);
                position.setStatus(StatusOfPosition.EMPTY);
            }
        }
    }
}
