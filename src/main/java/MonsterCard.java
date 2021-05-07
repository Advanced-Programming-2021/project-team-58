public class MonsterCard extends Card {
    private String monsterType;
    private int cardLevel;
    private Attribute cardAttribute;
    private int attack;
    private int defense;
    private boolean isEffectExists;
    private Effects effect;

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public MonsterCard(String cardName, String cardType, String monsterType, int cardLevel, String description,
                       Attribute cardAttribute, int attack, int defense, int price) {
        super.setCardType(cardType);
        setCardLevel(cardLevel);
        setCardAttribute(cardAttribute);
        setAttack(attack);
        setDefense(defense);
        super.setCardName(cardName);
//        super.setCardNumber(cardNumber);
        super.setCardDescription(description);
        super.setCardPrice(price);
        setMonsterType(monsterType);
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
        System.out.println("Type: " + this.getMonsterType());
        System.out.println("ATK: " + this.getAttack());
        System.out.println("DEF: " + this.getDefense());
        System.out.println("Description: " + this.getCardDescription());
    }

}
