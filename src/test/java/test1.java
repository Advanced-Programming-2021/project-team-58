import Controller.Game;
import Model.*;
import View.LoginMenu;
import org.junit.jupiter.api.*;

public class test1 {
    @Test
    void test1() {
        LoginMenu.addCards();

        Player player1 = new Player("player1", "****", "p1");
        Player player2 = new Player("player2", "****", "p2");

        Deck deck1 = new Deck("deck1");
        Deck deck2 = new Deck("deck2");

        for (int i = 0; i < 3; i++) {
            deck1.addCardToMainDeck(Card.getCardByName("Battle OX"));
            deck1.addCardToMainDeck(Card.getCardByName("Wattkid"));
            deck1.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck1.addCardToMainDeck(Card.getCardByName("Bitron"));
            deck1.addCardToMainDeck(Card.getCardByName("Dark Hole"));
            deck1.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck1.addCardToMainDeck(Card.getCardByName("Twin Twisters"));

            deck2.addCardToMainDeck(Card.getCardByName("Battle OX"));
            deck2.addCardToMainDeck(Card.getCardByName("Wattkid"));
            deck2.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck2.addCardToMainDeck(Card.getCardByName("Bitron"));
            deck2.addCardToMainDeck(Card.getCardByName("Dark Hole"));
            deck2.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck2.addCardToMainDeck(Card.getCardByName("Twin Twisters"));
        }

        player1.setActiveDeck(deck1);
        player2.setActiveDeck(deck2);

        Game game = new Game(player1, player2);

        Card card1 = null;
        Card card2 = null;
        Card graveyardCard;

        int attack1 = 0;
        int attack2 = 0;

        game.setPhase(Phase.MAIN);

        for (int i = 0; i < 5; i++) {
            if (game.getPlayer1().getHand().get(i) instanceof MonsterCard) {
                game.setSelectedCardHand(game.getPlayer1().getHand().get(i));
                card1 = game.getPlayer1().getHand().get(i);
                attack1 = ((MonsterCard) game.getSelectedCardHand()).getAttack();
                game.setTurnOfPlayer(player1);
                game.summon();
                break;
            }
        }

        game.setAnyCardSummoned(false);

        for (int i = 0; i < 5; i++) {
            if (game.getPlayer2().getHand().get(i) instanceof MonsterCard) {
                game.setSelectedCardHand(game.getPlayer2().getHand().get(i));
                card2 = game.getPlayer2().getHand().get(i);
                attack2 = ((MonsterCard) game.getSelectedCardHand()).getAttack();
                game.setTurnOfPlayer(player2);
                game.summon();
                break;
            }
        }

        game.setPhase(Phase.BATTLE);

        if (attack1 > attack2) {
            game.setSelectedPosition(game.getPlayer1().getBoard().getMonsterCards().get(2));
            game.setTurnOfPlayer(player1);
            game.attackToMonster(2);
            graveyardCard = game.getPlayer2().getBoard().getGraveYard().get(0);
            Assertions.assertEquals(card2, graveyardCard);
        } else {
            game.setSelectedPosition(game.getPlayer2().getBoard().getMonsterCards().get(2));
            game.setTurnOfPlayer(player2);
            game.attackToMonster(2);
            graveyardCard = game.getPlayer1().getBoard().getGraveYard().get(0);
            Assertions.assertEquals(card1, graveyardCard);
        }
    }

    @Test
    void test2() {
        LoginMenu.addCards();

        Player player1 = new Player("player1", "****", "p1");
        Player player2 = new Player("player2", "****", "p2");

        Deck deck1 = new Deck("deck1");
        Deck deck2 = new Deck("deck2");

        for (int i = 0; i < 3; i++) {
            deck1.addCardToMainDeck(Card.getCardByName("Battle OX"));
            deck1.addCardToMainDeck(Card.getCardByName("Wattkid"));
            deck1.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck1.addCardToMainDeck(Card.getCardByName("Bitron"));
            deck1.addCardToMainDeck(Card.getCardByName("Dark Hole"));
            deck1.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck1.addCardToMainDeck(Card.getCardByName("Twin Twisters"));

            deck2.addCardToMainDeck(Card.getCardByName("Battle OX"));
            deck2.addCardToMainDeck(Card.getCardByName("Wattkid"));
            deck2.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck2.addCardToMainDeck(Card.getCardByName("Bitron"));
            deck2.addCardToMainDeck(Card.getCardByName("Dark Hole"));
            deck2.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck2.addCardToMainDeck(Card.getCardByName("Twin Twisters"));
        }

        player1.setActiveDeck(deck1);
        player2.setActiveDeck(deck2);

        Game game = new Game(player1, player2);

        Position position1;
        Position position2;

        int attack1 = 0;
        int attack2 = 0;

        game.setPhase(Phase.MAIN);

        for (int i = 0; i < 5; i++) {
            if (game.getPlayer1().getHand().get(i) instanceof MonsterCard) {
                game.setSelectedCardHand(game.getPlayer1().getHand().get(i));
                attack1 = ((MonsterCard) game.getSelectedCardHand()).getAttack();
                game.setTurnOfPlayer(player1);
                game.summon();
                break;
            }
        }

        game.setAnyCardSummoned(false);

        for (int i = 0; i < 5; i++) {
            if (game.getPlayer2().getHand().get(i) instanceof MonsterCard) {
                game.setSelectedCardHand(game.getPlayer2().getHand().get(i));
                attack2 = ((MonsterCard) game.getSelectedCardHand()).getAttack();
                game.setTurnOfPlayer(player2);
                game.summon();
                break;
            }
        }
        position1 = game.getPlayer1().getBoard().getMonsterCards().get(2);
        position2 = game.getPlayer2().getBoard().getMonsterCards().get(2);

        game.setPhase(Phase.BATTLE);

        if (attack1 > attack2) {
            game.setSelectedPosition(position1);
            game.setTurnOfPlayer(player1);
            game.attackToMonster(2);
            Assertions.assertEquals(position2.getStatus(), StatusOfPosition.EMPTY);
            Assertions.assertNull(position2.getCard());
        } else {
            game.setSelectedPosition(position2);
            game.setTurnOfPlayer(player2);
            game.attackToMonster(2);
            Assertions.assertEquals(position1.getStatus(), StatusOfPosition.EMPTY);
            Assertions.assertNull(position1.getCard());
        }

    }

}