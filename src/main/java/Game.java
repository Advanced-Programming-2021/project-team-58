import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    private Phase phase;
    private Player turnOfPlayer;
    private Position selectedPosition;
    private Card selectedCardHand;

    public Game( Player player1, Player player2) {
        setPlayer1(player1);
        setPlayer2(player2);
        setPlayersLp();
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayersLp() {
        player1.setLP(8000);
        player2.setLP(8000);
    }

    public void draw() {
        int mainDeckSize = turnOfPlayer.getActiveDeck().getMainDeck().size();
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        turnOfPlayer.addCardToHand(turnOfPlayer.getActiveDeck().getMainDeck().get(index));
    }

    public void surrender() {
        turnOfPlayer.setLP(0);
    }

    public void showBoard() {

    }

    public void activateEffect() {

    }

    public void selectPosition(Position position) {
        selectedPosition = position ;
    }

    public void selectedPositionNulling() {
        selectedPosition = null;
    }

    public void selectCardHand(Card card){
        selectedCardHand = card;
    }

    public void selectedCardHandNulling(){
        selectedCardHand = null;
    }

    private int firstEmptyIndex(ArrayList<Position> array){
        int n = 0;
        int i = 0;
        while( n == 0 ){
            if (array.get(i).getStatus().equals(StatusOfPosition.EMPTY)){
                n = 1;
            }
            else{
                i++;
            }
        }
        return i;
    }

    public void summon() {
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
        turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
        turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
    }

    public void setMonsterCardOnBoard(MonsterCard monsterCard) {

    }

    public void changeMonsterStatus(MonsterCard monsterCard) {

    }

    public void attackToMonster(MonsterCard monsterCard) {

    }

    public void directAttack(MonsterCard monsterCard) {

    }

    public void sendToGraveyard(Card Card) {

    }

    public void activateSpell(TrapAndSpellCard spell) {

    }

    public void activateTrap(TrapAndSpellCard trap) {

    }

    public void setSpellOnBoard(TrapAndSpellCard spell) {

    }

    public void setTrapOnBoard(TrapAndSpellCard trap) {

    }

    public void activateTrapInOpponentTurn(TrapAndSpellCard trap) {

    }

    public void activateSpellInOpponentTurn(TrapAndSpellCard trap) {

    }

    public void specialSummon(MonsterCard monsterCard) {

    }

    public void ritualSummon(MonsterCard monsterCard) {

    }

    public void flipSummon(MonsterCard monsterCard) {

    }

    public void showGraveyard() {

    }

    public void showCard() {

    }
}

