import Controller.Game;
import Model.Card;
import Model.Deck;
import Model.MonsterCard;
import Model.Player;
import View.LoginMenu;
import org.junit.jupiter.api.Test;

public class test1 {
    @Test
    public void test1(){
        LoginMenu.addCards();

        Player player1 = new Player("player1" , "****" , "p1");
        Player player2 = new Player("player2" , "****" , "p2");

        Deck deck1 = new Deck("deck1");
        Deck deck2 = new Deck("deck2");

        for (int i = 0; i < 3; i++) {
            deck1.addCardToMainDeck(Card.getCardByName("Battle OX"));
            deck1.addCardToMainDeck(Card.getCardByName("Wattkid"));
            deck1.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck1.addCardToMainDeck(Card.getCardByName("Suijin"));
            deck1.addCardToMainDeck(Card.getCardByName("Dark Hole"));
            deck1.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck1.addCardToMainDeck(Card.getCardByName("Twin Twisters"));

            deck2.addCardToMainDeck(Card.getCardByName("Battle OX"));
            deck2.addCardToMainDeck(Card.getCardByName("Wattkid"));
            deck2.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck2.addCardToMainDeck(Card.getCardByName("Suijin"));
            deck2.addCardToMainDeck(Card.getCardByName("Dark Hole"));
            deck2.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck2.addCardToMainDeck(Card.getCardByName("Twin Twisters"));
        }

        player1.setActiveDeck(deck1);
        player2.setActiveDeck(deck2);

        Game game = new Game(player1 , player2);
        for (int i = 0; i < 5; i++) {
            if(game.getPlayer1().getHand().get(i) instanceof MonsterCard){
                game.setSelectedCardHand(game.getPlayer1().getHand().get(i));
                game.summon();
                break;
            }
        }


    }

}
