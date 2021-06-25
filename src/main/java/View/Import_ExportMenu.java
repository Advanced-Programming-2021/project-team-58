package View;

import Model.*;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Import_ExportMenu {

    private static ArrayList<Card> importedCards = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void run() {
        String importRegex = "import card (.+)";
        String exportRegex = "export card (.+)";
        String input = "";
        while (!(input = scanner.nextLine()).equals("menu exit")) {
            Matcher matchImport = getCommandMatcher(input, importRegex);
            Matcher matchExport = getCommandMatcher(input, exportRegex);

            if (matchImport.find()) {
                importCard(matchImport.group(1));
            } else if (matchExport.find()) {
                exportCard(matchExport.group(1));
            } else if (input.trim().matches("^(?i)(menu[ ]+show-current)$")) {
                System.out.println("Import-Export Menu");
            } else if (input.matches("menu enter [A-Za-z ]+")) {
                System.out.println("menu navigation is not possible");
            } else if (input.equals("--help")) {
                System.out.println("menu exit\n" +
                        "menu enter (menu name)\n" +
                        "menu show-current\n" +
                        "import card (card name)\n" +
                        "export card (card name)");
            } else {
                System.out.println("invalid command");
            }
        }
        MainMenu.run();
    }

    public static void importCard(String cardName) {
        try {
            String str = new String(Files.readAllBytes(Paths.get("CardsDatabase.txt")));
            ArrayList<Card> cards = new YaGson().fromJson(str,
                    new TypeToken<ArrayList<Card>>() {
                    }.getType());
            if (Card.getCardByName(cardName) != null) {
                for (Card card : cards) {
                    if (card.getCardName().equals(cardName)) {
                        importedCards.add(card);
                        System.out.println("Card " + cardName + " imported successfully!");
                    }
                }
            } else {
                System.out.println("there is no card with this name!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exportCard(String cardName) {

        try {
            String str = new String(Files.readAllBytes(Paths.get("ExportedCards.txt")));
            ArrayList<Card> cards = new YaGson().fromJson(str,
                    new TypeToken<ArrayList<Card>>() {
                    }.getType());
            if (Card.getCardByName(cardName) == null) {
                System.out.println("there is no card with this name!");
            } else {
                cards.add(Card.getCardByName(cardName));

                FileWriter writer = new FileWriter("ExportedCards.txt");
                writer.write(new YaGson().toJson(cards));
                writer.close();
                System.out.println("Card " + cardName + " exported successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
