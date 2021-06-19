package Controller;

import Model.*;
import View.*;

import java.util.*;
import java.util.regex.*;

public class Game {
    private static int nextPhaseCheck;

    private Player player;
    private Player player2;

    private Phase phase;
    private Player turnOfPlayer;
    private Position selectedPosition = null;
    private Card selectedCardHand;
    private boolean isAnyCardSummoned;
    static Scanner scanner = new Scanner(System.in);
    List<Position> attackedCards = new ArrayList<Position>();

    public Game(Player player1, Player player2) {
        setPlayer1(player1);
        setPlayer2(player2);
        setPlayersLp();
        setPlayersDeckOnBoard();
        turnOfPlayer = player1;
        drawAtFirstTurn(player1);
        drawAtFirstTurn(player2);
    }

    public void run() {
        try {
            while (player.getLP() > 0 && player2.getLP() > 0) {
                nextPhaseCheck = 0;
                isAnyCardSummoned = false;
                setAllPositionsChangeStatus(); //SETS CHANGING IN STATUS TO FALSE IN NEW TURN
                System.out.println("It's " + turnOfPlayer.getNickname() + "’s turn");
                drawPhase();
            }
        } catch (Exception e) {
            System.out.println("I have caught and exception");
            e.printStackTrace();
        }
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
        changeTurnOfPlayer();
    }

    public void mainPhase() {
        System.out.println("phase: main phase");
        setPhase(Phase.MAIN);
        String input;
        while (!((input = scanner.nextLine()).equals("next phase"))) {
            Matcher matchChangeStatus = getCommandMatcher(input, "^set position (attack|defense)$");
            Matcher matchSelect = getCommandMatcher(input, "^select --(hand|monster|spell) (--opponent )*([0-9]+)$");
            Matcher matchSelectField = getCommandMatcher(input, "^select --field (--opponent)?$");

            if (input.equals("summon")) {
                summon();
            } else if (input.equals("set")) {
                set();
            } else if (matchChangeStatus.find()) {
                changeMonsterStatus(matchChangeStatus.group(1));
            } else if (input.equals("flip-summon")) {
                flipSummon();
                selectedCardHandNulling();
                selectedPositionNulling();
            } else if (matchSelect.find()) {
                select(matchSelect);
            } else if (matchSelectField.find()) {

            } else if (input.equals("select -d")) {
                if ((selectedPosition == null) && (selectedCardHand == null)) {
                    System.out.println("no card is selected yet!");
                } else {
                    selectedPositionNulling();
                    selectedCardHandNulling();
                }

            } else if (input.equals("card show selected")) {
                showCard();
            } else if (input.equals("show graveyard")) {
                showGraveyard();
            } else if (input.equals("show current-phase")) {
                System.out.println(phase);
            } else {
                System.out.println("invalid command");
            }
            showBoard();
            if (isAnyoneWin()) {
                return;
            }
        }
        selectedPositionNulling();
        selectedCardHandNulling();
        if (nextPhaseCheck == 0) {
            nextPhaseCheck = 1;
            battlePhase();
        } else {
            endPhase();
        }
    }

    public void select(Matcher matcher) {
        int number = Integer.parseInt(matcher.group(3));

        if ((matcher.group(1).equals("monster")) && (matcher.group(2) == null)) {
            if ((number >= 1) && (number <= 5)) {
                if (turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(number)).getCard() == null) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("spell")) && (matcher.group(2) == null)) {
            if ((number >= 1) && (number <= 5)) {
                if (turnOfPlayer.getBoard().getTrapAndSpellCard().get(convertIndex(number)).getCard() == null) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = turnOfPlayer.getBoard().getTrapAndSpellCard().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("hand")) && (matcher.group(2) == null)) {
            if ((number >= 1) && (number <= turnOfPlayer.getHand().size())) {
                selectedCardHand = turnOfPlayer.getHand().get(number - 1);
                System.out.println("card selected");
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("monster")) && (matcher.group(2).equals("--opponent "))) {
            if ((number >= 1) && (number <= 5)) {
                if (getOpposition().getBoard().getMonsterCards().get(convertIndex(number)).getCard() == null) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = getOpposition().getBoard().getMonsterCards().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("spell")) && (matcher.group(2).equals("--opponent "))) {
            if ((number >= 1) && (number <= 5)) {
                if (getOpposition().getBoard().getTrapAndSpellCard().get(convertIndex(number)).getCard() == null) {
                    System.out.println("no card found in the given position");
                } else {
                    selectedPosition = getOpposition().getBoard().getTrapAndSpellCard().get(convertIndex(number));
                    System.out.println("card selected");
                }
            } else {
                System.out.println("invalid selection");
            }
        } else if ((matcher.group(1).equals("hand")) && (matcher.group(2).equals("--opponent "))) {
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
        }
    }

    public void drawPhase() {
        setPhase(Phase.DRAW);
        draw();
        if (isAnyoneWin()) return;
        mainPhase();
    }

    public void battlePhase() {
        System.out.println("phase : battle phase");
        setPhase(Phase.BATTLE);
        String input;
        while (!(input = scanner.nextLine()).equals("next phase")) {
            Matcher matchSelect = getCommandMatcher(input, "^select --(hand|monster|spell) (--opponent )*([0-9]+)$");

            if (input.trim().matches("^(?i)(attack direct)$"))
                directAttack();
            else if (input.trim().matches("^(?i)(attack (.+))$"))
                attackToMonster(getCommandMatcher(input, "^(?i)(attack (.+))$"));
            else if (matchSelect.find())
                select(matchSelect);
            else if (input.equals("show current-phase"))
                System.out.println(phase);
            else System.out.println("invalid command for this phase");

            showBoard();
            if (isAnyoneWin()) {
                return;
            }

        }
//        In the end of this phase we call this method:
        clearAttackedCardsArrayList();
        mainPhase();
    }

    public void standbyPhase() {
        setPhase(Phase.STANDBY);
        mainPhase();
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
        player.setLP(1000);
        player2.setLP(1000);
    }

    public void setPlayersDeckOnBoard() {
        player.getBoard().setMainDeck((ArrayList<Card>) (player.getActiveDeck().getMainDeck().clone()));
        player2.getBoard().setMainDeck((ArrayList<Card>) (player2.getActiveDeck().getMainDeck().clone()));
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void drawAtFirstTurn(Player player) {
        for (int i = 0; i < 6; i++) {
            int mainDeckSize = player.getBoard().getMainDeck().size();

            if (mainDeckSize == 0) {
                System.out.println("You don't have any other card to draw and you lost!!!");
                return;
            }
            Random rand = new Random();
            int index = rand.nextInt(mainDeckSize);
            player.addCardToHand(player.getBoard().getMainDeck().get(index));
            String cardName = player.getBoard().getMainDeck().get(index).getCardName();
            System.out.println("new card : " + cardName + " added to the hand of: " + player.getNickname());
            player.getBoard().getMainDeck().remove(index);
        }
    }


    public void draw() {
        int mainDeckSize = turnOfPlayer.getBoard().getMainDeck().size();
        //Adding the following in order to avoid Exception for the random method
        if (mainDeckSize == 0) {
            System.out.println("You don't have any other card to draw and you lost!!!");
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        turnOfPlayer.addCardToHand(turnOfPlayer.getBoard().getMainDeck().get(index));
        String cardName = turnOfPlayer.getBoard().getMainDeck().get(index).getCardName();
        System.out.println("new card :" + cardName + " added to the hand of: " + turnOfPlayer.getNickname());
        turnOfPlayer.getBoard().getMainDeck().remove(index);

    }

    public void surrender() {
        turnOfPlayer.setLP(0);

    }

    public static Player winner;
    public static Player loser;

    public void endGame() {
        System.out.println(winner + " won the game and the score is: " + (winner.getScore() - loser.getScore()));
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
        System.out.println("     " + printCardsOnBoard(getOpposition()));
        System.out.println(getOpposition().getBoard().getMainDeck().size());
        System.out.println("     " + printOpponentSpellCardsOnBoard(getOpposition()));
        System.out.println("     " + printOpponentMonsterOnBoard(getOpposition()));
        System.out.println(getOpposition().getBoard().getGraveYard().size());
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println("                             " + turnOfPlayer.getBoard().getGraveYard().size());
        System.out.println("     " + printMonsterCardOnBoard(turnOfPlayer));
        System.out.println("     " + printSpellCardsOnBoard(turnOfPlayer));
        System.out.println("                             " + turnOfPlayer.getBoard().getMainDeck().size());
        System.out.println(printCardsOnBoard(turnOfPlayer));
        System.out.println(turnOfPlayer.getNickname() + " : " + turnOfPlayer.getLP());
    }

    public void selectedPositionNulling() {
        selectedPosition = null;
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

    public void summon() {
        if (isAnyCardSummoned) {
            isAnyCardSummoned = summonMonsterOnBoard();
            isAnyCardSummoned = true;
        } else {
            isAnyCardSummoned = summonMonsterOnBoard();
        }
    }

    //changed firstEmptyIndex: maybe it has error!
    public boolean summonMonsterOnBoard() {
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
            if (((MonsterCard) selectedCardHand).getCardLevel() < 5)
                return lastStepForSummon();
            else {
                if (((MonsterCard) selectedCardHand).getCardLevel() < 7) {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() == 0) {
                        System.out.println("there are not enough cards for tribute");
                        return false;
                    } else {
                        isTributeSucceeds = tribute(1);
                        if (isTributeSucceeds) return lastStepForSummon();
                        else return false;
                    }
                } else {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() < 2) {
                        System.out.println("there are not enough cards for tribute");
                        return false;
                    } else {
                        isTributeSucceeds = tribute(2);
                        if (isTributeSucceeds) return lastStepForSummon();
                        else return false;
                    }
                }
            }
        }
    }

    private boolean lastStepForSummon() {
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
        turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
        turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
        turnOfPlayer.getHand().remove(selectedCardHand);
        System.out.println("summoned successfully");

        selectedCardHandNulling();
        selectedPositionNulling();
        return true;
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
        } else if (selectedCardHand == null) {
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
            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatusChanged(true);
            turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
            turnOfPlayer.getHand().remove(selectedCardHand);
            selectedCardHandNulling();
            System.out.println("set successfully");

            return true;
        }
    }

    public void changeMonsterStatus(String newStatus) {
        if (selectedPosition == null) {
            System.out.println("no card is selected on the board yet");
        } else if (!(selectedPosition.getCard() instanceof MonsterCard)) {
            System.out.println("you can’t change this card position");
        } else if (!this.phase.equals(Phase.MAIN)) {
            System.out.println("you can’t do this action in this phase");
        } else if (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)) {
            System.out.println("you can't change the position of cards that are in DH. Use flip-summon");
        } else if (((selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) && (newStatus.equals("attack")))
                || ((selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) && (newStatus.equals("defense")))) {
            System.out.println("this card is already in the wanted position");
        } else if (selectedPosition.getIsStatusChanged()) {
            System.out.println("you already changed this card position in this turn");
        } else {
            if (selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
                System.out.println(selectedPosition.getStatus());
                selectedPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
                System.out.println(selectedPosition.getStatus());
            } else if (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) {
                System.out.println(selectedPosition.getStatus());
                selectedPosition.setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
                System.out.println(selectedPosition.getStatus());
            }
            System.out.println("monster card position changed successfully");
            selectedPosition.setStatusChanged(true);
            selectedPositionNulling();
            selectedCardHandNulling();
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
//    ----------------------------------------------------------------------------------------------------
//    ----------------------------------------BATTLE PHASE------------------------------------------------

    public boolean isConditionsUnsuitableForAttack() {
        if ((selectedPosition == null) && (selectedCardHand == null)) {
            System.out.println("no card is selected yet");
            return true;
        }
        if (selectedPosition == null) {
            System.out.println("selected card should not be in your hand");
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
        if (hasCardAttackedInThisPhase(selectedPosition)) {
            System.out.println("this card already attacked");
            return true;
        }
        if (!selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            System.out.println("selected card is in defensive position!");
            return true;
        }
        return false;
    }

    public boolean hasCardAttackedInThisPhase(Position position) {
        if (attackedCards.contains(position)) return true;
        return false;
    }

    public void clearAttackedCardsArrayList() {
        attackedCards.clear();
    }

    //2.generate new methods to make it smaller

    public void attackToMonster(Matcher matcher) {

        if (matcher.find()) {
            int index;
            try {
                index = convertIndex(Integer.parseInt(matcher.group(2)));
            } catch (Exception e) {
                System.out.println("Please enter an integer");
                return;
            }
            if (isConditionsUnsuitableForAttack())
                return;
            Position oppositionCardPosition = getOpposition().getBoard().getMonsterCards().get(index);
            StatusOfPosition statusOfOpposition = oppositionCardPosition.getStatus();
            if (isOpponentMonsterZoneEmpty())
                System.out.println("your opponent's monster zone is empty and you can attack directly to their LP");
            else if (statusOfOpposition.equals(StatusOfPosition.EMPTY))
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
                                " no card is destroyed and you received " + damage + " battle damage");
                        oppositionCardPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
                    } else {
                        System.out.println("opponent’s monster card was " + cardName + " no card is destroyed");
                        oppositionCardPosition.setStatus(StatusOfPosition.DEFENSIVE_OCCUPIED);
                    }
                }
                attackedCards.add(selectedPosition);
                selectedCardHandNulling();
                selectedPositionNulling();
            }
        }
    }

    public boolean isOpponentMonsterZoneEmpty() {
        for (int i = 0; i < 5; i++) {
            if (!getOpposition().getBoard().getMonsterCards().get(i).getStatus().equals(StatusOfPosition.EMPTY))
                return false;
        }
        return true;
    }


    public void directAttack() {
        if (isConditionsUnsuitableForAttack())
            return;
        if (!isOpponentMonsterZoneEmpty())
            System.out.println("you can’t attack the opponent directly");
//        else if (there are some other reasons that we can't attack and we may find them later)
//            System.out.println("you can't attack the opponent directly");
        else {
            int damage = ((MonsterCard) selectedPosition.getCard()).getAttack();
            getOpposition().decreaseLP(damage);
            System.out.println("you opponent receives " + damage + " battle damage");
            attackedCards.add(selectedPosition);

            selectedPositionNulling();
            selectedCardHandNulling();
        }

    }

    public boolean isAnyoneWin() {
        return (player.getLP() <= 0) || (player2.getLP() <= 0);
    }

//    --------------------------------------------------------------------------------------------------------
//    --------------------------------------------------------------------------------------------------------

    public void sendToGraveyard(Card card, Player player) {
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
            turnOfPlayer.getBoard().getTrapAndSpellCard().get(i).setStatusChanged(true);
            turnOfPlayer.getBoard().getTrapAndSpellCard().get(i).setCard(selectedCardHand);
            turnOfPlayer.getHand().remove(selectedCardHand);
            selectedCardHandNulling();
            System.out.println("set successfully");
        }
    }

    public void activateTrapInOpponentTurn(TrapAndSpellCard trap) {

    }

    public void activateSpellInOpponentTurn(TrapAndSpellCard trap) {

    }

    private boolean isAnyMonsterInArray(ArrayList<Card> array) {
        for (Card card : array) {
            if (card instanceof MonsterCard) {
                return true;
            }
        }
        return false;
    }

    private void specialSummonHelping(ArrayList<Card> array) {
        int n = 0;
        while (n == 0) {
            int number = scanner.nextInt();
            if (number > array.size()) {
                System.out.println("given number is greater than number of cards");
            } else if (!(array.get(number) instanceof MonsterCard)) {
                System.out.println("you can't summon this card");
            } else {
                int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
                turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
                turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(array.get(number));
                array.remove(number);
                System.out.println("special summoned successfully");
            }
        }
    }

    public void specialSummon(String arrayName) {
        if (arrayName.equals("deck")) {
            if (!isAnyMonsterInArray(turnOfPlayer.getBoard().getMainDeck())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(turnOfPlayer.getBoard().getMainDeck());
                }
            }
        } else if (arrayName.equals("hand")) {
            if (!isAnyMonsterInArray(turnOfPlayer.getHand())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(turnOfPlayer.getHand());
                }
            }
        } else if (arrayName.equals("opponents graveyard")) {
            if (!isAnyMonsterInArray(getOpposition().getBoard().getGraveYard())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(getOpposition().getBoard().getGraveYard());
                }
            }
        } else {      //grave
            if (!isAnyMonsterInArray(turnOfPlayer.getBoard().getGraveYard())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(turnOfPlayer.getBoard().getGraveYard());
                }
            }
        }
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