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
        setPlayersDeckOnBoard();
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

    public void setPlayersDeckOnBoard(){
        player1.getBoard().setDeck(player1.getActiveDeck());
        player2.getBoard().setDeck(player2.getActiveDeck());
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void draw() {
        int mainDeckSize = turnOfPlayer.getBoard().getDeck().getMainDeck().size();
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        turnOfPlayer.addCardToHand(turnOfPlayer.getBoard().getDeck().getMainDeck().get(index));
        turnOfPlayer.getBoard().getDeck().getMainDeck().remove(index);
    }

    public void surrender() {
        turnOfPlayer.setLP(0);
    }

    private String convertStatusToChar(StatusOfPosition status){
        if(status.equals(StatusOfPosition.EMPTY)){
            return "E";
        }
        else if(status.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)){
            return "OO";
        }
        else if(status.equals(StatusOfPosition.DEFENSIVE_OCCUPIED)){
            return "DO";
        }
        else if(status.equals(StatusOfPosition.DEFENSIVE_HIDDEN)){
            return "DH";
        }
        else if(status.equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN)){
            return "H";
        }
        else if(status.equals(StatusOfPosition.SPELL_OR_TRAP_OCCUPIED)){
            return "O";
        }
        else{
            return "E";
        }
    }

    private String printOpponentMonsterOnBoard(Player player){
        String result = "";
        String character = "";
        for(int i=4 ; i>=0 ; i--){
            character = convertStatusToChar(player.getBoard().getMonsterCards().get(i).getStatus());
            if(character.length() == 2){
                result = result + character + "   ";
            }
            else{
                result = result + character + "    ";
            }
        }
        return result;
    }

    private String printOpponentSpellCardsOnBoard(Player player){
        String result = "";
        String character = "";
        for(int i=4 ; i>=0 ; i--){
            character = convertStatusToChar(player.getBoard().getSpellCard().get(i).getStatus());
            result = result + character + "    ";
        }
        return result;
    }

    private String printMonsterCardOnBoard(Player player){
        String result = "";
        String character = "";
        for(int i=0 ; i<5 ; i++){
            character = convertStatusToChar(player.getBoard().getMonsterCards().get(i).getStatus());
            if(character.length() == 2){
                result = result + character + "   ";
            }
            else{
                result = result + character + "    ";
            }
        }
        return result;
    }

    private String printSpellCardsOnBoard(Player player){
        String result = "";
        String character = "";
        for(int i=0 ; i<5 ; i++){
            character = convertStatusToChar(player.getBoard().getSpellCard().get(i).getStatus());
            result = result + character + "    ";
        }
        return result;
    }

    private String printCardsOnBoard(Player player){
        int handCardNumbers = player.getHand().size();
        String result ="";
        for(int i=0 ; i<handCardNumbers ; i++){
            result = result + "c    ";
        }
        return result;
    }
//field zone hasn't added in this
    public void showBoard() {
        System.out.println(getOpposition().getNickname() + " : " + getOpposition().getLP());
        System.out.println("    " + printCardsOnBoard(getOpposition()));
        System.out.println(getOpposition().getBoard().getDeck().getDeckSize());
        System.out.println("    " + printOpponentSpellCardsOnBoard(getOpposition()));
        System.out.println("    " + printOpponentMonsterOnBoard(getOpposition()));
        System.out.println(getOpposition().getBoard().getGraveYard().size());
        System.out.println();
        System.out.println("--------------------------");
        System.out.println();
        System.out.println(turnOfPlayer.getBoard().getGraveYard().size());
        System.out.println("    " + printMonsterCardOnBoard(turnOfPlayer));
        System.out.println("    " + printSpellCardsOnBoard(turnOfPlayer));
        System.out.println(printCardsOnBoard(turnOfPlayer));
        System.out.println(turnOfPlayer.getNickname() + " : " + turnOfPlayer.getLP());
    }

//which player: =opponent if opponents board , =player if ours board
//whichField: =monsterCards if monsters , =trapCards if trap and spell
    public void selectPosition(int index , String whichField , String whichPlayer, Position position) {
        if(whichField.equals("monsterCards")){
            if(whichPlayer.equals("opponent")){
                selectedPosition = getOpposition().getBoard().getMonsterCards().get(convertIndex(index));
            }
            else if(whichPlayer.equals("player")){
                selectedPosition = turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(index));
            }
        }
        else if(whichField.equals("trapCards")){
            if(whichPlayer.equals("opponent")){
                selectedPosition = getOpposition().getBoard().getSpellCard().get(convertIndex(index));
            }
            else if(whichPlayer.equals("player")){
                selectedPosition = turnOfPlayer.getBoard().getSpellCard().get(convertIndex(index));
            }
        }
    }

    public void selectedPositionNulling() {
        selectedPosition = null;
    }

    public void selectCardHand(int index){
        selectedCardHand = turnOfPlayer.getHand().get(index-1);
    }

    public void selectedCardHandNulling(){
        selectedCardHand = null;
    }

    private int firstEmptyIndex(ArrayList<Position> array){
        int n = 0;
        int i = 1;
        while( n == 0 ){
            if (array.get(convertIndex(i)).getStatus().equals(StatusOfPosition.EMPTY)){
                n = 1;
            }
            else{
                i++;
            }
        }
        return convertIndex(i);
    }

//changed firstEmptyIndex: maybe it has error!
    public void summon() {
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
        turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
        turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
    }

    public void setMonsterCardOnBoard() {
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

//1.use sendToGraveyard
//2.generate new methods to make it smaller
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

    private void sendToGraveyard(Card card , Player player) {
        player.getBoard().addToGraveyard(card);
    }

    public void setSpellOnBoard() {
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getSpellCard());
        turnOfPlayer.getBoard().getSpellCard().get(i).setStatus(StatusOfPosition.SPELL_OR_TRAP_HIDDEN);
        turnOfPlayer.getBoard().getSpellCard().get(i).setCard(selectedCardHand);
    }

    public void setTrapOnBoard(TrapAndSpellCard trap) {
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getSpellCard());
        turnOfPlayer.getBoard().getSpellCard().get(i).setStatus(StatusOfPosition.SPELL_OR_TRAP_HIDDEN);
        turnOfPlayer.getBoard().getSpellCard().get(i).setCard(selectedCardHand);
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

    public void activateSpell(TrapAndSpellCard spell) {

    }

    public void activateTrap(TrapAndSpellCard trap) {

    }

    public void activateEffect() {

    }

    public void showCard() {
        if(selectedPosition.equals(null)){
            selectedCardHand.showCard();
        }
        else if(selectedCardHand.equals(null)){
            selectedPosition.getCard().showCard();
        }
    }
}