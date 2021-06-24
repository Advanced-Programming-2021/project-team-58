package Controller;

import Model.*;
import View.*;


import java.util.ArrayList;
import java.util.Scanner;

public class MonsterReborn extends Effect {
    @Override
    public void activate(Game game) {
        System.out.println("choose between:\n" +
                "opponent graveyard\n" +
                "graveyard");
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();
            if (input.equals("opponents graveyard")) {
                if (game.getOpposition().getBoard().isMonsterInGraveYard()) {
                    game.specialSummon(input);
                    break;
                } else System.out.println("your opponent's graveyard has no monster\n" +
                        "you can use your own graveyard");
            } else if (input.equals("graveyard")) {
                if (game.getTurnOfPlayer().getBoard().isMonsterInGraveYard()) {
                    game.specialSummon("graveyard");
                    break;
                } else System.out.println("your graveyard has no monster\n" +
                        "you can use your opponent's graveyard");
            } else System.out.println("invalid command");
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        if (!game.getTurnOfPlayer().getBoard().isMonsterInGraveYard() ||
                !game.getOpposition().getBoard().isMonsterInGraveYard()) {
            return true;
        }
        return false;
    }
}
