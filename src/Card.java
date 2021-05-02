import java.util.ArrayList;

public class Card {

    private String cardName;
    private String cardDescription;
    private int cardNumber;
    private ArrayList<Card> allCards = new ArrayList<>();

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
        for (Card allCard : allCards) {
            if (allCard.getCardName() == (cardName)) return allCard;
        }
        return null;
    }
}
