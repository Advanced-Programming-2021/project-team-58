package Controller;

import Model.Effect;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwinTwisters extends Effect {
    @Override
    public void activate(Game game) {
        System.out.println("Please select a card in your hand");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            Matcher matchSelect = getCommandMatcher(input, "^select --(hand) (--opponent )*([0-9]+)$");
            if (matchSelect.find()) {
                game.select(matchSelect);
                game.getTurnOfPlayer().getHand().remove(game.getSelectedCardHand());
                game.getTurnOfPlayer().getBoard().addToGraveyard(game.getSelectedCardHand());
                break;
            } else System.out.println("invalid command");
        }
        int num = 0;
        while (num < 2) {
            System.out.println("Please select a spell or trap card in the game mat");
            String input = scanner.nextLine();
            Matcher matchSelect = getCommandMatcher(input, "^select --(spell) (--opponent )*([0-9]+)$");
            if (matchSelect.find()) {
               game.select(matchSelect);
               game.getTurnOfPlayer().getBoard().removeCardFromSpellAndTrapCards(game.getSelectedPosition().getIndex());
               num++;
            } else System.out.println("invalid command");
        }
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        if (!game.getTurnOfPlayer().getHand().isEmpty() && getNumOfTrapOrSpellCardsInTheMat(game) >= 2) return true;
        return false;
    }

    public int getNumOfTrapOrSpellCardsInTheMat(Game game) {
        return game.getOpposition().getBoard().cardsInTrapAndSpellZone() + game.getTurnOfPlayer().getBoard().cardsInTrapAndSpellZone();
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
