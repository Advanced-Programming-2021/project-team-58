import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!((input = scanner.nextLine()).equals("menu exit"))) {

            if (input.trim().matches("^(?i)(menu[ ]+enter[ ]+(.)+)$"))
                System.out.println("menu navigation is not possible");
            else if (input.trim().matches("^(?i)(menu[ ]+show-current)$"))
                showMenuName();
            else if (input.trim().matches("^(?i)(card[ ]+show[ ]+(\\w+))$"))
                showCard(getCommandMatcher(input, "^(?i)(card[ ]+show[ ]+(\\w+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+create[ ]+(\\w+))$"))
                createDeck(getCommandMatcher(input, "^(?i)(deck[ ]+create[ ]+(\\w+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+delete[ ]+(\\w+))$"))
                deleteDeck(getCommandMatcher(input, "^(?i)(deck[ ]+delete[ ]+(\\w+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+set[ -_]+activate[ ]+(\\w+))$"))
                setActivatedDeck(getCommandMatcher(input, "^(?i)(deck[ ]+set[ -_]+activate[ ]+(\\w+))$"));
            else if (input.trim().matches("^(?i)(deck add-card (--.+) (--.+))$"))
                addCardToMainDeck(getCommandMatcher(input, "^(?i)(deck add-card (--.+) (--.+))$"));
            else if (input.trim().matches("^(?i)(deck add-card (--.+) (--.+) (--.+))$"))
                addCardToSideDeck(getCommandMatcher(input, "^(?i)(deck add-card (--.+) (--.+) (--.+))$"));

        }
        MainMenu.run(); //Navigating to MainMenu at last
    }

    private static void showMenuName() {
        System.out.println("Deck Menu");
    }

    private static void showCard(Matcher matcher) {
        if (matcher.find()) {
            String cardName = matcher.group(2);
            if (Card.getCardByName(cardName) == null)
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

    private static void addCardToMainDeck(Matcher matcher) {
        if (matcher.find()) {
            Matcher matcher1;
            String cardName = null;
            String deckName = null;

            for (int i = 2; i <= matcher.groupCount(); i++) {
                if (matcher.group(i).matches("^(?i)(--card (.+))$")) {
                    matcher1 = getCommandMatcher(matcher.group(i), "^(?i)(--card (.*))$");
                    if (matcher1.find())
                        cardName = matcher1.group(2);
                }
                if (matcher.group(i).matches("^(?i)(--deck (.+))$")) {
                    matcher1 = getCommandMatcher(matcher.group(i), "^(?i)(--deck (.*))$");
                    if (matcher1.find())
                        deckName = matcher1.group(2);
                }
            }
            if (cardName != null && deckName != null){
                if (Card.getCardByName(cardName)==null)
                    System.out.println("card with name "+cardName+" does not exist");
                else if (Deck.getDeckByName(deckName) == null)
                    System.out.println("deck with name "+deckName+" does not exist");
                else if (Deck.getDeckByName(deckName).getMainDeckSize()==60)
                    System.out.println("main deck is full");
                else if (Deck.getDeckByName(deckName).getNumOfCardInDeck(Card.getCardByName(cardName))==3)
                    System.out.println("there are already three cards with name "+cardName+" in deck "+deckName);
                else {
                    Deck.getDeckByName(deckName).addCardToMainDeck(Card.getCardByName(cardName));
                    System.out.println("card added to deck successfully");
                }
            }
        }
    }

    private static void addCardToSideDeck(Matcher matcher) {
        if (matcher.find()) {
            Matcher matcher1;
            String cardName = null;
            String deckName = null;
            boolean isInSideDeck = false;
            for (int i = 2; i <= matcher.groupCount(); i++) {
                if (matcher.group(i).matches("^(?i)(--card (.+))$")) {
                    matcher1 = getCommandMatcher(matcher.group(i), "^(?i)(--card (.*))$");
                    if (matcher1.find())
                        cardName = matcher1.group(2);
                }
                if (matcher.group(i).matches("^(?i)(--deck (.+))$")) {
                    matcher1 = getCommandMatcher(matcher.group(i), "^(?i)(--deck (.*))$");
                    if (matcher1.find())
                        deckName = matcher1.group(2);
                }
                if (matcher.group(i).matches("^(?i)(--side)$"))
                    isInSideDeck = true;
            }
            if (cardName != null && deckName != null && isInSideDeck){
                if (Card.getCardByName(cardName)==null)
                    System.out.println("card with name "+cardName+" does not exist");
                else if (Deck.getDeckByName(deckName) == null)
                    System.out.println("deck with name "+deckName+" does not exist");
                else if (Deck.getDeckByName(deckName).getSideDeckSize()==15)
                    System.out.println("side deck is full");
                else if (Deck.getDeckByName(deckName).getNumOfCardInDeck(Card.getCardByName(cardName))==3)
                    System.out.println("there are already three cards with name "+cardName+" in deck "+deckName);
                else {
                    Deck.getDeckByName(deckName).addCardToSideDeck(Card.getCardByName(cardName));
                    System.out.println("card added to deck successfully");
                }
            }
            else System.out.println("invalid command");
        }
    }


    public static void removeCardFromDeck(String cardName, String deckType, String deckName) {

    }

    public static void showAllDecks() {

    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
