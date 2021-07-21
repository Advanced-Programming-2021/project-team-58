package Server.Controller;

import Server.Model.Card;
import Server.Model.Deck;
import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LobbyController {
    static DataOutputStream dataOutputStream;
    static ArrayList<Player> allPlayersInLobby = new ArrayList<>();
    static ArrayList<String> allPlayersByTokens = new ArrayList<>();
    static ArrayList<Player> waitingForGameRound1 = new ArrayList<>();
    static ArrayList<Player> waitingForGameRound3 = new ArrayList<>();

    public static void processInput(String message, DataOutputStream dOS) {
        dataOutputStream = dOS;
        if (message.startsWith("Lobby enter"))
            enterLobby(message.substring(11));
        else if (message.startsWith("Lobby exit"))
            exitLobby(message.substring(10));
        else if (message.startsWith("Lobby get tokens"))
            sendTokens(message.substring(16));
        else if (message.startsWith("Lobby start game"))
            handleGameRequest(message.substring(16));
    }

    private static void sendTokens(String token) {
        String result = "";
        int a = 5;
        for (int i = allPlayersByTokens.size() - 1; i >= allPlayersByTokens.size() - a; i--) {
            if (i >= 0 && allPlayersByTokens.get(i) != null) {
                if (!allPlayersByTokens.get(i).equals(token)) {
                    if (i == allPlayersByTokens.size() - 1)
                        result = allPlayersByTokens.get(i);
                    else
                        result = result + "#" + allPlayersByTokens.get(i);
                } else a = 6;
            }
        }
        try {
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enterLobby(String token) {
        allPlayersInLobby.add(Server.getAllLoggedInPlayers().get(token));
        allPlayersByTokens.add(token);
        Deck deck = new Deck("myDeck");
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck.addCardToMainDeck(Card.getCardByName("Silver Fang"));
        deck.addCardToMainDeck(Card.getCardByName("Silver Fang"));
        deck.addCardToMainDeck(Card.getCardByName("Silver Fang"));
        Server.getAllLoggedInPlayers().get(token).setActiveDeck(deck);
    }

    private static void handleGameRequest(String string) {
        String[] input = string.split("#");
        int numOfRound = Integer.parseInt(input[0]);
        String token = input[1];
        if (numOfRound == 1) {
            waitingForGameRound1.add(Server.getAllLoggedInPlayers().get(token));
        } else
            waitingForGameRound3.add(Server.getAllLoggedInPlayers().get(token));
    }

    private static void exitLobby(String token) {
        allPlayersInLobby.remove(Server.getAllLoggedInPlayers().get(token));
        allPlayersByTokens.remove(token);
    }
}
