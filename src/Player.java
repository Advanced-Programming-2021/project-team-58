import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> allPlayers;
    private String username;
    private String password;
    private String nickname;
    private Deck activeDeck;
    private int score;
    private int LP;
    private int money;
    private Board board;
//    private int rank;
//    something
    private ArrayList<Deck> decks;  //These are NOT the deck that is being used in the game
    private ArrayList<Card> hand;

    public Player(String username, String password) {
        setUsername(username);
        setPassword(password);
        board = new Board();
        allPlayers.add(this);
    }

    public void setUsername(String username) { this.username = username;}

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setNickname(String nickname) { this.nickname = nickname;}

    public String getNickname() {
        return this.nickname;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public int getLP() {
        return this.LP;
    }

    public void increaseLP(int LP) {
        this.LP += LP;
    }

    public void decreaseLP(int LP) {
        this.LP -= LP;
    }

    public void setActiveDeck(Deck activeDeck) { this.activeDeck = activeDeck;}

    public Deck getActiveDeck() {
        return this.activeDeck;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void decreaseScore(int score) {
        this.score -=score;
    }

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void decreaseMoney(int money) {
        this.money -= money;
    }

    public int getMoney() {
        return this.money;
    }

    public Board getBoard(){
        return this.board;
    }

    public ArrayList<Deck> getDecks() {
        return this.decks;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public static Player getPlayerByUsername(String userName) {
        for (Player player : allPlayers)
            if (player.getUsername().equals(userName))
                return player;
            return null;
    }
    public void addDeckToAllDecks(Deck deck){this.decks.add(deck);}

    public void addCardToHand(Card card) {
        this.hand.add(card);
    }
}
