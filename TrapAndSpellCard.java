public class TrapAndSpellCard extends Card{
    private TrapOrSpellTypes cardType;
    private TrapOrSpellIcons cardIcon;

    public TrapAndSpellCard(TrapOrSpellTypes cardType,TrapOrSpellIcons cardIcon,String cardName,String cardDescription) {
        setCardType(cardType);
        setCardIcon(cardIcon);
    }

    public TrapOrSpellTypes getCardType() {
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
    if(this.getCardType()== TrapOrSpellTypes.SPELL_CARD){
        System.out.println("Name: " + super.getCardName());
        System.out.println("Spell");
        System.out.println("Type: " + this.getCardType());
        System.out.println("Description: " + super.getCardDescription());
    }
    else if (this.getCardType()== TrapOrSpellTypes.TRAP_CARD){
        System.out.println("Name: " + super.getCardName());
        System.out.println("Trap");
        System.out.println("Type: " + this.getCardType());
        System.out.println("Description: " + super.getCardDescription());
    }
    }
}