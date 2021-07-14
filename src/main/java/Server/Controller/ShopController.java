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

public class ShopController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.equals("Shop get cards")) {
            dataOutputStream.writeUTF(getAllCardsName());
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
        }
        dataOutputStream.flush();
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
        }
        return "";
    }

    private static String getCardSrc(String cardName) {
        return Card.getCardByName(cardName).getImageSrc();
    }

    private static String getCardPrice(String cardName) {
        return String.valueOf(Card.getCardByName(cardName).getPrice());
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
}


