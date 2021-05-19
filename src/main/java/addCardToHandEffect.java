import java.util.Random;

public class addCardToHandEffect extends Effect{
//Effect of supply squad
    public void activate(Game game){
        int mainDeckSize = game.getTurnOfPlayer().getBoard().getDeck().getMainDeck().size();
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        game.getTurnOfPlayer().getHand().add(game.getTurnOfPlayer().getBoard().getDeck().getMainDeck().get(index));
        game.getTurnOfPlayer().getBoard().getDeck().getMainDeck().remove(index);
    }

    @Override
    public boolean isSuitableForActivate(Game game) {
        return false;
    }
}
