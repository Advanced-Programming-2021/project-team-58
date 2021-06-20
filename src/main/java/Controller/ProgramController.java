package Controller;

import Model.Card;
import View.*;
import com.gilecode.yagson.YaGson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ProgramController {
    public static void main(String[] args) {
        File file = new File("Players.txt");
        if(file.length() != 0) {
            try {
                jsonSaveAndLoad.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LoginMenu.addCards();
//        try {
//            FileWriter writer = new FileWriter("CardsDatabase.txt");
//            writer.write(new YaGson().toJson(Card.getAllCards()));
//            writer.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        LoginMenu.run();
    }
}
