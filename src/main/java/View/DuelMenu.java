package View;

import Controller.*;
import Model.*;

import java.util.Scanner;
import java.util.regex.*;

public class DuelMenu {

    private int round;

    public static void run(Player player1, Player player2, int round) {
        for (int i = 1; i <= round; i++) {
            Game game = new Game(player1, player2);
            game.run();
        }
    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
