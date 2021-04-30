import javafx.geometry.Pos;

import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    private Phase phase;
    private Player turnOfPlayer;
    private Position selectedPosition;
    private int round;

    //dhgjdd

    public Game(int round , Player player1 , Player player2){
        this.setRound(round);
        this.setPlayer1(player1);
        this.setPlayer2(player2);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setPlayersLP(){
        player1.setLP(8000);
        player2.setLP(8000);
    }

    public void setTurnOfPlayer(Player turnOfPlayer) {
        this.turnOfPlayer = turnOfPlayer;
    }

    public void draw(Player turnOfPlayer){
        Random rand = new Random();
        int randIndex = rand.nextInt(turnOfPlayer.getActiveDeck().getMainDeck().size())
        turnOfPlayer.addCardToHand( turnOfPlayer.getActiveDeck().getMainDeck().get( randIndex ) );
    }

    public void surrender(){
        turnOfPlayer.setLP(0);
    }

    public void showBoard(){

    }

    public void activateEffect(){

    }

    private int indexChange(int index){
        if(index == 1){
            return 2;
        }
        if(index == 2){
            return 3;
        }
        if(index == 3){
            return 1;
        }
        if(index == 4){
            return 4;
        }
        if(index == 5){
            return 0;
        }
    }

    private int indexChangeOpponent(int index){
        if(index == 1){
            return 2;
        }
        if(index == 2){
            return 1;
        }
        if(index == 3){
            return 3;
        }
        if(index == 4){
            return 0;
        }
        if(index == 5){
            return 4;
        }
    }

    public void selectOpponentPosition(int index , String monsterOrSpell){
        if (turnOfPlayer.equals(player1)){
            if(monsterOrSpell.equals("monster")) {
                selectedPosition = player2.getBoard().getMonsterCards().get(indexChangeOpponent(index));
            }
            if(monsterOrSpell.equals("spell")){
                selectedPosition = player2.getBoard().getSpellCards().get(indexChangeOpponent(index));
            }
        }
        else{
            if(monsterOrSpell.equals("monster")) {
                selectedPosition = player1.getBoard().getMonsterCards().get(indexChangeOpponent(index));
            }
            if(monsterOrSpell.equals("spell")){
                selectedPosition = player1.getBoard().getSpellCards().get(indexChangeOpponent(index));
            }
        }
    }

    public void selectPosition(int index , String monsterOrSpell){
        if (turnOfPlayer.equals(player1)){
            if(monsterOrSpell.equals("monster")) {
                selectedPosition = player1.getBoard().getMonsterCards().get(indexChange(index));
            }
            if(monsterOrSpell.equals("spell")){
                selectedPosition = player1.getBoard().getSpellCards().get(indexChange(index));
            }
        }
        else{
            if(monsterOrSpell.equals("monster")) {
                selectedPosition = player2.getBoard().getMonsterCards().get(indexChange(index));
            }
            if(monsterOrSpell.equals("spell")){
                selectedPosition = player2.getBoard().getSpellCards().get(indexChange(index));
            }
        }
    }

    public void summon(){

    }

    public void setMonsterCardOnBoard(){

    }

    public void changeMonsterCardStatus(Position position , StatusOfPosition newStatus){
        position.setStatus(newStatus);
    }

    public void attackToMonster( , int index){

    }

    public void directAttack(){
        if(selectedPosition.getCard() instanceof MonsterCard) {
            ((MonsterCard) selectedPosition.getCard()).getAttack();
        }
        else{

        }

    }

    public void sendToGraveyard(){

    }

    public void setSpellOnBoard(TrapAndSpellCard spell){

    }

    public void setTrapOnBoard(TrapAndSpellCard trap){

    }

    public void setSelectedCardNulling(){
        this.selectedCard = null ;
    }
    public void activateSpell(){

    }

    public void activateTrap(){

    }

    public void activateTrapInOpponentTurn(){

    }

    public void activateSpellInOpponentTurn(){

    }

    public void specialSummon(MonsterCard monster){

    }

    public void ritualSummon(){

    }

    public void flipSummon(){

    }

    public void showGraveyard(){

    }

}
















