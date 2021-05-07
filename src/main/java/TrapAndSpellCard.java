public class TrapAndSpellCard extends Card{
    private TrapOrSpellTypes cardType;
    private TrapOrSpellIcons cardIcon;
    private TrapAndSpellStatus cardStatus;

    public TrapAndSpellStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(TrapAndSpellStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public TrapAndSpellCard(String cardName, TrapOrSpellTypes cardType, TrapOrSpellIcons cardIcon, String cardDescription, TrapAndSpellStatus status, int price) {
        setCardType(cardType);
        setCardIcon(cardIcon);
        setCardStatus(status);
        super.setCardName(cardName);
        super.setCardDescription(cardDescription);
        super.setCardPrice(price);
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
        System.out.println("Type: " + this.getCardType());
        System.out.println("Description: " + super.getCardDescription());
    }
    else if (this.getTrapOrSpellTypes()== TrapOrSpellTypes.TRAP_CARD){
        System.out.println("Name: " + super.getCardName());
        System.out.println("Trap");
        System.out.println("Type: " + this.getCardType());
        System.out.println("Description: " + super.getCardDescription());
    }
    }
}