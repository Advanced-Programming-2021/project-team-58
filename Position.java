public class Position {
    private StatusOfPosition status;
    private Card card;
    private int index;

    public Position(StatusOfPosition status, int index) {
        this.status = status;
        this.index = index;
    }

    public StatusOfPosition getStatus() {
        return status;
    }

    public void setStatus(StatusOfPosition status) {
        this.status = status;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public static Position getPositionByIndex(int index){

    }
}
