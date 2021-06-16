package Controller;

import Model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class jsonSaveAndLoad {

    public static void save(ArrayList<Player> players) throws IOException {
        FileWriter writer = new FileWriter("Players.txt");
        writer.write(new Gson().toJson(Player.getAllPlayers()));
        writer.close();
    }

    public static void load() throws IOException {
        String str = new String(Files.readAllBytes(Paths.get("Players.txt")));
        ArrayList<Player> players = new Gson().fromJson(str , new TypeToken<ArrayList<Player>>(){}.getType() );
        Player.setAllPlayers(players);
    }

}
