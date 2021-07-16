package Server.Controller;

import Server.Model.Card;
import Server.Model.MonsterCard;
import Server.Model.Player;
import Server.Model.TrapAndSpellCard;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ShopController {

    private static ArrayList<Card> forbiddenCardsShop = new ArrayList<>();

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.equals("Shop get cards")) {
            dataOutputStream.writeUTF(getAllCardsName());
            forbiddenCardsShop.add(Card.getCardByName("Battle OX"));
            forbiddenCardsShop.add(Card.getCardByName("Bitron"));
            forbiddenCardsShop.add(Card.getCardByName("Silver Fang"));
            forbiddenCardsShop.add(Card.getCardByName("Yomi Ship"));
        } else if (message.startsWith("Shop get card")) {
            dataOutputStream.writeUTF(handleCardInfo(message.substring(13)));
        } else if (message.startsWith("Shop num of card")) {
            dataOutputStream.writeUTF(numberOfCardsInAllCards(message.substring(16)));
        } else if (message.startsWith("Shop buy")) {
            buyCard(message.substring(8));
        } else if (message.startsWith("Shop show info")) {
            dataOutputStream.writeUTF(showInfo(message.substring(14)));
        } else if (message.startsWith("Shop number of card")) {
            dataOutputStream.writeUTF(numberOfCardsInShop(message.substring(19)));
        } else if (message.startsWith("Shop is card forbidden")) {
            dataOutputStream.writeUTF(isCardForbidden(message.substring(22)));
        } else if (message.startsWith("Shop forbid card")) {
            forbidCard(message.substring(16));
        } else if (message.startsWith("Shop sell")){
            sellCard(message.substring(9));
        } else if (message.startsWith("Shop forbid")){
            forbidCard(message.substring(11));
        } else if (message.startsWith("Shop decrease shop card")){
            decreaseShopCard(message.substring(23));
        } else if (message.startsWith("Shop increase shop card")){
            increaseShopCard(message.substring(23));

        }
        dataOutputStream.flush();
    }

    private static void increaseShopCard(String cardName) {
        Card.getCardByName(cardName).increaseNumberOfCardInShop();
    }

    private static void decreaseShopCard(String cardName) {
        Card.getCardByName(cardName).decreaseNumberOfCardInShop();
    }

    private static void sellCard(String str) {
        String[] tmp = str.split("#");
        String cardName = tmp[0];
        String token = tmp[1];
        allLoggedInPlayers.get(token).getAllCards().remove(Card.getCardByName(cardName));
        allLoggedInPlayers.get(token).increaseMoney(Card.getCardByName(cardName).getPrice());
        Card.getCardByName(cardName).increaseNumberOfCardInShop();
    }

    private static String numberOfCardsInShop(String cardName) {
        return String.valueOf(Card.getCardByName(cardName).getNumberOfCardInShop());
    }

    private static String showInfo(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card instanceof MonsterCard) {
            return ("Level: " + ((MonsterCard) card).getCardLevel() +
                    "\nType: " + ((MonsterCard) card).getMonsterType() +
                    "\nATK: " + ((MonsterCard) card).getAttack() +
                    "\nDEF: " + ((MonsterCard) card).getDefense() +
                    "\nDescription: " + card.getCardDescription());
        } else {
            TrapAndSpellCard TPCard = (TrapAndSpellCard) card;
            return ("Type: " + TPCard.getTrapOrSpellTypes() +
                    "\nDescription: " + TPCard.getCardDescription());
        }
    }

    private static String getAllCardsName() {
        String result = "";
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            Card card = Card.getAllCards().get(i);
            if (i == Card.getAllCards().size() - 1)
                result = result + card.getCardName();
            else
                result = result + card.getCardName() + "#";
        }
        return result;
    }

    private static String numberOfCardsInAllCards(String str) {
        String[] tmp = str.split("#");
        String cardName = tmp[0];
        String token = tmp[1];

        ArrayList<Card> allCards = allLoggedInPlayers.get(token).getAllCards();
        int result = 0;
        for (Card card : allCards) {
            if (card.getCardName().equals(cardName)) {
                result++;
            }
        }
        return String.valueOf(result);
    }

    private static String handleCardInfo(String str) {
        String[] tmp = str.split("#");
        String info = tmp[0];
        String cardName = tmp[1];
        if (info.equals("price")) {
            return getCardPrice(cardName);
        } else if (info.equals("imageSrc")) {
            return getCardSrc(cardName);
        } else {
            return "";
        }
    }

    private static String getCardSrc(String cardName) {
        return Objects.requireNonNull(Card.getCardByName(cardName)).getImageSrc();
    }

    private static String getCardPrice(String cardName) {
        return String.valueOf(Objects.requireNonNull(Card.getCardByName(cardName)).getPrice());
    }

    private static void buyCard(String str) {
        String[] tmp = str.split("#");
        String cardName = tmp[0];
        String token = tmp[1];

        Player player = allLoggedInPlayers.get(token);
        Card card = Card.getCardByName(cardName);
        player.decreaseMoney(card.getPrice());
        player.getAllCards().add(card);
        card.decreaseNumberOfCardInShop();
    }

    private static String isCardForbidden(String cardName) {
        return String.valueOf(forbiddenCardsShop.contains(Card.getCardByName(cardName)));
    }

    private static void forbidCard(String cardName) {
        forbiddenCardsShop.add(Card.getCardByName(cardName));
    }
}


