package View;

import Controller.*;
import Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DuelMenu {

    private static Player winnerPlayer;
    private static Player loserPlayer;

    private static Player matchWinner;
    private static Player matchLoser;

    private static Player firstPlayer;
    private static Player secondPlayer;

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
            System.out.println("Round " + i + " started!\n");
            setPlayers(i, player1, player2);
            Game game = new Game(firstPlayer, secondPlayer);
            game.run();
            endOfTheRoundSet(player1, player2, game);
            if (player1SetsWin == 2 || player2SetsWin == 2) {
                break;
            }
        }
        endOfTheMatch(player1, player2, round);
    }

    private static void setPlayers(int i, Player player1, Player player2) {
        if (i == 1) {
            Random rand = new Random();
            int coin = rand.nextInt(2);
            System.out.println(player1.getNickname() + "'s bet is head and " + player2.getNickname() + "'s is tail");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (coin == 0) {
                System.out.println(player1.getNickname() + " goes first!");
                firstPlayer = player1;
                secondPlayer = player2;
            } else {
                System.out.println(player2.getNickname() + " goes first!");
                firstPlayer = player2;
                secondPlayer = player1;
            }
        } else {
            firstPlayer = winnerPlayer;
            secondPlayer = loserPlayer;
            System.out.println(firstPlayer.getNickname() + " goes first!");
        }
    }


    public static void endOfTheRoundSet(Player player1, Player player2, Game game) {
        if (player1.getLP() <= 0 || player1.getBoard().getMainDeck().size() == 0 || ((game.isSurrendered) && player1.equals(game.getTurnOfPlayer()))) {
            setWinnerPlayer(player2);
            setLoserPlayer(player1);
        } else {
            setWinnerPlayer(player1);
            setLoserPlayer(player2);
        }
        LPsPlayer1.add(player1.getLP());
        LPsPlayer2.add(player2.getLP());
        if (winnerPlayer.equals(player1)) {
            player1SetsWin++;
        } else {
            player2SetsWin++;
        }
        System.out.println(winnerPlayer.getNickname() + " won the game and the score is: "
                + player1SetsWin + " - " + player2SetsWin + "\n");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("Do you want to manage your main deck?(type y to confirm)");
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        if (input.equals("y")){
//            manageDeck();
//        } else return;
    }

//    private static void manageDeck() {
//    }

    public static void endOfTheMatch(Player player1, Player player2, int round) {
        Integer maxLpWinner;
        if (round == 3) {
            if (player1SetsWin == 2) {
                setMatchWinner(player1);
                setMatchLoser(player2);
                maxLpWinner = Collections.max(LPsPlayer1);

            } else {
                setMatchWinner(player2);
                setMatchLoser(player1);
                maxLpWinner = Collections.max(LPsPlayer2);
            }
        } else {
            setMatchWinner(winnerPlayer);
            setMatchLoser(loserPlayer);
            if (winnerPlayer.equals(player1)) {
                maxLpWinner = Collections.max(LPsPlayer1);
            } else {
                maxLpWinner = Collections.max(LPsPlayer2);
            }
        }
        matchWinner.increaseScore(round * 1000);
        matchWinner.increaseMoney(round * (1000 + maxLpWinner));
        matchLoser.increaseMoney(100 * round);
        System.out.println(matchWinner.getNickname() + " won the whole match with score: "
                + player1SetsWin + " - " + player2SetsWin);
        try {
            jsonSaveAndLoad.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMatchLoser(Player matchLoser) {
        DuelMenu.matchLoser = matchLoser;
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
}
