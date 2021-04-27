public class TrapAndSpellCard extends Card{
    private TrapOrSpellTypes cardType;
    private TrapOrSpellIcon cardIcon;

    public TrapAndSpellCard(TrapOrSpellTypes cardType, TrapOrSpellIcon cardIcon,String cardName,String cardDiscription) {
        this.cardType = cardType;
        this.cardIcon = cardIcon;
    }

    public TrapOrSpellTypes getCardType() {
        return cardType;
    }

    public void setCardType(TrapOrSpellTypes cardType) {
        this.cardType = cardType;
    }

    public TrapOrSpellIcon getCardIcon() {
        return cardIcon;
    }

    public void setCardIcon(TrapOrSpellIcon cardIcon) {
        this.cardIcon = cardIcon;
    }

    @Override
    public void showCard(String cardName) {
    }
}
