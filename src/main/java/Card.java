import java.util.ArrayList;

abstract public class Card {

    private String cardName;
    private String cardDescription;
    private int cardNumber;
    private static ArrayList<Card> allCards = new ArrayList<>();

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
}
