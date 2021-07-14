package Server.Controller;

import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class LoginController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static String login(String str) {
        String[] info = str.split("#");
        String username = info[0];
        String password = info[1];

        if ((Player.getPlayerByUsername(username) == null) ||
                (!Objects.requireNonNull(Player.getPlayerByUsername(username)).getPassword().equals(password))) {
            return "username and password didn't match";
        } else {
            String token = UUID.randomUUID().toString();
            System.out.println(token);
            allLoggedInPlayers.put(token, Player.getPlayerByUsername(username));
            return "user logged in successfully" + token;
        }
    }

    public static String logout(String str) {
        allLoggedInPlayers.remove(str);
        return "logged out successfully";
    }

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if(message.startsWith("Login")){
            dataOutputStream.writeUTF(login(message.substring(5)));
        }
        else if(message.startsWith("Logout")){
            dataOutputStream.writeUTF(logout(message.substring(6)));
        }
    }
}
