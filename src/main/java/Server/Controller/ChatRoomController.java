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
            } else if (message.startsWith("Chat get all tokens"))
                dataOutputStream.writeUTF(sendTokens(message.substring(19)));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        for (String str: arraylist) {
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
        Server.sendMessageToAllClients("Server load message");
        String senderUsername = allLoggedInPlayers.get(token).getUsername();
        String result = senderUsername + "@" + message;
        messages.add(result);
    }
}
