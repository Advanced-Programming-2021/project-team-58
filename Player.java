import java.util.ArrayList;

public class Player {
    private String username;
    private String password;
    private String nickname;
    private DeckMenu score;
    private int rank;
    private int LP;
    private int money;
    private ArrayList<Deck> decks;
    private ArrayList<Card> hand;
    private static ArrayList<Player> allPlayers;
    private Deck activeDeck;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLP() {
        return LP;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }
    public void increaseScore(int score){

    }
    public void decreaseScore(int score){

    }
    public void increaseMoney(int money){

    }
    public void decreaseMoney(int money){

    }
    public Player getPlayerByUsername(String username){

    }
    public void increaseLP(int LP){

    }
    public void decreaseLP(int LP){

    }
    public void addCardToHand(Card card){

    }
}
