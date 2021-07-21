package Server.Controller;

import Server.Model.Auction;
import Server.Model.Player;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AuctionController {

    public static void processInput(String message, DataOutputStream dataOutputStream) throws IOException {
        if (message.equals("Auction send data"))
            sendData(dataOutputStream);
    }

    private static void sendData(DataOutputStream dataOutputStream) throws IOException {
        Auction auction = new Auction("/Monster/BattleOx.jpg", Player.getPlayerByUsername("marzie"), 23555, "Battle OX");
        String result = "";
        ArrayList<Auction> allAuctions = Auction.getAllAuctions();
        for (int i = 0; i < allAuctions.size(); i++) {
            if (i == 0) {
                result = allAuctions.get(i).getAuctionImgSrc() + "#" + allAuctions.get(i).getAuctionOwner() + "#" +
                    allAuctions.get(i).getLastPriceOffered() + "#" + allAuctions.get(i).getAuctionCardName();
            } else result = result + "#" + allAuctions.get(i).getAuctionImgSrc() + "#" + allAuctions.get(i).getAuctionOwner() + "#" +
                    allAuctions.get(i).getLastPriceOffered() + "#" + allAuctions.get(i).getAuctionCardName();
        }
        dataOutputStream.writeUTF(result);
        dataOutputStream.flush();
    }
}
