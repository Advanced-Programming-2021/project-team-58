public class MonsterCard extends Card{
    private String cardType;
    private int cardLevel;
    private Attribute cardAttribute;
    private int attack;
    private int defense;
    private boolean isEffectExists;
    private Effect effect;

    public MonsterCard(String cardName,String cardType,int cardNumber, int cardLevel,String discription, Attribute cardAttribute, int attack, int defense) {
        this.cardType = cardType;
        this.cardLevel = cardLevel;
        this.cardAttribute = cardAttribute;
        this.attack = attack;
        this.defense = defense;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public Attribute getCardAttribute() {
        return cardAttribute;
    }

    public void setCardAttribute(Attribute cardAttribute) {
        this.cardAttribute = cardAttribute;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setEffectExists(boolean tmp) {
        this.isEffectExists = tmp;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    public void increaseAttack(int attack){

    }
    public void increaseDefense(int defense){

    }
    public void decreaseAttack(int attack){

    }
    public void decreaseDefense(int defense){

    }

}
