package Server.Controller;

import Server.Model.Auction;
import Server.Model.Card;
import Server.Model.Player;
import Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AuctionController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.equals("Auction send data"))
            sendData(dataOutputStream);
        else if (message.startsWith("Auction player cards data"))
            dataOutputStream.writeUTF(sendPlayerCardsData(message.substring(25)));
        else if (message.startsWith("Auction create new auction"))
            createNewAuction(message.substring(26));
        else if (message.startsWith("Auction offer bid")) {
            setOffer(message.substring(17));
        } else if (message.startsWith("Auction expire auction")) {
            expireAuction(message.substring(22));
        }
        dataOutputStream.flush();
    }

    private static void expireAuction(String str) {
        int id = Integer.parseInt(str);
        Auction auction = Auction.getAuctionByID(id);
        Auction.getAllAuctions().remove(auction);
    }

    private static void setOffer(String str) {
        String[] tmp = str.split("#");
        int id = Integer.parseInt(tmp[0]);
        String token = tmp[1];
        int offeredPrice = Integer.parseInt(tmp[2]);
        Player player = allLoggedInPlayers.get(token);
        Auction auction = Auction.getAuctionByID(id);
        auction.setLastPriceOfferer(player);
        auction.setLastPriceOffered(offeredPrice);
    }

    private static void createNewAuction(String str) {
        String[] tmp = str.split("#");
        String token = tmp[0];
        String cardName = tmp[1];
        int basePrice = Integer.parseInt(tmp[2]);

        Player player = allLoggedInPlayers.get(token);
        Card card = Card.getCardByName(cardName);

        new Auction(card.getImageSrc(), player, basePrice, card.getCardName());
    }

    private static String sendPlayerCardsData(String token) {
        Player player = allLoggedInPlayers.get(token);
        ArrayList<Card> allCards = player.getAllCards();
        String result = "";
        for (int i = 0; i < allCards.size(); i++) {
            if (i == 0)
                result = allCards.get(i).getImageSrc() + "#" + allCards.get(i).getCardName();
            else
                result = result + "#" + allCards.get(i).getImageSrc() + "#" + allCards.get(i).getCardName();
        }
        return result;
    }

    private static void sendData(DataOutputStream dataOutputStream) throws IOException {
        String result = "";
        ArrayList<Auction> allAuctions = Auction.getAllAuctions();
        for (int i = 0; i < allAuctions.size(); i++) {
            if (i == 0) {
                result = allAuctions.get(i).getAuctionImgSrc() + "#" + allAuctions.get(i).getAuctionOwner().getNickname() + "#" +
                        allAuctions.get(i).getLastPriceOffered() + "#" + allAuctions.get(i).getAuctionCardName() + "#" + allAuctions.get(i).getId();
            } else
                result = result + "#" + allAuctions.get(i).getAuctionImgSrc() + "#" + allAuctions.get(i).getAuctionOwner().getNickname() + "#" +
                        allAuctions.get(i).getLastPriceOffered() + "#" + allAuctions.get(i).getAuctionCardName() + "#" + allAuctions.get(i).getId();
        }
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();
    }
}
