package Server.Controller;

import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatRoomController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    private static ArrayList<String> messages = Server.getMessages();

    public static void processInput(String message, DataOutputStream dataOutputStream) {
        try {
            if (message.startsWith("Chat new message")) {
                addNewMessage(message.substring(16));
            } else if (message.startsWith("Chat get all messages")) {
                dataOutputStream.writeUTF(getAllMessages());
            } else if (message.startsWith("Chat tamoomesh kon")) {
                dataOutputStream.writeUTF(":)");
            } else if (message.startsWith("Chat get all tokens")) {
                dataOutputStream.writeUTF(sendTokens(message.substring(19)));
            } else if (message.startsWith("Chat pin message")) {
                Server.setPinnedMessage(message.substring(16));
                Server.sendMessageToAllClients("Server pin message" + message.substring(16));
            } else if (message.startsWith("Chat get pinned message")) {
                dataOutputStream.writeUTF(sendPinnedMessage());
            } else if (message.startsWith("Chat delete message index")) {
                deleteMessage(message.substring(25));
            } else if (message.startsWith("Chat change message index")) {
                changeMessage(message.substring(25));
            } else if (message.startsWith("Chat get player info")) {
                dataOutputStream.writeUTF(getInfo(message.substring(20)));
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getInfo(String nickname) {
        Player player = Player.getPlayerByNickname(nickname);
        String result = player.getUsername() + "#" + player.getNickname()
                + "#" + player.getScore() + "#" + player.getWinMatches() + "#" + player.getLoseMatches();
        return result;
    }

    private static void changeMessage(String str) {
        String[] tmp1 = str.split("#");
        int index = Integer.parseInt(tmp1[0]);
        String newMessage = tmp1[1];
        String targetMessage = messages.get(index);
        String[] tmp2 = targetMessage.split("@");
        String sender = tmp2[0];
        String result = sender + "@" + newMessage;
        messages.set(index, result);
        Server.sendMessageToAllClients("Server load message");
    }

    private static void deleteMessage(String number) {
        int index = Integer.parseInt(number);
        messages.remove(index);
        Server.sendMessageToAllClients("Server load message");
    }

    private static String sendPinnedMessage() {
        if (Server.getPinnedMessage().equals(""))
            return "@";
        else
            return Server.getPinnedMessage();
    }

    private static String sendTokens(String token) {
        String result = "";
        ArrayList<String> allTokens = new ArrayList<>();
        for (Map.Entry<String, Player> e : allLoggedInPlayers.entrySet()) {
            if (!playerAlreadyExists(allTokens, e.getKey()) && !e.getKey().equals(token))
                allTokens.add(e.getKey());
        }

        for (int i = 0; i < allTokens.size(); i++) {
            if (i == 0)
                result = allTokens.get(i);
            else result = result + "#" + allTokens.get(i);
        }
        return result;
    }

    private static boolean playerAlreadyExists(ArrayList<String> arraylist, String token) {
        for (String str : arraylist) {
            if (allLoggedInPlayers.get(str).equals(allLoggedInPlayers.get(token)))
                return true;
        }
        return false;
    }

    private static String getAllMessages() {
        String result = "";
        for (int i = 0; i < messages.size(); i++) {
            if (i == messages.size() - 1) {
                result = result + messages.get(i);
            } else {
                result = result + messages.get(i) + "#";
            }
        }
        return result;
    }

    private static void addNewMessage(String str) {
        String[] tmp = str.split("#");
        String message = tmp[0];
        String token = tmp[1];
        String senderUsername = allLoggedInPlayers.get(token).getUsername();
        String result = senderUsername + "@" + message;
        messages.add(result);
        Server.sendMessageToAllClients("Server load message");
    }
}
