package Server.Controller;

import Server.Model.Card;
import Server.Model.Deck;
import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class DeckController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.startsWith("Deck player cards")) {
            dataOutputStream.writeUTF(getPlayerAllCards(message.substring(17)));
        } else if (message.startsWith("Deck player decks")) {
            dataOutputStream.writeUTF(getPlayerAllDecks(message.substring(17)));
        } else if (message.startsWith("Deck main deck cards")) {
            dataOutputStream.writeUTF(getMainDeckCards(message.substring(20)));
        } else if (message.startsWith("Deck side deck cards")) {
            dataOutputStream.writeUTF(getSideDeckCards(message.substring(20)));
        } else if (message.startsWith("Deck add card player")) {
            addCardToPlayerCards(message.substring(20));
        } else if (message.startsWith("Deck remove deck player")) {
            removeDeckFromPlayerDecks(message.substring(23));
        } else if (message.startsWith("Deck player has deck")) {
            dataOutputStream.writeUTF(hasADeck(message.substring(20)));
        } else if (message.startsWith("Deck create new deck")) {
            createNewDeck(message.substring(20));
        } else if (message.startsWith("Deck remove card from side")) {
            removeCardFromSideByIndex(message.substring(26));
        } else if (message.startsWith("Deck remove card from main")) {
            removeCardFromMainByIndex(message.substring(26));
        } else if (message.startsWith("Deck remove from player cards")) {
            removeCardFromPlayerCardsByIndex(message.substring(29));
        } else if (message.startsWith("Deck add card to main")) {
            addCardToMainDeck(message.substring(21));
        } else if (message.startsWith("Deck add card to side")) {
            addCardToSideDeck(message.substring(21));
        } else if (message.startsWith("Deck set activate")) {
            setActivateDeck(message.substring(17));
        }
        dataOutputStream.flush();
    }

    private static void setActivateDeck(String str) {
        String[] tmp = str.split("#");
        String deckName = tmp[0];
        String token = tmp[1];
        Deck deck = allLoggedInPlayers.get(token).getDeckByName(deckName);
        allLoggedInPlayers.get(token).setActiveDeck(deck);
    }

    private static void addCardToSideDeck(String str) {
        String[] tmp = str.split("#");
        String cardName = tmp[0];
        String deckName = tmp[1];
        String token = tmp[2];

        Card card = Card.getCardByName(cardName);
        Deck deck = allLoggedInPlayers.get(token).getDeckByName(deckName);
        deck.addCardToSideDeck(card);
    }

    private static void addCardToMainDeck(String str) {
        String[] tmp = str.split("#");
        String cardName = tmp[0];
        String deckName = tmp[1];
        String token = tmp[2];

        Card card = Card.getCardByName(cardName);
        Deck deck = allLoggedInPlayers.get(token).getDeckByName(deckName);
        deck.addCardToMainDeck(card);
    }

    private static void removeCardFromMainByIndex(String str) {
        String[] tmp = str.split("#");
        int index = Integer.parseInt(tmp[0]);
        String deckName = tmp[1];
        String token = tmp[2];

        Deck deck = allLoggedInPlayers.get(token).getDeckByName(deckName);
        deck.getMainDeck().remove(index);
    }

    private static void removeCardFromSideByIndex(String str) {
        String[] tmp = str.split("#");
        int index = Integer.parseInt(tmp[0]);
        String deckName = tmp[1];
        String token = tmp[2];

        Deck deck = allLoggedInPlayers.get(token).getDeckByName(deckName);
        deck.getSideDeck().remove(index);
    }

    private static void removeCardFromPlayerCardsByIndex(String str) {
        String[] tmp = str.split("#");
        int index = Integer.parseInt(tmp[0]);
        String token = tmp[1];

        allLoggedInPlayers.get(token).getAllCards().remove(index);
    }

    private static void createNewDeck(String str) {
        String[] tmp = str.split("#");
        String deckName = tmp[0];
        String token = tmp[1];
        Deck deck = new Deck(deckName);
        allLoggedInPlayers.get(token).getDecks().add(deck);
    }

    private static String hasADeck(String str) {
        String[] tmp = str.split("#");
        String deckName = tmp[0];
        String token = tmp[1];
        return String.valueOf((allLoggedInPlayers.get(token).getDeckByName(deckName) != null));
    }

    private static void removeDeckFromPlayerDecks(String str) {
        String[] tmp = str.split("#");
        String deckName = tmp[0];
        String token = tmp[1];
        Deck deck = allLoggedInPlayers.get(token).getDeckByName(deckName);
        allLoggedInPlayers.get(token).getDecks().remove(deck);
    }

    private static void addCardToPlayerCards(String str) {
        String[] tmp = str.split("#");
        String cardName = tmp[0];
        String token = tmp[1];
        allLoggedInPlayers.get(token).getAllCards().add(Card.getCardByName(cardName));
    }

    private static String getMainDeckCards(String str) {
        String[] tmp = str.split("#");
        String deckName = tmp[0];
        String token = tmp[1];

        ArrayList<Card> mainDeck = allLoggedInPlayers.get(token).getDeckByName(deckName).getMainDeck();
        String result = "";
        for (int i = 0; i < mainDeck.size(); i++) {
            if (i == mainDeck.size() - 1) {
                result = result + mainDeck.get(i).getCardName();
            } else {
                result = result + mainDeck.get(i).getCardName() + "#";
            }
        }
        return result;
    }

    private static String getSideDeckCards(String str) {
        String[] tmp = str.split("#");
        String deckName = tmp[0];
        String token = tmp[1];

        ArrayList<Card> sideDeck = allLoggedInPlayers.get(token).getDeckByName(deckName).getSideDeck();
        String result = "";
        for (int i = 0; i < sideDeck.size(); i++) {
            if (i == sideDeck.size() - 1) {
                result = result + sideDeck.get(i).getCardName();
            } else {
                result = result + sideDeck.get(i).getCardName() + "#";
            }
        }
        return result;
    }

    private static String getPlayerAllDecks(String token) {
        Player player = allLoggedInPlayers.get(token);
        LinkedList<Deck> allDecks = player.getDecks();
        String result = "";
        for (int i = 0; i < allDecks.size(); i++) {
            if (i == allDecks.size() - 1) {
                result = result + allDecks.get(i).getDeckName();
            } else {
                result = result + allDecks.get(i).getDeckName() + "#";
            }
        }
        return result;
    }

    private static String getPlayerAllCards(String token) {
        Player player = allLoggedInPlayers.get(token);
        ArrayList<Card> allCards = player.getAllCards();
        String result = "";
        for (int i = 0; i < allCards.size(); i++) {
            if (i == allCards.size() - 1) {
                result = result + allCards.get(i).getCardName();
            } else {
                result = result + allCards.get(i).getCardName() + "#";
            }
        }
        return result;
    }
}
