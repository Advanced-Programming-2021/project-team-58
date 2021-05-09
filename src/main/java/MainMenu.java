import java.util.Scanner;
import java.util.regex.*;

public class MainMenu {
    public static void run() {

        Scanner scanner = new Scanner(System.in);
        String input;

        while (!(input = scanner.nextLine()).equals("menu exit")) {

            if (input.trim().matches("^(?i)(menu[ ]+enter[ ]+(.+))$")) //doesn't contain duel & scoreboard menu
                enterAMenu(getCommandMatcher(input, "^(?i)(menu[ ]+enter[ ]+(.+))$"));
            else if (input.trim().matches("^(?i)(menu[ ]+show-current)$"))
                showMenuName();
            else if (input.trim().matches("^(?i)(user[ ]+logout)$")) {
                printProperRespondForLogOut();
                break;
            } else if (input.trim().matches("duel (.*)"))
                startAGame(getCommandMatcher(input, "duel (.*)"));
            else System.out.println("invalid command");
        }
        LoginMenu.run(); //Navigating to LoginMenu at last
    }

    private static void enterAMenu(Matcher matcher) {
        if (matcher.find()) {
            String menuName = matcher.group(2);
            if (menuName.matches("^(?i)(profile([ -_]*menu)?)$"))
                ProfileMenu.run();
            else if (menuName.matches("^(?i)(shop([ -_]*menu)?)$"))
                Shop.run();
            else if (menuName.matches("^(?i)(deck([ -_]*menu)?)$"))
                DeckMenu.run();
            else if (menuName.matches("^(?i)((import[ -_/]*export|export[ -_/]*import)([ ]*menu)?)$"))
                Import_ExportMenu.run();
            else System.out.println("The entered menu does not exist\n" +
                        "Or you should use some other commands to navigate to those menus\n" +
                        "E.g. if you want to go to ScoreBoard Menu you'd rather enter:\n" +
                        "\"scoreboard show\"\n" +
                        "Or if you want to go to Duel Menu you'd rather start a game by:\n" +
                        "\"duel new <opponent's username> rounds <1/3>\"");
        }
    }

    private static void showMenuName() {
        System.out.println("Main Menu");
    }

    private static void printProperRespondForLogOut() {
        System.out.println("User logged out successfully!");
    }

    public static void startAGame(Matcher matcher) {
        if (matcher.find()) {
// In this level you should check if the activated deck is valid or not. If it's valid then you are allowed to start the game
//            int round;
//            Player player2;
//            DuelMenu.run(LoginMenu.getLoggedInPlayer(), player2, round);
        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
