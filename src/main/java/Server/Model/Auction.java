package Server.Model;

import java.util.ArrayList;

public class Auction {
    Player auctionOwner;
    int lastPriceOffered;
    String auctionCardName;
    String auctionImgSrc;

    static ArrayList<Auction> allAuctions = new ArrayList<>();

    public Auction(String auctionImgSrc, Player auctionOwner, int lastPriceOffered, String auctionCardName) {
        this.auctionImgSrc = auctionImgSrc;
        this.auctionOwner = auctionOwner;
        this.lastPriceOffered = lastPriceOffered;
        this.auctionCardName = auctionCardName;
        allAuctions.add(this);
    }

    public void setAuctionCardName(String auctionCardName) {
        this.auctionCardName = auctionCardName;
    }

    public void setAuctionOwner(Player auctionOwner) {
        this.auctionOwner = auctionOwner;
    }

    public void setLastPriceOffered(int lastPriceOffered) {
        this.lastPriceOffered = lastPriceOffered;
    }

    public void setAuctionImgSrc(String auctionImgSrc) {
        this.auctionImgSrc = auctionImgSrc;
    }

    public Player getAuctionOwner() {
        return auctionOwner;
    }

    public int getLastPriceOffered() {
        return lastPriceOffered;
    }

    public String getAuctionCardName() {
        return auctionCardName;
    }

    public String getAuctionImgSrc() {
        return auctionImgSrc;
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }
}
