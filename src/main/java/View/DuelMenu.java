package View;

import Controller.*;
import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.*;

public class DuelMenu {

    private int round;
    private static Player winnerPlayer;
    private static Player loserPlayer;

    private static Player matchWinner;

    private static int player1SetsWin;
    private static int player2SetsWin;

    private static ArrayList<Integer> LPsPlayer1 = new ArrayList<>();
    private static ArrayList<Integer> LPsPlayer2 = new ArrayList<>();


    public static void run(Player player1, Player player2, int round) {
        LPsPlayer1.clear();
        LPsPlayer2.clear();
        player1SetsWin = 0;
        player2SetsWin = 0;

        for (int i = 1; i <= round; i++) {
            Game game = new Game(player1, player2);
            game.run();
            endOfTheRoundSet(player1, player2);
            if (player1SetsWin == 2 || player2SetsWin == 2) {
                break;
            }
        }
        endOfTheMatch(player1 , player2 , round);
    }

    public static void endOfTheRoundSet(Player player1, Player player2) {
        if (player1.getLP() <= 0) {
            setWinnerPlayer(player2);
        } else {
            setWinnerPlayer(player1);
        }
        LPsPlayer1.add(player1.getLP());
        LPsPlayer2.add(player2.getLP());
        if (winnerPlayer.equals(player1)) {
            player1SetsWin++;
        } else {
            player2SetsWin++;
        }
        System.out.println(winnerPlayer.getNickname() + " won the game and the score is: "
                + player1SetsWin + " - " + player2SetsWin);
    }

    public static void endOfTheMatch(Player player1, Player player2, int round) {
        Integer maxLpWinner;
        if (round == 3) {
            if (player1SetsWin == 2) {
                setMatchWinner(player1);
                maxLpWinner = Collections.max(LPsPlayer1);

            } else {
                setMatchWinner(player2);
                maxLpWinner = Collections.max(LPsPlayer2);
            }
        } else {
            setMatchWinner(winnerPlayer);
            if(winnerPlayer.equals(player1)){
                maxLpWinner = Collections.max(LPsPlayer1);
            }
            else{
                maxLpWinner = Collections.max(LPsPlayer2);
            }
        }
        matchWinner.increaseScore(round * 1000);
        matchWinner.increaseMoney(round * (1000 + maxLpWinner));
        System.out.println( matchWinner.getNickname() + " won the whole match with score: "
                + player1SetsWin + " - " + player2SetsWin);
    }

    public static void setLoserPlayer(Player loserPlayer) {
        DuelMenu.loserPlayer = loserPlayer;
    }

    public static void setWinnerPlayer(Player winnerPlayer) {
        DuelMenu.winnerPlayer = winnerPlayer;
    }

    public static void setMatchWinner(Player matchWinner) {
        DuelMenu.matchWinner = matchWinner;
    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
