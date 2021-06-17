package Model;

import Controller.*;
import View.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MonsterCard.class, name = "Monster"),
        @JsonSubTypes.Type(value = TrapAndSpellCard.class, name = "Trap"),
})

public abstract class Card implements Comparable<Card> {

    private String cardName;
    private String cardDescription;
    private int price;
    protected static ArrayList<Card> allCards = new ArrayList<Card>();

    public Card(String cardName, String cardDescription, int price) {
        setCardName(cardName);
        setCardDescription(cardDescription);
        setPrice(price);
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
