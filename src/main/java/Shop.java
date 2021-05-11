import java.util.*;
import java.util.regex.*;

public class Shop {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Matcher matcher = null;
        if (input.matches("shop buy ([A-Za-z]+)")){
            matcher = getCommandMatcher(input, "shop buy ([A-Za-z]+)");
            buy(getCommandMatcher(input, "shop buy ([A-Za-z]+)").group(1));
        }
        if (input.trim().equalsIgnoreCase("menu exit")) MainMenu.run();
        else while (!input.equalsIgnoreCase("menu exit")) {
            MonsterCard.addMonster();
            if (getCommandMatcher(input.trim(), "^ [ ]+ menu enter [A-Za-z]+ [ ]+ $").find())
                System.out.println("menu navigation is not possible");
            if (input.trim().equalsIgnoreCase("menu show-current")) System.out.println("Shop");
            if (getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").find()) {
                if (checkCardExist(getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").group(1)) &&
                        checkEnoughMoney(getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").group(1)))
                    buy(getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").group(1));
            }
            if (getCommandMatcher(input.trim(), "^ [ ]+ shop show --all [ ]+ $").find())
                showAllCards();
            input = scanner.nextLine();
        }
    }

    public static void buy(String cardName) {
        Player player = LoginMenu.getLoggedInPlayer();
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            if (Card.getCardByName(cardName)!=null) {
                player.decreaseMoney(Objects.requireNonNull(Card.getCardByName(cardName)).getPrice());
                player.getAllCards().add(Card.getCardByName(cardName));
                System.out.println("successful");
            }
        }
    }

    public static boolean checkCardExist(String cardName) {

            if (Card.getCardByName(cardName)!=null)
                return true;

        System.out.println("there is no card with this name");
        return false;
    }

    public static boolean checkEnoughMoney(String cardName) {

            if (Card.getCardByName(cardName) != null) {
                Player player = LoginMenu.getLoggedInPlayer();
                if (Objects.requireNonNull(Card.getCardByName(cardName)).getPrice() > player.getMoney())
                    System.out.println("not enough money");
                return false;
            }

        return true;
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
