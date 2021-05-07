import java.util.ArrayList;

public class TrapAndSpellCard extends Card{
    private TrapOrSpellTypes cardType;
    private TrapOrSpellIcons cardIcon;
    private TrapAndSpellStatus cardStatus;
    private static ArrayList<TrapAndSpellCard> allSpellOrTrapCards = new ArrayList<>();
    public TrapAndSpellStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(TrapAndSpellStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public TrapAndSpellCard(String cardName, TrapOrSpellTypes cardType, TrapOrSpellIcons cardIcon, String cardDescription, TrapAndSpellStatus status, int price) {
        super(cardName,cardDescription,price);
        setCardType(cardType);
        setCardIcon(cardIcon);
        setCardStatus(status);
        allSpellOrTrapCards.add(this);
    }

    public TrapOrSpellTypes getTrapOrSpellTypes() {
        return cardType;
    }
    public void setCardType(TrapOrSpellTypes cardType) {
        this.cardType = cardType;
    }
    public TrapOrSpellIcons getCardIcon() {
        return cardIcon;
    }
    public void setCardIcon(TrapOrSpellIcons cardIcon) {
        this.cardIcon = cardIcon;
    }

    public void showCard() {

        if(this.getTrapOrSpellTypes()== TrapOrSpellTypes.SPELL_CARD){
        System.out.println("Name: " + super.getCardName());
        System.out.println("Spell");
        System.out.println("Type: " + this.getTrapOrSpellTypes());
        System.out.println("Description: " + super.getCardDescription());
    }
        else if (this.getTrapOrSpellTypes()== TrapOrSpellTypes.TRAP_CARD){
        System.out.println("Name: " + super.getCardName());
        System.out.println("Trap");
        System.out.println("Type: " + this.getTrapOrSpellTypes());
        System.out.println("Description: " + super.getCardDescription());
    }

    }
}