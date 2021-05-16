
import jdk.internal.util.xml.impl.Input;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private Player player;
    private Player player2;
    private Phase phase;
    private Player turnOfPlayer;
    private Position selectedPosition = null;
    private Card selectedCardHand;
    private boolean isAnyCardSummoned;
    static Scanner scanner = new Scanner(System.in);

    public Game(Player player1, Player player2) {
        setPlayer1(player1);
        setPlayer2(player2);
        setPlayersLp();
        setPlayersDeckOnBoard();
        turnOfPlayer = player1;
        drawAtFirstTurn(player1);
        drawAtFirstTurn(player2);
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public Player getTurnOfPlayer() {
        return turnOfPlayer;
    }

    public void endPhase() {
        System.out.println("its " + getOpposition().getNickname() + "’s turn");
        changeTurnOfPlayer();
    }

    public void mainPhase() {
        System.out.println("phase: main phase");
        setPhase(Phase.MAIN);
        String input;
        isAnyCardSummoned = false;
        while (!(input = scanner.nextLine()).equals("next phase")) {
            Matcher matchChangeStatus = getCommandMatcher(input, "^set position (attack|defense)$");
            Matcher matchSelect = getCommandMatcher(input, "^select (hand|monster|spell) (opponent )*([0-9]+)$");

            if (input.equals("summon")) {
                if (isAnyCardSummoned) {
                    isAnyCardSummoned = summon();
                    isAnyCardSummoned = true;
                } else {
                    isAnyCardSummoned = summon();
                }
                selectedCardHandNulling();
                selectedPositionNulling();
            } else if (input.equals("set")) {
                set();
            } else if (matchChangeStatus.find()) {
                changeMonsterStatus(matchChangeStatus.group(1));
                selectedPositionNulling();
                selectedCardHandNulling();
            } else if (input.equals("flip-summon")) {
                flipSummon();
                selectedCardHandNulling();
                selectedPositionNulling();
            } else if (matchSelect.find()) {
                select(matchSelect);
            } else if (input.equals("select -d")) {
                if ((selectedPosition == null) && (selectedCardHand == null)) {
                    selectedPositionNulling();
                    selectedCardHandNulling();
                }
            } else if (input.equals("card show selected")) {
                showCard();
            } else if (input.equals("show graveyard")) {
                showGraveyard();
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void select(Matcher matcher) {
        int number = Integer.parseInt(matcher.group(2));

        if ((matcher.group(1).equals("monster")) && (matcher.group(2).equals(""))) {
            if ((number >= 1) && (number <= 5)) {
                if (turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(number)).getCard().equals(null)) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("spell")) && (matcher.group(2).equals(""))) {
            if ((number >= 1) && (number <= 5)) {
                if (turnOfPlayer.getBoard().getTrapAndSpellCard().get(convertIndex(number)).getCard().equals(null)) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = turnOfPlayer.getBoard().getTrapAndSpellCard().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("hand")) && (matcher.group(2).equals(""))) {
            if ((number >= 1) && (number <= turnOfPlayer.getHand().size())) {
                selectedCardHand = turnOfPlayer.getHand().get(number);
                System.out.println();
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("monster")) && (matcher.group(2).equals("opponent"))) {
            if ((number >= 1) && (number <= 5)) {
                if (getOpposition().getBoard().getMonsterCards().get(convertIndex(number)).getCard().equals(null)) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = getOpposition().getBoard().getMonsterCards().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("spell")) && (matcher.group(2).equals("opponent"))) {
            if ((number >= 1) && (number <= 5)) {
                if (getOpposition().getBoard().getTrapAndSpellCard().get(convertIndex(number)).getCard().equals(null)) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = getOpposition().getBoard().getTrapAndSpellCard().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("hand")) && (matcher.group(2).equals("opponent"))) {
            System.out.println("invalid selection");
        } else {
            System.out.println("invalid selection");
        }
    }

    public void set() {
        if (selectedCardHand instanceof TrapAndSpellCard) {
            setTrapSpellOnBoard();
        } else {
            if (isAnyCardSummoned) {
                isAnyCardSummoned = setMonsterCardOnBoard();
                isAnyCardSummoned = true;
            } else {
                isAnyCardSummoned = setMonsterCardOnBoard();
            }
            selectedPosition.setStatusChanged(true);
        }
        selectedCardHandNulling();
        selectedPositionNulling();
    }

    public void drawPhase() {
        setPhase(Phase.DRAW);
        draw();
    }

    public void battlePhase() {
        setPhase(Phase.BATTLE);
    }

    public void standbyPhase() {
        setPhase(Phase.STANDBY);
    }

    public void run() {

        while (player.getLP() != 0 && player2.getLP() != 0) {
            drawPhase();
            standbyPhase();
            setAllPositionsChangeStatus(); //SETS CHANGING IN STATUS TO FALSE IN NEW TURN
            mainPhase();
            battlePhase();
            mainPhase();
            endPhase();

        }
    }

    private void setAllPositionsChangeStatus() {
        for (int i = 0; i < 5; i++) {
            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatusChanged(false);
        }
    }

    public void changeTurnOfPlayer() {
        if (turnOfPlayer.equals(player))
            turnOfPlayer = player2;
        else {
            turnOfPlayer = player;
        }
    }

    public Player getPlayer1() {
        return player;
    }

    public void setPlayer1(Player player1) {
        this.player = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayersLp() {
        player.setLP(8000);
        player2.setLP(8000);
    }

    public void setPlayersDeckOnBoard() {
        player.getBoard().setDeck(player.getActiveDeck());
        player2.getBoard().setDeck(player2.getActiveDeck());
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void drawAtFirstTurn(Player player) {
        for (int i = 0; i < 6; i++) {
            int mainDeckSize = player.getBoard().getDeck().getMainDeck().size();
            Random rand = new Random();
            int index = rand.nextInt(mainDeckSize);
            player.addCardToHand(player.getBoard().getDeck().getMainDeck().get(index));
            String cardName = turnOfPlayer.getBoard().getDeck().getMainDeck().get(index).getCardName();
            System.out.println("new card added to the hand : " + cardName);
            player.getBoard().getDeck().getMainDeck().remove(index);
        }
    }

    public void draw() {
        int mainDeckSize = turnOfPlayer.getBoard().getDeck().getMainDeck().size();
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        turnOfPlayer.addCardToHand(turnOfPlayer.getBoard().getDeck().getMainDeck().get(index));
        String cardName = turnOfPlayer.getBoard().getDeck().getMainDeck().get(index).getCardName();
        System.out.println("new card added to the hand : " + cardName);
        turnOfPlayer.getBoard().getDeck().getMainDeck().remove(index);

    }

    public void surrender() {
        turnOfPlayer.setLP(0);
    }

    private String convertStatusToChar(StatusOfPosition status) {
        if (status.equals(StatusOfPosition.EMPTY)) {
            return "E";
        } else if (status.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            return "OO";
        } else if (status.equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) {
            return "DO";
        } else if (status.equals(StatusOfPosition.DEFENSIVE_HIDDEN)) {
            return "DH";
        } else if (status.equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN)) {
            return "H";
        } else if (status.equals(StatusOfPosition.SPELL_OR_TRAP_OCCUPIED)) {
            return "O";
        } else {
            return "E";
        }
    }

    private String printOpponentMonsterOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 4; i >= 0; i--) {
            character = convertStatusToChar(player.getBoard().getMonsterCards().get(i).getStatus());
            if (character.length() == 2) {
                result = result + character + "   ";
            } else {
                result = result + character + "    ";
            }
        }
        return result;
    }

    private String printOpponentSpellCardsOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 4; i >= 0; i--) {
            character = convertStatusToChar(player.getBoard().getTrapAndSpellCard().get(i).getStatus());
            result = result + character + "    ";
        }
        return result;
    }

    private String printMonsterCardOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 0; i < 5; i++) {
            character = convertStatusToChar(player.getBoard().getMonsterCards().get(i).getStatus());
            if (character.length() == 2) {
                result = result + character + "   ";
            } else {
                result = result + character + "    ";
            }
        }
        return result;
    }

    private String printSpellCardsOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 0; i < 5; i++) {
            character = convertStatusToChar(player.getBoard().getTrapAndSpellCard().get(i).getStatus());
            result = result + character + "    ";
        }
        return result;
    }

    private String printCardsOnBoard(Player player) {
        int handCardNumbers = player.getHand().size();
        String result = "";
        for (int i = 0; i < handCardNumbers; i++) {
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
    public void selectPosition(int index, String whichField, String whichPlayer) {
        if (whichField.equals("monsterCards")) {
            if (whichPlayer.equals("opponent")) {
                selectedPosition = getOpposition().getBoard().getMonsterCards().get(convertIndex(index));
            } else if (whichPlayer.equals("player")) {
                selectedPosition = turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(index));
            }
        } else if (whichField.equals("trapCards")) {
            if (whichPlayer.equals("opponent")) {
                selectedPosition = getOpposition().getBoard().getTrapAndSpellCard().get(convertIndex(index));
            } else if (whichPlayer.equals("player")) {
                selectedPosition = turnOfPlayer.getBoard().getTrapAndSpellCard().get(convertIndex(index));
            }
        }
    }

    public void selectedPositionNulling() {
        selectedPosition = null;
    }

    public void selectCardHand(int index) {
        selectedCardHand = turnOfPlayer.getHand().get(index - 1);
    }

    public void selectedCardHandNulling() {
        selectedCardHand = null;
    }

    private int firstEmptyIndex(ArrayList<Position> array) {
        int n = 0;
        int i = 1;
        while (n == 0) {
            if (array.get(convertIndex(i)).getStatus().equals(StatusOfPosition.EMPTY)) {
                n = 1;
            } else {
                i++;
            }
        }
        return convertIndex(i);
    }

    //changed firstEmptyIndex: maybe it has error!
    public boolean summon() {
        boolean isTributeSucceeds = false;
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            System.out.println("no card is selected yet");
            return false;
        } else if (((selectedCardHand == null) && (selectedPosition != null)) || !(selectedCardHand instanceof MonsterCard)) {
            System.out.println("you can’t summon this card");
            return false;
        } else if (!this.phase.equals(Phase.MAIN)) {
            System.out.println("action not allowed in this phase");
            return false;
        } else if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
            System.out.println("monster card zone is full");
            return false;
        } else if (this.isAnyCardSummoned) {
            System.out.println("you already summoned/set on this turn");
            return true;
        } else {
            if (((MonsterCard) selectedCardHand).getCardLevel() < 5) {
                int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
                turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
                turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
                System.out.println("summoned successfully");
                return true;
            } else {
                if (((MonsterCard) selectedCardHand).getCardLevel() < 7) {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() == 0) {
                        System.out.println("there are not enough cards for tribute");
                        return false;
                    } else {
                        isTributeSucceeds = tribute(1);
                        if (isTributeSucceeds) {
                            int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
                            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
                            turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
                            System.out.println("summoned successfully");
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() < 2) {
                        System.out.println("there are not enough cards for tribute");
                        return false;
                    } else {
                        isTributeSucceeds = tribute(2);
                        if (isTributeSucceeds) {
                            int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
                            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
                            turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
                            System.out.println("summoned successfully");
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }

    private boolean tribute(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            int a = scanner.nextInt();
            int b = convertIndex(a);
            if (turnOfPlayer.getBoard().getMonsterCards().get(b).getStatus().equals(StatusOfPosition.EMPTY)) {
                System.out.println("there no monsters one this address");
                return false;
            } else {
                turnOfPlayer.getBoard().addToGraveyard(turnOfPlayer.getBoard().getMonsterCards().get(b).getCard());
                turnOfPlayer.getBoard().getMonsterCards().get(b).setCard(null);
                turnOfPlayer.getBoard().getMonsterCards().get(b).setStatus(StatusOfPosition.EMPTY);
                return true;
            }
        }
        return false;
    }

    public boolean setMonsterCardOnBoard() {
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            System.out.println("no card is selected yet");
            return false;
        } else if (((selectedCardHand == null) && (selectedPosition != null))) {
            System.out.println("you can’t set this card");
            return false;
        } else if ((selectedCardHand instanceof MonsterCard) && !(this.phase.equals(Phase.MAIN))) {
            System.out.println("you can’t do this action in this phase");
            return false;
        } else if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
            System.out.println("monster card zone is full");
            return false;
        } else if (this.isAnyCardSummoned) {
            System.out.println("you already summoned/set on this turn");
            return true;
        } else {
            int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.DEFENSIVE_HIDDEN);
            turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
            System.out.println("set successfully");
            return true;
        }
    }

    public void changeMonsterStatus(String newStatus) {
        if ((selectedPosition == null) && (selectedCardHand == null)) {
            System.out.println("no card is selected yet");
        } else if ((selectedCardHand != null) || (selectedPosition.getCard() instanceof MonsterCard)) {
            System.out.println("you can’t change this card position");
        } else if (!this.phase.equals(Phase.MAIN)) {
            System.out.println("you can’t do this action in this phase");
        } else if (((!selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) && (newStatus.equals("attack")))
                || ((!selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) && (newStatus.equals("defense")))) {
            System.out.println("this card is already in the wanted position");
        } else if (selectedPosition.getIsStatusChanged()) {
            System.out.println("you already changed this card position in this turn");
        } else {
            if (selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
                selectedPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
            }
            if (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) {
                selectedPosition.setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
            }
            System.out.println("monster card position changed successfully");
            selectedPosition.setStatusChanged(true);
        }
    }

    public static int convertIndex(int index) {
        if (index == 1) {
            return 2;
        } else if (index == 2) {
            return 3;
        } else if (index == 3) {
            return 1;
        } else if (index == 4) {
            return 4;
        } else {
            return 0;
        }
    }

    public Player getOpposition() {
        if (turnOfPlayer.equals(player)) {
            return player2;
        }
        return player;
    }

    public boolean isConditionsUnsuitableForAttack() {
        if (selectedPosition == null) {
            System.out.println("no card is selected yet");
            return true;
        }
        if (!(selectedPosition.getCard() instanceof MonsterCard)) {
            System.out.println("you can’t attack with this card");
            return true;
        }
        if (!phase.equals(Phase.BATTLE)) {
            System.out.println("you can’t do this action in this phase");
            return true;
        }
//      if (the card has already attacked)
//           {System.out.println("this card already attacked");
//           return true;}
        return false;
    }

//    1.use sendToGraveyard
//    done:)
    //2.generate new methods to make it smaller

    public void attackToMonster(int index) {
        if (isConditionsUnsuitableForAttack())
            return;
        Position oppositionCardPosition = getOpposition().getBoard().getMonsterCards().get(index);
        StatusOfPosition statusOfOpposition = oppositionCardPosition.getStatus();
        if (statusOfOpposition.equals(StatusOfPosition.EMPTY))
            System.out.println("there is no card to attack here");
        else {
            int selectedCardAttack = ((MonsterCard) selectedPosition.getCard()).getAttack();
            int oppositionCardAttack = ((MonsterCard) oppositionCardPosition.getCard()).getAttack();
            int oppositionCardDefense = ((MonsterCard) oppositionCardPosition.getCard()).getDefense();

            if (statusOfOpposition.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
                if (selectedCardAttack > oppositionCardAttack) {
                    int damage = selectedCardAttack - oppositionCardAttack;
                    sendToGraveyard(oppositionCardPosition.getCard(), getOpposition());
                    oppositionCardPosition.setCard(null);
                    oppositionCardPosition.setStatus(StatusOfPosition.EMPTY);
                    getOpposition().decreaseLP(damage);
                    System.out.println("your opponent’s monster is destroyed and your opponent receives "
                            + damage + " battle damage");
                } else if (selectedCardAttack < oppositionCardAttack) {
                    int damage = oppositionCardAttack - selectedCardAttack;
                    sendToGraveyard(oppositionCardPosition.getCard(), turnOfPlayer);
                    selectedPosition.setCard(null);
                    selectedPosition.setStatus(StatusOfPosition.EMPTY);
                    turnOfPlayer.decreaseLP(damage);
                    System.out.println("Your monster card is destroyed and you received " + damage +
                            " battle damage");
                } else {
                    sendToGraveyard(oppositionCardPosition.getCard(), getOpposition());
                    sendToGraveyard(selectedPosition.getCard(), turnOfPlayer);
                    selectedPosition.setCard(null);
                    selectedPosition.setStatus(StatusOfPosition.EMPTY);
                    oppositionCardPosition.setCard(null);
                    oppositionCardPosition.setStatus(StatusOfPosition.EMPTY);
                    System.out.println("both you and your opponent monster cards "
                            + "are destroyed and no one receives damage");
                }
            } else if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) {
                if (selectedCardAttack > oppositionCardDefense) {
                    sendToGraveyard(oppositionCardPosition.getCard(), getOpposition());
                    oppositionCardPosition.setCard(null);
                    oppositionCardPosition.setStatus(StatusOfPosition.EMPTY);
                    System.out.println("the defense position monster is destroyed");
                } else if (selectedCardAttack < oppositionCardDefense) {
                    int damage = oppositionCardDefense - selectedCardAttack;
                    turnOfPlayer.decreaseLP(damage);
                    System.out.println("no card is destroyed and you received " + damage + " battle damage");
                } else {
                    System.out.println("no card is destroyed");
                }
            } else if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_HIDDEN)) {
                String cardName = oppositionCardPosition.getCard().getCardName();
                if (selectedCardAttack > oppositionCardDefense) {
                    sendToGraveyard(oppositionCardPosition.getCard(), getOpposition());
                    System.out.println("opponent’s monster card was " + cardName +
                            " and the defense position monster is destroyed");
                    oppositionCardPosition.setCard(null);
                    oppositionCardPosition.setStatus(StatusOfPosition.EMPTY);
                } else if (selectedCardAttack < oppositionCardDefense) {
                    int damage = oppositionCardDefense - selectedCardAttack;
                    turnOfPlayer.decreaseLP(damage);
                    System.out.println("opponent’s monster card was " + cardName +
                            "no card is destroyed and you received " + damage + " battle damage");
                    oppositionCardPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
                } else {
                    System.out.println("opponent’s monster card was " + cardName + "no card is destroyed");
                    oppositionCardPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
                }
            }
        }
    }


    public void directAttack() {
        if (isConditionsUnsuitableForAttack())
            return;
//        if (for any reason we can't attack directly)
//        System.out.println("you can’t attack the opponent directly");
        else {
            int damage = ((MonsterCard) selectedPosition.getCard()).getAttack();
            getOpposition().decreaseLP(damage);
            System.out.println("you opponent receives " + damage + " battle damage");
        }
    }

    private void sendToGraveyard(Card card, Player player) {
        player.getBoard().addToGraveyard(card);
    }

    public void setTrapSpellOnBoard() {
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            System.out.println("no card is selected yet");
        } else if ((selectedCardHand == null) && (selectedPosition != null)) {
            System.out.println("you can’t set this card");
        } else if (!this.phase.equals(Phase.MAIN)) {
            System.out.println("you can’t do this action in this phase");
        } else if (turnOfPlayer.getBoard().isTrapAndSpellZoneFull()) {
            System.out.println("spell card zone is full");
        } else {
            int i = firstEmptyIndex(turnOfPlayer.getBoard().getTrapAndSpellCard());
            turnOfPlayer.getBoard().getTrapAndSpellCard().get(i).setStatus(StatusOfPosition.SPELL_OR_TRAP_HIDDEN);
            turnOfPlayer.getBoard().getTrapAndSpellCard().get(i).setCard(selectedCardHand);
            System.out.println("set successfully");
        }
    }

    public void activateTrapInOpponentTurn(TrapAndSpellCard trap) {

    }

    public void activateSpellInOpponentTurn(TrapAndSpellCard trap) {

    }

    public void specialSummon(MonsterCard monsterCard) {

    }

    public void ritualSummon(MonsterCard monsterCard) {

    }

    public void flipSummon() {
        if ((selectedPosition == null) && (selectedCardHand == null)) {
            System.out.println("no card is selected yet");
        } else if ((selectedCardHand != null) || !(selectedPosition.getCard() instanceof MonsterCard)) {
            System.out.println("you can’t change this card position");
        } else if (!this.phase.equals(Phase.MAIN)) {
            System.out.println("you can’t do this action in this phase");
        } else if ((!selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)) || (selectedPosition.getIsStatusChanged())) {
            System.out.println("you can’t flip summon this card");
        } else {
            selectedPosition.setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
            System.out.println("flip summoned successfully");
        }
    }

    public void showGraveyard() {
        int graveSize = turnOfPlayer.getBoard().getGraveYard().size();
        if (graveSize == 0) {
            System.out.println("graveyard is empty");
        } else {
            for (int i = 0; i < graveSize; i++) {
                String cardName = turnOfPlayer.getBoard().getGraveYard().get(i).getCardName();
                String cardDescription = turnOfPlayer.getBoard().getGraveYard().get(i).getCardDescription();
                int rank = i + 1;
                System.out.println(rank + ". " + cardName + " : " + cardDescription);
            }
        }
    }

    public void activateSpell(TrapAndSpellCard spell) {

    }

    public void activateTrap(TrapAndSpellCard trap) {

    }

    public void activateEffect() {

    }

    private boolean isPositionInOpponentsBoard() {
        if (getOpposition().getBoard().getMonsterCards().contains(selectedPosition)) {
            return true;
        } else if (getOpposition().getBoard().getTrapAndSpellCard().contains(selectedPosition)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPositionHidden() {
        if ((selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)) ||
                (selectedPosition.getStatus().equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN))) {
            return true;
        }
        return false;
    }

    public void showCard() {
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            System.out.println("no card is selected yet");
        } else if ((isPositionInOpponentsBoard()) && (isPositionHidden())) {
            System.out.println("card is not visible");
        } else {
            if (selectedPosition == null) {
                selectedCardHand.showCard();
            } else if (selectedCardHand == null) {
                selectedPosition.getCard().showCard();
            }
        }
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}