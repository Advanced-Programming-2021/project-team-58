package Controller;

import Model.*;
import View.*;



public class UMIIRUKAEffect {

    public static void activate(Game game) {
        for (Position position : game.getTurnOfPlayer().getBoard().getMonsterCards())
            if (position.getCard() instanceof MonsterCard) {
                if (((MonsterCard) position.getCard()).getMonsterType().equals("Aqua")) {
//                    add 500 to their ATK
//                    reduce 400 from their DEF
                }
            }
    }
}
