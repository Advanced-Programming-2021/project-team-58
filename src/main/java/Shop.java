import java.util.*;
import java.util.regex.*;

public class Shop {
    public static void run() {
        String input;
        Scanner scanner = new Scanner(System.in);

        while (!(input = scanner.nextLine()).equalsIgnoreCase("menu exit")) {
            MonsterCard.addMonster();

            if (input.matches("shop buy ([A-Za-z]+)"))
                buy(getCommandMatcher(input, "shop buy ([A-Za-z]+)"));
            else if (input.matches("^ [ ]+ menu enter [A-Za-z]+ [ ]+ $"))
                System.out.println("menu navigation is not possible");

//             تا اینجا الان به نظرم تمیز شد
//             فقط باید تو تابع buy بررسی کنی که این اسمی که میگیره ایا وجود داره یا نه
//            و اینکه خریدار پول کافی داره یا نه
//            بقیه ش هم با همین منطق پیش بری به نظر مشکلی نخواهد بود


//            else if (input.trim().equalsIgnoreCase("menu show-current")) System.out.println("Shop Menu");
//            if (getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").find()) {
//                if (checkCardExist(getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").group(1)) &&
//                        checkEnoughMoney(getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)").group(1)))
//                    buy(getCommandMatcher(input.trim(), "shop buy ([A-Za-z]+)"));
//            }
//            if (getCommandMatcher(input.trim(), "^ [ ]+ shop show --all [ ]+ $").find())
//                showAllCards();
        }
        MainMenu.run(); //Navigating to the Main Menu at last
    }

    public static void buy(Matcher matcher) {
        if (matcher.find()) {
            String cardName = matcher.group(1);
            Player player = LoginMenu.getLoggedInPlayer();
            for (int i = 0; i < Card.getAllCards().size(); i++) {
                if (Card.getCardByName(cardName) != null) {
                    player.decreaseMoney(Objects.requireNonNull(Card.getCardByName(cardName)).getPrice());
                    player.getAllCards().add(Card.getCardByName(cardName));
                    System.out.println("successful");
                }
//                مثلا میتونی برای همین if یه else همینجا بذاری که اگر این کارت وجود نداشت خطا بده
            }
        }
    }

    public static boolean checkCardExist(String cardName) {

        if (Card.getCardByName(cardName) != null)
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

    public static void main(String[] args) {
        Shop.run();
    }
}
