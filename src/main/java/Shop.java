import java.util.*;
import java.util.regex.*;

public class Shop {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("menu exit")) {
            MonsterCard.addMonster();
            TrapAndSpellCard.addTrapAndSpell();

            if (input.matches("menu enter [A-Za-z ]+")) System.out.println("menu navigation is not possible");
            if (input.matches("menu show-current")) System.out.println("Shop");
            if (input.matches("shop show --all")) showAllCards();

            //buy
            Pattern buy1 = Pattern.compile("shop buy ([A-Za-z ]+)");
            Matcher buy2 = buy1.matcher(input);
            Player player = LoginMenu.getLoggedInPlayer();

            if (buy2.find()) {
                if (checkCardExist(buy2) && checkEnoughMoney(buy2, player))
                    buy(buy2,player);
            }
            input = scanner.nextLine();
        }

        MainMenu.run();
    }


    public static void buy(Matcher matcher, Player player) {
        if (matcher.find()) {
            player.decreaseMoney(Objects.requireNonNull(Card.getCardByName(matcher.group(1))).getPrice());
            player.getAllCards().add(Card.getCardByName(matcher.group(1)));
        }
    }

    public static boolean checkCardExist(Matcher matcher) {
        if (matcher.find()) {
            if (Card.getCardByName(matcher.group(1)) != null)
                return true;
            System.out.println("there is no card with this name");
            return false;
        }
        return false;
    }

    public static boolean checkEnoughMoney(Matcher matcher, Player player) {
        if (matcher.find()) {
            if (player.getMoney() >= Objects.requireNonNull(Card.getCardByName(matcher.group(1))).getPrice()) {
                System.out.println("Successful");
                return true;
            }
            System.out.println("not enough money!");
            return false;
        }
        return false;
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