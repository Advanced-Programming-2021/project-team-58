import java.util.ArrayList;

abstract public class Card implements Comparable<Card>{

    private String cardName;
    private String cardDescription;
    private int cardNumber;
    private static ArrayList<Card> allCards = new ArrayList<Card>();
//    private static ArrayList<Card> allAvailableCards = new ArrayList<Card>();

    public Card() {
        allCards.add(this);
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static Card getCardByName(String cardName) {
        for (Card card : allCards) {
            if (card.getCardName().equals(cardName)) return card;
        }
        return null;
    }

    abstract public void showCard();

    public int compareTo(Card anotherCard) {
        return this.getCardName().compareTo(anotherCard.getCardName());
    }

}
