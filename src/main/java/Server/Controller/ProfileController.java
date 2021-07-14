package Server.Controller;

import Server.Model.Player;
import Server.Server;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ProfileController {

    private static int imageDataCounter = 0;
    private static int imageDataNumber;
    private static String imageData = "";
    private static String standbyToken;

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static String changePassword(String str) {
        String[] info = str.split("#");
        String newPassword = info[0];
        String token = info[1];
        Player player = allLoggedInPlayers.get(token);

        if (player.getPassword().equals(newPassword)) {
            return "same password";
        } else {
            player.setPassword(newPassword);
            return "password changed";
        }
    }

    public static String changeNickname(String str) {
        String[] info = str.split("#");
        String newNickname = info[0];
        String token = info[1];
        if (Player.isNicknameExists(newNickname)) {
            return "nickname already exists";
        } else {
            allLoggedInPlayers.get(token).setNickname(newNickname);
            return "nickname changed";
        }
    }

    public static void changeProfile(String imageJson, String token) {
        System.out.println(imageJson);
        Image image = new YaGson().fromJson(imageJson, new TypeToken<Image>() {
        }.getType());
        allLoggedInPlayers.get(token).setImage(image);
    }

    public static void sendImage(String token, DataOutputStream dataOutputStream) {
        Image image = allLoggedInPlayers.get(token).getImage();
        try {
            String imageJson = new YaGson().toJson(image);
            int x = imageJson.length() / 64000;
            dataOutputStream.writeUTF(String.valueOf(x + 1));
            dataOutputStream.flush();

            for (int i = 0; i < x; i++) {
                String tmp = imageJson.substring(0, 64000);
                imageJson = imageJson.substring(64000);
                dataOutputStream.writeUTF(tmp);
                dataOutputStream.flush();
            }
            dataOutputStream.writeUTF(imageJson);
            dataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUsername(String token) {
        return allLoggedInPlayers.get(token).getUsername();
    }

    public static String getNickname(String token) {
        return allLoggedInPlayers.get(token).getNickname();
    }

    public static String getMoney(String token) {
        return String.valueOf(allLoggedInPlayers.get(token).getMoney());
    }

    public static String getScore(String token) {
        return String.valueOf(allLoggedInPlayers.get(token).getScore());
    }

    public static String getDeckSize(String token) {
        return String.valueOf(allLoggedInPlayers.get(token).getDecks().size());
    }

    public static String getWinMatches(String token) {
        return String.valueOf(allLoggedInPlayers.get(token).getWinMatches());
    }

    public static String getLoseMatches(String token){
        return String.valueOf(allLoggedInPlayers.get(token).getLoseMatches());
    }

    public static String getActiveDeckName(String token) {
        if(allLoggedInPlayers.get(token).getActiveDeck() == null){
            return "Not set yet";
        }
        return allLoggedInPlayers.get(token).getActiveDeck().getDeckName();
    }

    public static void processInput(String message , DataOutputStream dataOutputStream) throws IOException {
        if (message.startsWith("Profile change nickname")) {
            dataOutputStream.writeUTF(changeNickname(message.substring(23)));
        } else if (message.startsWith("Profile change password")) {
            dataOutputStream.writeUTF(changePassword(message.substring(23)));
        } else if (message.startsWith("Profile image")) {
            sendImage(message.substring(13), dataOutputStream);
        } else if (message.startsWith("Profile username")) {
            dataOutputStream.writeUTF(getUsername(message.substring(16)));
        } else if (message.startsWith("Profile nickname")) {
            dataOutputStream.writeUTF(getNickname(message.substring(16)));
        } else if (message.startsWith("Profile money")) {
            dataOutputStream.writeUTF(getMoney(message.substring(13)));
        } else if (message.startsWith("Profile score")) {
            dataOutputStream.writeUTF(getScore(message.substring(13)));
        } else if (message.startsWith("Profile decks size")) {
            dataOutputStream.writeUTF(getDeckSize(message.substring(18)));
        } else if (message.startsWith("Profile win matches")) {
            dataOutputStream.writeUTF(getWinMatches(message.substring(19)));
        } else if (message.startsWith("Profile lose matches")) {
            dataOutputStream.writeUTF(getLoseMatches(message.substring(20)));
        } else if (message.startsWith("Profile active deck name")) {
            dataOutputStream.writeUTF(getActiveDeckName(message.substring(24)));
        } else if (message.startsWith("Profile length")) {
            String str = message.substring(14);
            String[] info = str.split("#");
            String imageLength = info[0];
            String token = info[1];
            imageDataNumber = Integer.parseInt(imageLength);
            standbyToken = token;
        } else if (message.startsWith("Profile change")) {
            if (imageDataCounter < imageDataNumber) {
                imageData = imageData + message.substring(14);
                imageDataCounter++;
            } else {
                changeProfile(imageData, standbyToken);
                imageDataCounter = 0;
                imageDataNumber = 0;
                imageData = "";
                standbyToken = "";
            }
        }
        dataOutputStream.flush();
    }
}
