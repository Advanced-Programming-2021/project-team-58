import java.util.ArrayList;

public class ScoreboardMenu {
    private ArrayList<Player> playersSort = Player.getAllPlayers();

    public void showScoreboard(){
        int rank = 1;
        for(int i=0 ; i<playersSort.size() ; i++){
            if(i == 0) {
                System.out.println("1" + playersSort.get(i).getNickname() + ": " + playersSort.get(i).getScore());
            }
            else{
                if(playersSort.get(i-1).getScore() != playersSort.get(i).getScore()){
                    rank = i+1;
                }
                System.out.println(rank + playersSort.get(i).getNickname() + ": " + playersSort.get(i).getScore());
            }
        }
    }

    public void sort(){
        for(int i=0 ; i < playersSort.size()-1 ; i++){
            for(int j=i+1 ; j < playersSort.size() ; j++){
                if( compare (playersSort.get(j) , playersSort.get(i)) ){
                    Player tmp = playersSort.get(j);
                    playersSort.set(j , playersSort.get(i));
                    playersSort.set(i , tmp);
                }
            }
        }
    }

    public boolean compare(Player player1 , Player player2){
        boolean condition1 = player1.getScore() > player2.getScore();
        boolean condition2 = (player1.getNickname().compareTo(player2.getNickname()) < 0);
        boolean condition3 = (player1.getScore() == player2.getScore());
        if(condition1){
            return true;
        }
        else{
            if ((condition2) && (condition3)){
                return true;
            }
            else{
                return false;
            }
        }
    }

}
