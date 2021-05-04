import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!((input = scanner.nextLine()).equals("menu exit"))){

            if (input.trim().matches("^(?i)(menu[ ]+enter[ ]+(.)+)$"))
                System.out.println("menu navigation is not possible");
            else if (input.trim().matches("^(?i)(menu[ ]+show-current)$"))
                showMenuName();
            else if (input.trim().matches("^(?i)(card[ ]+show[ ]+(\\w+))$"))
                showCard(getCommandMatcher(input, "^(?i)(card[ ]+show[ ]+(\\w+))$"));
            else if (input.trim().matches("^(?i)deck[ ]+create[ ]+(\\w+)$"))
                createDeck(getCommandMatcher(input, "^(?i)deck[ ]+create[ ]+(\\w+)$"));
            else if (input.trim().matches("^(?i)(deck[ ]+delete[ ]+(\\w+))$"))
                deleteDeck(getCommandMatcher(input, "^(?i)(deck[ ]+delete[ ]+(\\w+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+set[ -_]+activate[ ]+(\\w+))$"))
                setActivatedDeck(getCommandMatcher(input, "^(?i)(deck[ ]+set[ -_]+activate[ ]+(\\w+))$"));
        }
        MainMenu.run(); //Navigating to MainMenu at last
    }

    private static void showMenuName() {
        System.out.println("Deck Menu");
    }

    private static void showCard(Matcher matcher) {
        if (matcher.find()) {
            String cardName = matcher.group(2);
            if (Card.getCardByName(cardName)==null)
                System.out.println("No card with this name was found!");
            else Card.getCardByName(cardName).showCard();
        }
    }

    private static void createDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (Deck.getDeckByName(deckName) == null) {
                System.out.println("deck created successfully!");
                Deck.addDeckToAllDecks(deckName, LoginMenu.getLoggedInPlayer().getUsername());
            } else System.out.println("deck with name " + deckName + " already exists");
        }
    }

    private static void deleteDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (Deck.getDeckByName(deckName) == null)
                System.out.println("deck with name " + deckName + " does not exist");
            else Deck.removeDeckFromAllDecks(deckName);
        }
    }

    private static void setActivatedDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (Deck.getDeckByName(deckName) == null)
                System.out.println("deck with name " + deckName + " does not exist");
            else {
                LoginMenu.getLoggedInPlayer().setActiveDeck(Deck.getDeckByName(deckName));
                System.out.println("deck activated successfully!");
            }
        }
    }

    public void addCardToDeck(String cardName, String deckType, String deckName) {

    }

    public void removeCardFromDeck(String cardName, String deckType, String deckName) {

    }

    public static void showAllDecks() {

    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
