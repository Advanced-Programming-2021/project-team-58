public class MonsterCard extends Card {
    private String cardType;
    private int cardLevel;
    private Attribute cardAttribute;
    private int attack;
    private int defense;
    private boolean isEffectExists;
    private Effects effect;

    public MonsterCard(String cardName, String cardType, int cardNumber, int cardLevel, String description, Attribute cardAttribute, int attack, int defense) {
        setCardType(cardType);
        setCardLevel(cardLevel);
        setCardAttribute(cardAttribute);
        setAttack(attack);
        setDefense(defense);
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

    public Effects getEffect() {
        return effect;
    }

    public void setEffect(Effects effect) { this.effect = effect; }

    public void increaseAttack(int attack) {
    this.attack+=attack;
    }

    public void increaseDefense(int defense) {
    this.defense+=defense;
    }

    public void decreaseAttack(int attack) {
    this.attack -= attack;
    }

    public void decreaseDefense(int defense) {
    this.defense -= defense;
    }

    public void showCard(){
        System.out.println("Name: " + this.getCardName());
        System.out.println("Level: " + this.getCardLevel());
        System.out.println("Type: " + this.getCardType());
        System.out.println("ATK: " + this.getAttack());
        System.out.println("DEF: " + this.getDefense());
        System.out.println("Description: " + this.getCardDescription());
    }

}
