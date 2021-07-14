package Server.Controller;

import Server.Model.Card;
import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ShopController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.equals("Shop get cards")) {
            dataOutputStream.writeUTF(getAllCardsName());
        } else if (message.startsWith("Shop get card")) {
            dataOutputStream.writeUTF(handleCardInfo(message.substring(13)));
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

    private static String handleCardInfo(String str) {
        String[] tmp = str.split("#");
        String info = tmp[0];
        String cardName = tmp[1];
        if(info.equals("price")){
            return getCardPrice(cardName);
        }
        else if(info.equals("imageSrc")){
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
}
