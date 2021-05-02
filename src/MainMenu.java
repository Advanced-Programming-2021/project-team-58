import java.util.regex.*;

public class MainMenu {
    public static void run() {

        Scanner scanner = new Scanner(System.in);
        String input;

        while (!(input = scanner.nextline()).equals("menu exit")) {

            if (input.trim().matches("^(?i)(menu[ ]+enter[ ]+([\\w]+))$")) //doesn't contain duel and scoreboard menu
                enterAMenu(getCommandMatcher(input, "^(?i)(menu[ ]+enter[ ]+([\\w]+))$"));
            else if (input.trim().matches("^(?i)(menu[ ]+show-current)$"))
                showMenuName();
            else if (input.trim().matches("^(?i)(user[ ]+logout)$")) {
                printProperRespondForLogOut();
                break;
            } else if (input.trim().matches("duel (.*)"))
                startAGame(getCommandMatcher(input, "duel (.*)"));
            else System.out.println("invalid command");
        }
        LoginMenu.run(); //Navigating to the login menu at last
    }

    private void enterAMenu(Matcher matcher) {
        if (matcher.find()) {
            String menuName = matcher.group(1);
            if (menuName.matches("^(?i)(profile([ -_]*menu)?)$"))
                ProfileMenu.run();
            else if (menuName.matches("^(?i)(shop([ -_]*menu)?)$"))
                Shop.run();
            else if (menuName.matches("^(?i)(deck([ -_]*menu)?)$"))
                DeckMenu.run();
            else System.out.println("The entered menu does not exist\n" +
                        "Or you should use some other commands to navigate to those menus\n" +
                        "E.g. if you want to go to ScoreBoard Menu you'd rather enter:\n" +
                        "\"scoreboard show\"\n" +
                        "Or if you want to go to Duel Menu you'd rather start a game by:\n" +
                        "\"duel new <opponent's username> rounds <an odd number>\"");
        }
    }

    private void showMenuName() {
        System.out.println("Main Menu");
    }

    private void printProperRespondForLogOut() {
        System.out.println("User logged out successfully!");
    }

    public void startAGame(Matcher matcher) {
        if (matcher.find()) {

        }
    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
