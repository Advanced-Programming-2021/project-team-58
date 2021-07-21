package Server;

import Server.Controller.*;
import Server.Model.MonsterCard;
import Server.Model.Player;
import Server.Model.TrapAndSpellCard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    private static HashMap<String, Player> allLoggedInPlayers = new HashMap<>();
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<Socket> allActiveSockets = new ArrayList<>();

    public static void main(String[] args) {
        MonsterCard.addMonster();
        TrapAndSpellCard.addTrapAndSpell();
        File file = new File("Players.txt");
        if (file.length() != 0) {
            try {
                JsonSaveAndLoad.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while (true) {
                Socket socket = serverSocket.accept();
                makeNewThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeNewThread(Socket socket) {
        new Thread(() -> {
            try {
                allActiveSockets.add(socket);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                processInput(dataInputStream, dataOutputStream);
                dataInputStream.close();
                socket.close();
                allActiveSockets.remove(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void processInput(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        while (true) {
            try {
                String message = dataInputStream.readUTF();

                if (message.startsWith("Register")) {
                    dataOutputStream.writeUTF(RegisterController.register(message.substring(8)));
                } else if (message.startsWith("Log")) {
                    LoginController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Profile")) {
                    ProfileController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Shop")) {
                    ShopController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Deck")) {
                    DeckController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Chat")) {
                    ChatRoomController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Lobby")) {
                    LobbyController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Scoreboard")) {
                    ScoreBoardController.processInput(message, dataOutputStream);
                } else if (message.startsWith("Auction")) {
                    AuctionController.processInput(message, dataOutputStream);
                } else if (message.equals("end")) {
                    break;
                }
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static HashMap<String, Player> getAllLoggedInPlayers() {
        return allLoggedInPlayers;
    }

    public static ArrayList<String> getMessages() {
        return messages;
    }

    public static void sendMessageToAllClients(String message) {
        try {
            for (Socket socket : allActiveSockets) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}



