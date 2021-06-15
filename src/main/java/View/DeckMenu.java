package View;

import Controller.*;
import Model.*;


import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu {
    static Player player = LoginMenu.getLoggedInPlayer();

    public static void run() {
        handleInput();
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
            else if (input.trim().matches("^(?i)(deck add-card (--.+) (--.+) (--.+))$"))
                addCardToSideDeck(getCommandMatcher(input, "^(?i)(deck add-card (--.+) (--.+) (--.+))$"));
            else if (input.trim().matches("^(?i)(deck add-card (--.+) (--.+))$"))
                addCardToMainDeck(input);
            else if (input.trim().matches("^(?i)(deck rm-card (--.+) (--.+) (--.+))$"))
                removeCardFromSideDeck(getCommandMatcher(input, "^(?i)(deck rm-card (--.+) (--.+) (--.+))$"));
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
            else Card.getCardByName(cardName).showCard();
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
                Deck.removeDeckFromAllDecks(player.getDeckByName(deckName));
                if (player.getActiveDeck() != null)
                    if (player.getActiveDeck().getDeckName().equals(deckName))
                        player.setActiveDeck(null);
                player.getDecks().remove(player.getDeckByName(deckName));
                System.out.println("deck with name " + deckName + " deleted successfully");
            }
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
            if (!player.getAllCards().contains(Card.getCardByName(cardName)))
                System.out.println("card with name " + cardName + " does not exist");
            else if (!player.hasADeck(player.getDeckByName(deckName)))
                System.out.println("deck with name " + deckName + " does not exist");
            else if (player.getDeckByName(deckName).getMainDeckSize() == 60)
                System.out.println("main deck is full");
            else if (player.getDeckByName(deckName).getNumOfCardInDeck(Card.getCardByName(cardName)) == 3)
                System.out.println("there are already three cards with name " + cardName + " in deck " + deckName);
            else {
                player.getDeckByName(deckName).addCardToMainDeck(Card.getCardByName(cardName));
                player.getAllCards().remove(Card.getCardByName(cardName));
                System.out.println("card added to deck successfully");
            }
        }
    }

    private static void addCardToSideDeck(Matcher matcher) {
        if (matcher.find()) {
            String cardName = findCardName(matcher);
            String deckName = findDeckName(matcher);
            boolean isInSideDeck = isInSideDeck(matcher);

            if (cardName != null && deckName != null && isInSideDeck) {
                if (!player.getAllCards().contains(Card.getCardByName(cardName)))
                    System.out.println("card with name " + cardName + " does not exist");
                else if (!player.getDecks().contains(player.getDeckByName(deckName)))
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
    }


    public static void removeCardFromMainDeck(Matcher matcher) {
        if (matcher.find()) {
            String cardName = findCardName(matcher);
            String deckName = findDeckName(matcher);
            if (cardName != null && deckName != null) {
                if (!player.getDecks().contains(player.getDeckByName(deckName)))
                    System.out.println("deck with name " + deckName + " does not exist");
                else {
                    Card card = Card.getCardByName(cardName);
                    boolean isCardInMainDeckExists = player.getDeckByName(deckName).getMainDeck().contains(card);
                    if (card == null || !isCardInMainDeckExists)
                        System.out.println("card with name " + cardName + " does not exist in main deck");
                    else {
                        player.getDeckByName(deckName).removeCardFromMainDeck(Card.getCardByName(cardName));
                        player.getAllCards().add(Card.getCardByName(cardName));
                        System.out.println("card removed form deck successfully");
                    }
                }
            } else System.out.println("invalid command");
        }
    }

    public static void removeCardFromSideDeck(Matcher matcher) {
        if (matcher.find()) {
            String cardName = findCardName(matcher);
            String deckName = findDeckName(matcher);
            boolean isInSideDeck = isInSideDeck(matcher);

            if (cardName != null && deckName != null && isInSideDeck) {
                if (!player.getDecks().contains(player.getDeckByName(deckName)))
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
    }

    public static void showAllDecks() {
        System.out.println("hello everyone");
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
                System.out.println(mainDeckSize>=5);
                System.out.println(mainDeckSize<=10);
                System.out.println(mainDeckSize<=15);

                if (decks.get(i).isValid())
                    System.out.println(deckName + ": main deck " + mainDeckSize + ", side deck " + sideDeckSize + ", valid");
                else
                    System.out.println(deckName + ": main deck " + mainDeckSize + ", side deck " + sideDeckSize + ", invalid");
            }
        }
    }

    private static String findCardName(Matcher matcher) {
        String cardName = null;
        if (matcher.find()) {
            Matcher matcher1;
            for (int i = 2; i <= matcher.groupCount(); i++) {
                if (matcher.group(i).matches("^(?i)(--card (.+))$")) {
                    matcher1 = getCommandMatcher(matcher.group(i), "^(?i)(--card (.+))$");
                    if (matcher1.find())
                        cardName = matcher1.group(2);
                }
            }
        }
        return cardName;
    }

    private static String findDeckName(Matcher matcher) {
        String deckName = null;
        if (matcher.find()) {
            Matcher matcher1;
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i).matches("^(?i)(--deck (.+))$")) {
                    matcher1 = getCommandMatcher(matcher.group(i), "^(?i)(--deck (.+))$");
                    if (matcher1.find())
                        deckName = matcher1.group(2);
                }
            }
        }
        return deckName;
    }

    private static boolean isInSideDeck(Matcher matcher) {
        boolean isInSideDeck = false;
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++)
                if (matcher.group(i).matches("^(?i)(--side)$"))
                    isInSideDeck = true;
        }
        return isInSideDeck;
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

    private static void printCards(ArrayList<MonsterCard> monsterCards, ArrayList<TrapAndSpellCard> trapAndSpellCards) {
        for (int i = 0; i < monsterCards.size(); i++) {
            System.out.println(monsterCards.get(i).getCardName() + ": " + monsterCards.get(i).getCardDescription());
        }
        System.out.println("Spell and Traps:");
        for (int i = 0; i < trapAndSpellCards.size(); i++)
            System.out.println(trapAndSpellCards.get(i).getCardName() + ": " + trapAndSpellCards.get(i).getCardDescription());
    }

    private static void showAllCards() {
        ArrayList<Card> allCards = player.getAllCards();
        Collections.sort(allCards);
        for (Card card : allCards)
            System.out.println(card.getCardName() + ": " + card.getCardDescription());
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
