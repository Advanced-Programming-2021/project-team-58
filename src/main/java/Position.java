
import java.util.ArrayList;

public class Position {
    public static ArrayList<Position> allPositions = new ArrayList<Position>();
    private StatusOfPosition status;
    private Card card;
    private int index;
    private boolean isStatusChanged;

    public Position(StatusOfPosition status, int index) {
        setStatus(status);
        setIndex(index);
        allPositions.add(this);
    }

    public StatusOfPosition getStatus() {
        return status;
    }

    public void setStatus(StatusOfPosition status) {
        this.status = status;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setStatusChanged(boolean statusChanged) {
        isStatusChanged = statusChanged;
    }

    public boolean getIsStatusChanged(){
        return isStatusChanged;
    }

    public static Position getPositionByIndex(int index){
        for (Position position: allPositions)
            if (position.getIndex()==index)
                return position;
        return null;
    }
}
