package Server.Controller;

import Server.Model.Player;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreBoardController {

    public static void processInput( String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.startsWith("Scoreboard get players"))
            dataOutputStream.writeUTF(sendPlayers(message.substring(22)));
    }

    private static String sendPlayers(String substring) {
        String result = "";
        ArrayList<Player> allPlayers = Player.getAllPlayers();
        sort(allPlayers);

        for (int i = 0; i < Player.getAllPlayers().size(); i++) {

        }
        return result;
    }

    public static void setScore(ArrayList<Player> playersSort) {
        int rank = 1;
        for (int i = 0; i < playersSort.size(); i++) {
            if (i == 0) {
                System.out.println(rank +"- " + playersSort.get(i).getNickname() + ": " + playersSort.get(i).getScore());
            } else {
                if (playersSort.get(i - 1).getScore() != playersSort.get(i).getScore()) {
                    rank = i + 1;
                }
                System.out.println(rank + "- " + playersSort.get(i).getNickname() + ": " + playersSort.get(i).getScore());
            }
        }
    }

    public static void sort(ArrayList<Player> playersSort) {
        for (int i = 0; i < playersSort.size() - 1; i++) {
            for (int j = i + 1; j < playersSort.size(); j++) {
                if (compare(playersSort.get(j), playersSort.get(i))) {
                    Player tmp = playersSort.get(j);
                    playersSort.set(j, playersSort.get(i));
                    playersSort.set(i, tmp);
                }
            }
        }
    }

    public static boolean compare(Player player1, Player player2) {
        boolean condition1 = player1.getScore() > player2.getScore();
        boolean condition2 = (player1.getNickname().compareTo(player2.getNickname()) < 0);
        boolean condition3 = (player1.getScore() == player2.getScore());
        if (condition1) {
            return true;
        } else {
            if ((condition2) && (condition3)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
