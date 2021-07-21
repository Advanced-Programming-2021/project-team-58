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
        else if (message.startsWith("Auction player cards data")) {
            dataOutputStream.writeUTF(sendPlayerCardsData(message.substring(25)));
        }
        dataOutputStream.flush();
    }

    private static String sendPlayerCardsData(String token) {
        Player player = allLoggedInPlayers.get(token);
        ArrayList<Card> allCards = player.getAllCards();
        String result = "";
        for (int i = 0; i < allCards.size(); i++) {
            if (i == 0)
                result = allCards.get(i).getImageSrc();
            else
                result = result + "#" + allCards.get(i).getImageSrc();
        }
        return result;
    }

    private static void sendData(DataOutputStream dataOutputStream) throws IOException {
        Auction auction = new Auction("/Images/Monster/BattleOx.jpg", Player.getPlayerByUsername("marzie"), 23555, "Battle OX");
        String result = "";
        ArrayList<Auction> allAuctions = Auction.getAllAuctions();
        for (int i = 0; i < allAuctions.size(); i++) {
            if (i == 0) {
                result = allAuctions.get(i).getAuctionImgSrc() + "#" + allAuctions.get(i).getAuctionOwner().getNickname() + "#" +
                        allAuctions.get(i).getLastPriceOffered() + "#" + allAuctions.get(i).getAuctionCardName();
            } else
                result = result + "#" + allAuctions.get(i).getAuctionImgSrc() + "#" + allAuctions.get(i).getAuctionOwner().getNickname() + "#" +
                        allAuctions.get(i).getLastPriceOffered() + "#" + allAuctions.get(i).getAuctionCardName();
        }
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();
    }
}
