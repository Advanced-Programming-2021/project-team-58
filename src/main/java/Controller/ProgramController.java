package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import View.*;
import com.gilecode.yagson.YaGson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


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
//        Deck deck = new Deck("firstAndOnlyDeck");
//        for (int i = 0; i < 3; i++) {
//            deck.addCardToMainDeck(Card.getCardByName("Battle OX"));
//        }
//        for (int i = 0; i < 3; i++) {
//            deck.addCardToMainDeck(Card.getCardByName("Silver Fang"));
//        }
//        for (int i = 0; i < 3; i++) {
//            deck.addCardToMainDeck(Card.getCardByName("Yomi Ship"));
//        }
//        for (int i = 0; i < 3; i++) {
//            deck.addCardToMainDeck(Card.getCardByName("Wattkid"));
//        }
//        ArrayList<Deck> x = new ArrayList<>();
//        x.add(deck);
//        try {
//            FileWriter writer = new FileWriter("AIDeck.txt");
//            writer.write(new YaGson().toJson(x));
//            writer.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }

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
