package Controller;

import Model.*;
import View.*;


import java.util.ArrayList;
import java.util.Scanner;
// for "Monster Reborn" card
public class SummonFromGraveyardEffect {
    private static boolean isAnyMonsterInArray(ArrayList<Card> array){
        for (Card card : array) {
            if(card instanceof MonsterCard){
                return true;
            }
        }
        return false;
    }

    public static void activate(Game game){
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if(input.equals("opponents graveyard")){
            game.specialSummon(input);
        }
        else{
            game.specialSummon("graveyard");
        }
    }
}
