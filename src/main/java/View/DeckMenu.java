package View;

import Controller.*;
import Model.*;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu {
    static Player player;

    public static void setPlayer(Player player) {
        DeckMenu.player = player;
    }

    public static void run() {
        setPlayer(LoginMenu.getLoggedInPlayer());
        handleInput();
        try {
            jsonSaveAndLoad.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenu.run(); //Navigating to MainMenu at last
    }

    private static void handleInput() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!((input = scanner.nextLine()).equals("menu exit"))) {

            if (input.trim().matches("^(?i)(menu[ ]+enter[ ]+(.)+)$"))
                System.out.println("menu navigation is not possible");
            else if (input.trim().matches("^(?i)(menu[ ]+show-current)$"))
                showMenuName();
            else if (input.trim().matches("^(?i)(card[ ]+show[ ]+(.+))$"))
                showCard(getCommandMatcher(input, "^(?i)(card[ ]+show[ ]+(.+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+create[ ]+(.+))$"))
                createDeck(getCommandMatcher(input, "^(?i)(deck[ ]+create[ ]+(.+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+delete[ ]+(.+))$"))
                deleteDeck(getCommandMatcher(input, "^(?i)(deck[ ]+delete[ ]+(.+))$"));
            else if (input.trim().matches("^(?i)(deck[ ]+set-activate[ ]+(.+))$"))
                setActivatedDeck(getCommandMatcher(input, "^(?i)(deck[ ]+set-activate[ ]+(.+))$"));
            else if (input.trim().matches("^(?i)(deck add-card (--.*) (--.*) (--.*))$"))
                addCardToSideDeck(input);
            else if (input.trim().matches("^(?i)(deck add-card (--.+) (--.+))$"))
                addCardToMainDeck(input);
            else if (input.trim().matches("^(?i)(deck rm-card (--.+) (--.+) (--.+))$"))
                removeCardFromSideDeck(input);
            else if (input.trim().matches("^(?i)(deck rm-card (--.+) (--.+))$"))
                removeCardFromMainDeck(getCommandMatcher(input, "^(?i)(deck rm-card (--.+) (--.+))$"));
            else if (input.trim().matches("^(?i)(deck show --all)$"))
                showAllDecks();
            else if (input.trim().matches("^(?i)(deck show (--.+) (--.+))$"))
                showSideDeck(input);
            else if (input.trim().matches("^(?i)(deck show --deck-name (.+))$"))
                showMainDeck(getCommandMatcher(input, "^(?i)(deck show --deck-name (.+))$"));
            else if (input.trim().matches("^(?i)(deck show --cards)$"))
                showAllCards();
            else if (input.equals("--help"))
                help();
            else System.out.println("invalid command");
        }
    }


    private static void showMenuName() {
        System.out.println("Deck Menu");
    }

    private static void showCard(Matcher matcher) {
        if (matcher.find()) {
            String cardName = matcher.group(2);
            if (Card.getCardByName(cardName) == null)
                System.out.println("No card with this name was found!");
            else Objects.requireNonNull(Card.getCardByName(cardName)).showCard();
        }
    }

    private static void createDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (!(player.hasADeck(player.getDeckByName(deckName)))) {
                System.out.println("deck with name " + deckName + " created successfully!");
                player.getDecks().add(new Deck(deckName));
            } else System.out.println("deck with name " + deckName + " already exists");
        }
    }

    private static void deleteDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (!player.hasADeck(player.getDeckByName(deckName)))
                System.out.println("deck with name " + deckName + " does not exist");
            else {
                if (player.getActiveDeck() != null)
                    if (player.getActiveDeck().getDeckName().equals(deckName))
                        player.setActiveDeck(null);

                giveBackCards(player.getDeckByName(deckName));
                Deck.removeDeckFromAllDecks(player.getDeckByName(deckName));
                player.getDecks().remove(player.getDeckByName(deckName));
                System.out.println("deck with name " + deckName + " deleted successfully");
            }
        }
    }

    private static void giveBackCards(Deck deck) {
        ArrayList<Card> mainDeck = deck.getMainDeck();
        ArrayList<Card> sideDeck = deck.getSideDeck();
        for (Card card : mainDeck) {
            player.getAllCards().add(card);
        }
        for (Card card : sideDeck) {
            player.getAllCards().add(card);
        }
    }

    private static void setActivatedDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (!player.hasADeck(player.getDeckByName(deckName)))
                System.out.println("deck with name " + deckName + " does not exist");
            else {
                player.setActiveDeck((Deck) player.getDeckByName(deckName).clone());
                System.out.println("deck activated successfully!");
            }
        }
    }

    private static void addCardToSideDeck(String input) {
        boolean isInSideDeck = false;
        String cardName = null;
        String deckName = null;
        Matcher matcher = getCommandMatcher(input, "deck add-card (--.+) (--.+) (--.+)");
        if (matcher.find()) {
            for (int i = 1; i < 4; i++) {
                if (matcher.group(i).matches("--side"))
                    isInSideDeck = true;
                if (matcher.group(i).matches("--card (.+)")) {
                    Matcher matcher1 = getCommandMatcher(matcher.group(i), "--card (.+)");
                    if (matcher1.find()) {
                        cardName = matcher1.group(1);
                    }
                }
                if (matcher.group(i).matches("--deck (.+)")) {
                    Matcher matcher2 = getCommandMatcher(matcher.group(i), "--deck (.+)");
                    if (matcher2.find()) {
                        deckName = matcher2.group(1);
                    }
                }
            }
        }

        if (cardName != null && deckName != null && isInSideDeck) {
            if (!player.hasACard(Card.getCardByName(cardName)))
                System.out.println("card with name " + cardName + " does not exist");
            else if (!player.hasADeck((player.getDeckByName(deckName))))
                System.out.println("deck with name " + deckName + " does not exist");
            else if (player.getDeckByName(deckName).getSideDeckSize() == 15)
                System.out.println("side deck is full");
            else if (player.getDeckByName(deckName).getNumOfCardInDeck(Card.getCardByName(cardName)) == 3)
                System.out.println("there are already three cards with name " + cardName + " in deck " + deckName);
            else {
                player.getDeckByName(deckName).addCardToSideDeck(Card.getCardByName(cardName));
                player.getAllCards().remove(Card.getCardByName(cardName));
                System.out.println("card added to deck successfully");
            }
        } else System.out.println("invalid command");
    }

    private static void addCardToMainDeck(String input) {
        String cardName = null;
        String deckName = null;
        if (input.matches("deck add-card --deck (.+) --card (.+)")) {
            Matcher matcher = getCommandMatcher(input, "deck add-card --deck (.+) --card (.+)");
            if (matcher.find()) {
                deckName = matcher.group(1);
                cardName = matcher.group(2);
            }
        } else if (input.matches("deck add-card --card (.+) --deck (.+)")) {
            Matcher matcher = getCommandMatcher(input, "deck add-card --card (.+) --deck (.+)");
            if (matcher.find()) {
                cardName = matcher.group(1);
                deckName = matcher.group(2);
            }
        } else {
            System.out.println("invalid command");
            return;
        }
        if (cardName != null && deckName != null) {
            if (!player.hasACard(Card.getCardByName(cardName)))
                System.out.println("card with name " + cardName + " does not exist");
            else if (!player.hasADeck(player.getDeckByName(deckName)))
                System.out.println("deck with name " + deckName + " does not exist");
            else if (player.getDeckByName(deckName).getMainDeckSize() == 60)
                System.out.println("main deck is full");
            else if (player.getDeckByName(deckName).getNumOfCardInDeck(Card.getCardByName(cardName)) == 3)
                System.out.println("there are already three cards with name " + cardName + " in deck " + deckName);
            else {
                player.getDeckByName(deckName).addCardToMainDeck(Card.getCardByName(cardName));
                player.removeCardFromAllCards(Card.getCardByName(cardName));
                System.out.println("card added to deck successfully");
            }
        }
    }

    public static void removeCardFromSideDeck(String input) {
        String cardName = null;
        String deckName = null;
        boolean isInSideDeck = false;

        Matcher matcher = getCommandMatcher(input, "deck rm-card (--.+) (--.+) (--.+)");
        if (matcher.find()) {
            for (int i = 1; i < 4; i++) {
                if (matcher.group(i).matches("--side"))
                    isInSideDeck = true;
                if (matcher.group(i).matches("--card (.+)")) {
                    Matcher matcher1 = getCommandMatcher(matcher.group(i), "--card (.+)");
                    if (matcher1.find()) {
                        cardName = matcher1.group(1);
                    }
                }
                if (matcher.group(i).matches("--deck (.+)")) {
                    Matcher matcher2 = getCommandMatcher(matcher.group(i), "--deck (.+)");
                    if (matcher2.find()) {
                        deckName = matcher2.group(1);
                    }
                }
            }
        }

        if (cardName != null && deckName != null && isInSideDeck) {
            if (!player.hasADeck(player.getDeckByName(deckName)))
                System.out.println("deck with name " + deckName + " does not exist");
            else {
                Card card = Card.getCardByName(cardName);
                boolean isCardInSideDeckExists = player.getDeckByName(deckName).getSideDeck().contains(card);
                if (card == null || !isCardInSideDeckExists)
                    System.out.println("card with name " + cardName + " does not exist in side deck");
                else {
                    player.getDeckByName(deckName).removeCardFromSideDeck(card);
                    player.getAllCards().add(Card.getCardByName(cardName));
                    System.out.println("card removed form deck successfully");
                }
            }
        } else System.out.println("invalid command");
    }

    public static void removeCardFromMainDeck(Matcher matcher) {
        if (matcher.find()) {
            Matcher matcher1 = getCommandMatcher(matcher.group(0), "deck rm-card --card (.+) --deck (.+)");
            Matcher matcher2 = getCommandMatcher(matcher.group(0), "deck rm-card --deck (.+) --card (.+)");
            String cardName = null;
            String deckName = null;
            if (matcher1.find()) {
                cardName = matcher1.group(1);
                deckName = matcher1.group(2);
            } else if (matcher2.find()) {
                cardName = matcher2.group(2);
                deckName = matcher2.group(1);
            }
            if (cardName != null && deckName != null) {
                if (!player.hasADeck((player.getDeckByName(deckName))))
                    System.out.println("deck with name " + deckName + " does not exist");
                else {
                    Card card = Card.getCardByName(cardName);
                    boolean isCardInMainDeckExists = player.getDeckByName(deckName).hasACardInMainDeck(card);
                    if (card == null || !isCardInMainDeckExists)
                        System.out.println("card with name " + cardName + " does not exist in main deck");
                    else {
                        player.getDeckByName(deckName).removeCardFromMainDeck(Card.getCardByName(cardName));
                        player.getAllCards().add(Card.getCardByName(cardName));
                        System.out.println("card removed from deck successfully");
                    }
                }
            } else System.out.println("invalid command");
        }
    }

    public static void showAllDecks() {
        System.out.println("Decks:");
        System.out.println("Active deck:");
        if (player.getActiveDeck() != null) {
            Deck activeDeck = player.getActiveDeck();
            String activeDeckName = activeDeck.getDeckName();
            int mainDeckSize = activeDeck.getMainDeckSize();
            int sideDeckSize = activeDeck.getSideDeckSize();
            if (activeDeck.isValid())
                System.out.println(activeDeckName + ": main deck " + mainDeckSize + ", side deck " + sideDeckSize + ", valid");
            else
                System.out.println(activeDeckName + ": main deck " + mainDeckSize + ", side deck " + sideDeckSize + ", invalid");
        }
        System.out.println("Other decks:");
        LinkedList<Deck> decks = player.getDecks();
        if (!decks.isEmpty()) {
            Collections.sort(decks);
            for (int i = 0; i < decks.size(); i++) {
                String deckName = decks.get(i).getDeckName();
                if (player.getActiveDeck() != null)
                    if (deckName.equals(player.getActiveDeck().getDeckName()))
                        continue;
                int mainDeckSize = decks.get(i).getMainDeckSize();
                int sideDeckSize = decks.get(i).getSideDeckSize();
                if (decks.get(i).isValid())
                    System.out.println(deckName + ": main deck " + mainDeckSize + ", side deck " + sideDeckSize + ", valid");
                else
                    System.out.println(deckName + ": main deck " + mainDeckSize + ", side deck " + sideDeckSize + ", invalid");
            }
        }
    }

    public static void showSideDeck(String input) {
        String deckName = null;
        if (input.matches("deck show --deck-name (.+) --side") ||
                input.matches("deck show --side --deck-name (.+)")) {
            Matcher matcher = getCommandMatcher(input, "deck show --deck-name (.+) --side");
            Matcher matcher1 = getCommandMatcher(input, "deck show --side --deck-name (.+)");
            if (matcher.find()) {
                deckName = matcher.group(1);
            } else if (matcher1.find()) {
                deckName = matcher1.group(1);
            }
        } else {
            System.out.println("invalid command");
            return;
        }

        if (!player.hasADeck(player.getDeckByName(deckName)))
            System.out.println("deck with name " + deckName + " does not exist");
        else {
            ArrayList<MonsterCard> monsterCards = new ArrayList<MonsterCard>();
            ArrayList<TrapAndSpellCard> trapAndSpellCards = new ArrayList<TrapAndSpellCard>();
            for (Card card : player.getDeckByName(deckName).getSideDeck()) {
                if (card instanceof MonsterCard)
                    monsterCards.add((MonsterCard) card);
                else trapAndSpellCards.add((TrapAndSpellCard) card);
            }
            Collections.sort(monsterCards);
            Collections.sort(trapAndSpellCards);

            System.out.println("Deck: " + deckName + "\n" +
                    "Side deck\n" +
                    "Monsters:");
            printCards(monsterCards, trapAndSpellCards);
        }
    }


    public static void showMainDeck(Matcher matcher) {
        if (matcher.find()) {
            String deckName = matcher.group(2);
            if (!player.hasADeck(player.getDeckByName(deckName)))
                System.out.println("deck with name " + deckName + " does not exist");
            else {
                ArrayList<MonsterCard> monsterCards = new ArrayList<MonsterCard>();
                ArrayList<TrapAndSpellCard> trapAndSpellCards = new ArrayList<TrapAndSpellCard>();
                for (Card card : player.getDeckByName(deckName).getMainDeck()) {
                    if (card instanceof MonsterCard)
                        monsterCards.add((MonsterCard) card);
                    else trapAndSpellCards.add((TrapAndSpellCard) card);
                }
                Collections.sort(monsterCards);
                Collections.sort(trapAndSpellCards);

                System.out.println("Deck: " + deckName + "\n" +
                        "Main deck\n" +
                        "Monsters:");
                printCards(monsterCards, trapAndSpellCards);
            }
        }
    }

    private static void showAllCards() {
        ArrayList<Card> allCards = player.getAllCards();
        Collections.sort(allCards);
        for (Card card : allCards)
            System.out.println(card.getCardName() + ": " + card.getCardDescription());
    }

    private static void help() {
        System.out.println("menu exit\n" +
                "menu-show current\n" +
                "card show (card name)\n" +
                "deck create (deck name)\n" +
                "deck delete (deck name)\n" +
                "deck set-activate (deck name)\n" +
                "deck add-card --card (card name) --deck (deck name)\n" +
                "deck add-card --card (card name) --deck (deck name) --side\n" +
                "deck rm-card --card (card name) --deck (deck name)\n" +
                "deck rm-card --card (card name) --deck (deck name) --side\n" +
                "deck show --all\n" +
                "deck show --deck-name (deck name)\n" +
                "deck show --deck-name (deck name) --side\n" +
                "deck show --cards");
    }

    private static void printCards(ArrayList<MonsterCard> monsterCards, ArrayList<TrapAndSpellCard> trapAndSpellCards) {
        for (int i = 0; i < monsterCards.size(); i++) {
            System.out.println(monsterCards.get(i).getCardName() + ": " + monsterCards.get(i).getCardDescription());
        }
        System.out.println("Spell and Traps:");
        for (int i = 0; i < trapAndSpellCards.size(); i++)
            System.out.println(trapAndSpellCards.get(i).getCardName() + ": " + trapAndSpellCards.get(i).getCardDescription());
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
