package View;

import Controller.*;
import Model.*;

import java.util.*;
import java.util.regex.*;

public class Shop {
    public static void run() throws Exception {
        Collections.sort(MonsterCard.getAllMonsterCards());
        Collections.sort(TrapAndSpellCard.getAllCards());

        String input;
        Scanner scanner = new Scanner(System.in);
        while (!(input = scanner.nextLine()).equalsIgnoreCase("menu exit")) {

            if (input.matches("menu enter [A-Za-z ]+")) System.out.println("menu navigation is not possible");
            else if (input.matches("menu show-current")) System.out.println("Shop Menu");
            else if (input.matches("shop show --all")) showAllCards();
            else if (input.matches("shop buy (.+)"))
                buy(getCommandMatcher(input,"shop buy (.+)"));
            else System.out.println("invalid command");
        }
        MainMenu.run();
    }

    public static void buy(Matcher matcher) {
        if (matcher.find()) {
            Player player = LoginMenu.getLoggedInPlayer();
            String cardName = matcher.group(1);
            if (Card.getCardByName(cardName) == null)
                System.out.println("there is no card with this name");
            else if (player.getMoney()<Card.getCardByName(cardName).getPrice())
                System.out.println("not enough money");
            else {
                player.decreaseMoney(Card.getCardByName(cardName).getPrice());
                player.getAllCards().add(Card.getCardByName(cardName));
                System.out.println("Card with name "+cardName+" was bought successfully!");
            }
        }
    }

    public static void showAllCards() {
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            System.out.println(Card.getAllCards().get(i).getCardName() + " : " + Card.getAllCards().get(i).getPrice());
        }
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}