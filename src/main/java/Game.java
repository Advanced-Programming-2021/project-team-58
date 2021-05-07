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

    public Position getSelectedPosition() {
        return selectedPosition;
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
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
        turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.DEFENSIVE_HIDDEN);
        turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
    }

    public void changeMonsterStatus() {
        if(selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)){
            selectedPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
        }
        if(selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_OCCUPIED)){
            selectedPosition.setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
        }
    }

    private int convertIndex(int index){
        if(index == 1){
            return 2;
        }
        else if(index == 2){
            return 3;
        }
        else if(index == 3){
            return 1;
        }
        else if(index == 4){
            return 4;
        }
        else{
            return 0;
        }
    }

    private Player getOpposition(){
        if(turnOfPlayer.equals(player1)){
            return player2;
        }
        return player1;
    }

    public void attackToMonster(int index) {
        int selectedCardAttack = ((MonsterCard)selectedPosition.getCard()).getAttack();
        int oppositionCardAttack = ((MonsterCard)getOpposition().getBoard().getMonsterCards().get(index).getCard()).getAttack();
        int oppositionCardDefense = ((MonsterCard)getOpposition().getBoard().getMonsterCards().get(index).getCard()).getDefense();
        StatusOfPosition statusOfOpposition = getOpposition().getBoard().getMonsterCards().get(index).getStatus();

        if(statusOfOpposition.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            if (selectedCardAttack > oppositionCardAttack) {
                int damage =selectedCardAttack - oppositionCardAttack;
                getOpposition().getBoard().addToGraveyard(getOpposition().getBoard().getMonsterCards().get(index).getCard());
                getOpposition().getBoard().getMonsterCards().get(index).setCard(null);
                getOpposition().getBoard().getMonsterCards().get(index).setStatus(StatusOfPosition.EMPTY);
                getOpposition().decreaseLP(damage);
                System.out.println("your opponent’s monster is destroyed and your opponent receives "
                        + damage +" battle damage");
            }
            else if(selectedCardAttack < oppositionCardAttack){
                int damage = oppositionCardAttack - selectedCardAttack;
                turnOfPlayer.getBoard().addToGraveyard(selectedPosition.getCard());
                selectedPosition.setCard(null);
                selectedPosition.setStatus(StatusOfPosition.EMPTY);
                turnOfPlayer.decreaseLP(damage);
                System.out.println("Your monster card is destroyed and you received " + damage + " battle damage");
            }
            else{
                getOpposition().getBoard().addToGraveyard(getOpposition().getBoard().getMonsterCards().get(index).getCard());
                turnOfPlayer.getBoard().addToGraveyard(selectedPosition.getCard());
                selectedPosition.setCard(null);
                selectedPosition.setStatus(StatusOfPosition.EMPTY);
                getOpposition().getBoard().getMonsterCards().get(index).setCard(null);
                getOpposition().getBoard().getMonsterCards().get(index).setStatus(StatusOfPosition.EMPTY);
                System.out.println("both you and your opponent monster cards are destroyed and no one receives damage");
            }
        }

        if(statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED)){
            if(selectedCardAttack > oppositionCardDefense){
                getOpposition().getBoard().addToGraveyard(getOpposition().getBoard().getMonsterCards().get(index).getCard());
                getOpposition().getBoard().getMonsterCards().get(index).setCard(null);
                getOpposition().getBoard().getMonsterCards().get(index).setStatus(StatusOfPosition.EMPTY);
                System.out.println("the defense position monster is destroyed");
            }
            else if (selectedCardAttack < oppositionCardDefense){
                int damage = oppositionCardDefense - selectedCardAttack ;
                turnOfPlayer.decreaseLP(damage);
                System.out.println("no card is destroyed and you received " + damage + " battle damage");
            }
            else{
                System.out.println("no card is destroyed");
            }
        }

        if(statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_HIDDEN)){
            String cardName = getOpposition().getBoard().getMonsterCards().get(index).getCard().getCardName();
            if (selectedCardAttack > oppositionCardDefense){
                getOpposition().getBoard().addToGraveyard(getOpposition().getBoard().getMonsterCards().get(index).getCard());
                System.out.println("opponent’s monster card was " + cardName + " and the defense position monster is destroyed");
                getOpposition().getBoard().getMonsterCards().get(index).setCard(null);
                getOpposition().getBoard().getMonsterCards().get(index).setStatus(StatusOfPosition.EMPTY);
            }
            else if (selectedCardAttack < oppositionCardDefense){
                int damage = oppositionCardDefense - selectedCardAttack ;
                turnOfPlayer.decreaseLP(damage);
                System.out.println("opponent’s monster card was " + cardName + "no card is destroyed and you received " + damage + " battle damage");
                getOpposition().getBoard().getMonsterCards().get(index).setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
            }
            else{
                System.out.println("opponent’s monster card was " + cardName + "no card is destroyed");
                getOpposition().getBoard().getMonsterCards().get(index).setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
            }
        }

    }

    public void directAttack() {
        int damage = ((MonsterCard)selectedPosition.getCard()).getAttack();
        getOpposition().decreaseLP(damage);
        System.out.println("you opponent receives " + damage + " battale damage");
    }

    private void sendToGraveyard(Card Card) {

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

