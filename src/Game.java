public class Game {
    private Player player1;
    private Player player2;
    private Phase phase;
    private Player turnOfPlayer;
    private Card selectedCard;
    private int round;
<<<<<<< HEAD
public static void run(){

}
=======
    public static void run(){

    }
>>>>>>> df7a2b4 (test game)
    public Game(int round,Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.round=round;
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

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
    public void setPlayersLp(){

    }
    public void draw(Player turnOfPlayer){

    }
    public void surrender(){

    }
    public void showBoard(){

    }
    public void activateEffect(){

    }
    public void selectCard(Card card){

    }
    public void summon(MonsterCard monsterCard){

    }
    public void setMonsterCardOnBoard(MonsterCard monsterCard){

    }
    public void changeMonsterStatus(MonsterCard monsterCard){

    }
    public void attackToMonster(MonsterCard monsterCard){

    }
    public void directAttack(MonsterCard monsterCard){

    }
    public void sendToGraveyard(Card Card){

    }
    public void activateSpell(TrapAndSpellCard spell){

    }
    public void activateTrap(TrapAndSpellCard trap){

    }
    public void setSpellOnBoard(TrapAndSpellCard spell){

    }
    public void setTrapOnBoard(TrapAndSpellCard trap){

    }
    public void selectedCardNulling(Card selectedCard){

    }
    public void activateTrapInOpponentTurn(TrapAndSpellCard trap){

    }
    public void activateSpellInOpponentTurn(TrapAndSpellCard trap){

    }
    public void specialSummon(MonsterCard monsterCard){

    }
    public void ritualSummon(MonsterCard monsterCard){

    }
    public void flipSummon(MonsterCard monsterCard){

    }
    public void showGraveyard(){

    }
    public void showCard(){

    }
<<<<<<< HEAD
}
=======
}
>>>>>>> df7a2b4 (test game)
