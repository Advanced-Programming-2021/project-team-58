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
            } else if (input.trim().matches("^(?i)(duel (--.+) (--.+) (--.+))$"))
                startAGame(getCommandMatcher(input, "^(?i)(duel (--.+) (--.+) (--.+))$"));
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
            Player loggedInPlayer = LoginMenu.getLoggedInPlayer();
            int numOfRounds = 1;
            String opponentName = findOpponentName(matcher);
            boolean isNewEntered = isNewEntered(matcher);
//            if (opponentName != null && isNewEntered && numOfRounds != 0) {
            System.out.println(opponentName);

                if (Player.getPlayerByUsername(opponentName) == null)
                    System.out.println("there is no player with this username");
                else if (loggedInPlayer.getActiveDeck() == null)
                    System.out.println(loggedInPlayer.getUsername() + " had no activated deck");
                else if (Player.getPlayerByUsername(opponentName).getActiveDeck() == null)
                    System.out.println(opponentName + " had no activated deck");
                else if (!loggedInPlayer.getActiveDeck().isValid())
                    System.out.println(loggedInPlayer.getUsername() + "'s deck is invalid");
                else if (!Player.getPlayerByUsername(opponentName).getActiveDeck().isValid())
                    System.out.println(opponentName + "'s deck is invalid");
                else if (numOfRounds != 1 && numOfRounds != 3)
                    System.out.println("number of rounds is not supported");
                else{
                    System.out.println("a new game is being started");
                }
//             else System.out.println("invalid command");

//            DuelMenu.run(LoginMenu.getLoggedInPlayer(), player2, round);
        }
    }

    private static int findNumOfRounds(Matcher matcher) {
        int numOfRounds = 0;
        Matcher matcher1 = null;
        for (int i = 2; i < matcher.groupCount(); i++) {
            if (matcher.group(i).matches("(?i)(--rounds (.+))"))
                matcher1 = getCommandMatcher(matcher.group(i),"(?i)(--rounds (.+))");
            if (matcher1.find())
                numOfRounds = Integer.parseInt(matcher1.group(2));
        }
        return numOfRounds;
    }

    private static String findOpponentName(Matcher matcher) {
        String opponentName = null;
        Matcher matcher1 = null;
        for (int i = 2; i < matcher.groupCount(); i++) {
            if (matcher.group(i).matches("(?i)(--second-player (.+))"))
                matcher1 = getCommandMatcher(matcher.group(i),"(?i)(--second-player (.+))");
            if (matcher1.find())
                opponentName = matcher1.group(2);
        }
        return opponentName;
    }

    private static boolean isNewEntered(Matcher matcher) {
        if (matcher.group(2).matches("(?i)(--new)"))
            return true;
        return false;
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
