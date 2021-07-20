package Server.Controller;

import Server.Model.Card;
import Server.Model.Deck;
import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class LobbyController {
    static DataOutputStream dataOutputStream;
    static ArrayList<Player> allPlayersInLobby = new ArrayList<>();
    static ArrayList<Player> waitingForGameRound1 = new ArrayList<>();
    static ArrayList<Player> waitingForGameRound3 = new ArrayList<>();

    public static void processInput(String message) {
        dataOutputStream = Server.getDataOutputStream();
        if (message.startsWith("Lobby enter"))
            enterLobby(message.substring(11));
        else if (message.startsWith("Lobby exit"))
            exitLobby(message.substring(10));
//        else if (message.equals("Lobby get players nickname"))
//            sendPlayersNickname();
//        else if (message.equals("Lobby get players image")) ;
//        sendPlayersImagesSrc();
        else if (message.startsWith("Lobby start game"))
            handleGameRequest(message.substring(16));
    }

    private static void enterLobby(String token) {
        allPlayersInLobby.add(Server.getAllLoggedInPlayers().get(token));
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
            System.out.println(waitingForGameRound1.size());}
        else
            waitingForGameRound3.add(Server.getAllLoggedInPlayers().get(token));
    }

    public static void startAGame(Player player1, Player player2) {
        System.out.println("I'm in start game");
    }

    private static void exitLobby(String token) {
        allPlayersInLobby.remove(Server.getAllLoggedInPlayers().get(token));
    }

    public static ArrayList<Player> getWaitingForGameRound1() {
        return waitingForGameRound1;
    }

    public static ArrayList<Player> getWaitingForGameRound3() {
        return waitingForGameRound3;
    }

//    private static void sendPlayersNickname() {
//        String result = "";
//        for (int i = 0; i < allPlayersInLobby.size(); i++)
//            if (i == allPlayersInLobby.size() - 1)
//                result = result + allPlayersInLobby.get(i).getNickname();
//            else
//                result = result + allPlayersInLobby.get(i).getNickname() + "#";
//        try {
//            dataOutputStream.writeUTF(result);
//            dataOutputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private static void sendPlayersImagesSrc() {
//        String result = "";
//        for (int i = 0; i < allPlayersInLobby.size(); i++)
//            if (i == allPlayersInLobby.size() - 1)
//                result = result + allPlayersInLobby.get(i).getImage();
//            else
//                result = result + allPlayersInLobby.get(i).getNickname() + "#";
//        try {
//            dataOutputStream.writeUTF(result);
//            dataOutputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Thread getThreadByName(String threadName) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals(threadName)) return thread;
        }
        return null;
    }
    public static void findOppositionThreadOneRound() {
        new Thread(() -> {
            while (true) {
//                System.out.println(waitingForGameRound1.size());
                int size = waitingForGameRound1.size();
                if (waitingForGameRound1.size() >= 2) {
                    System.out.println("I'm in 1");
                    Player player1 = waitingForGameRound1.get(0);
                    Player player2 = waitingForGameRound1.get(1);

                    startAGame(player1, player2);
                    waitingForGameRound1.remove(0);
                    waitingForGameRound1.remove(0);
                }
            }
        }).start();
    }

    public static void findOppositionThreadThreeRound() {
        new Thread(() -> {
            while (true) {
                ArrayList<Player> waitingForGameRound3 = LobbyController.getWaitingForGameRound3();
                if (waitingForGameRound3.size() >= 2) {
                    Player player1 = waitingForGameRound3.get(0);
                    Player player2 = waitingForGameRound3.get(1);

                    LobbyController.startAGame(player1, player2);
                    waitingForGameRound3.remove(0);
                    waitingForGameRound3.remove(0);
                }
            }
        }).start();
    }
}
