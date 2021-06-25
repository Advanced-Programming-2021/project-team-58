package View;

import Model.*;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.*;

public class MainMenu {
    public static void run() {
        handleInput();
        LoginMenu.run(); //Navigating to LoginMenu at last
    }

    private static void handleInput() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (!(input = scanner.nextLine()).equals("menu exit")) {

            if (input.trim().matches("^(?i)(menu[ ]+enter[ ]+(.+))$")) //doesn't contain duel & scoreboard menu
                enterAMenu(getCommandMatcher(input, "^(?i)(menu[ ]+enter[ ]+(.+))$"));
            else if (input.trim().matches("^(?i)(menu[ ]+show-current)$"))
                showMenuName();
            else if (input.trim().matches("^(?i)(user[ ]+logout)$")) {
                printProperRespondForLogOut();
                return;
            } else if (input.trim().matches("^(?i)(scoreboard show)$"))
                ScoreboardMenu.run();
            else if (input.matches("duel --second-player (.+) --rounds (.+) --new"))
                startAGame(getCommandMatcher(input, "duel --second-player (.+) --rounds (.+) --new"));
            else if (input.matches("duel --s-p (.+) --r (.+) --n"))
                startAGame(getCommandMatcher(input, "duel --s-p (.+) --r (.+) --n"));
            else if (input.matches("duel --new --ai --rounds (.*)")) {
                startAGameWithAI(getCommandMatcher(input, "duel --new --ai --rounds (.*)"));
            } else if (input.matches("duel --n --ai --r (.*)")) {
                startAGameWithAI(getCommandMatcher(input, "duel --n --ai --r (.*)"));
            } else if (input.equals("--help"))
                help();
            else System.out.println("invalid command");
        }
    }

    private static void help() {
        System.out.println("menu exit\n" +
                "menu enter (menu name)\n" +
                "menu show-current\n" +
                "user logout\n" +
                "scoreboard show\n" +
                "duel --second-player (second-player name) --rounds (number of rounds) --new\n" +
                "duel --new --ai --rounds (number of rounds)");
    }

    private static void startAGameWithAI(Matcher matcher) {
        if (matcher.find()) {
            int numOfRounds = 0;
            try {
                numOfRounds = Integer.parseInt(matcher.group(1));
            } catch (Exception e) {
                System.out.println("please enter an integer");
                return;
            }

            Player loggedInPlayer = LoginMenu.getLoggedInPlayer();

            if (loggedInPlayer.getActiveDeck() == null)
                System.out.println(loggedInPlayer.getUsername() + " has no activated deck");
            else if (!loggedInPlayer.getActiveDeck().isValid())
                System.out.println(loggedInPlayer.getUsername() + "'s deck is invalid");
            else if (numOfRounds != 1 && numOfRounds != 3)
                System.out.println("number of rounds is not supported");
            else {
                System.out.println("New game started between " + LoginMenu.getLoggedInPlayer().getNickname() +
                        " and AI!\n");
                AIClass aiPlayer = new AIClass();
                DuelMenu.run(LoginMenu.getLoggedInPlayer(), aiPlayer, numOfRounds);
            }
        }
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
            int numOfRounds = Integer.parseInt(matcher.group(2));
            String opponentName = matcher.group(1);
            Player loggedInPlayer = LoginMenu.getLoggedInPlayer();

            if (Player.getPlayerByUsername(opponentName) == null)
                System.out.println("there is no player with this username");
                //The following isn't mentioned in the doc.
            else if (loggedInPlayer.getUsername().equals(opponentName))
                System.out.println("You can't play with yourself. Please choose another player");
            else if (loggedInPlayer.getActiveDeck() == null)
                System.out.println(loggedInPlayer.getUsername() + " has no activated deck");
            else if (Objects.requireNonNull(Player.getPlayerByUsername(opponentName)).getActiveDeck() == null)
                System.out.println(opponentName + " has no activated deck");
            else if (!loggedInPlayer.getActiveDeck().isValid())
                System.out.println(loggedInPlayer.getUsername() + "'s deck is invalid");
            else if (!Objects.requireNonNull(Player.getPlayerByUsername(opponentName)).getActiveDeck().isValid())
                System.out.println(opponentName + "'s deck is invalid");
            else if (numOfRounds != 1 && numOfRounds != 3)
                System.out.println("number of rounds is not supported");
            else {
                String opponentNickName = Objects.requireNonNull(Player.getPlayerByUsername(opponentName)).getNickname();
                System.out.println("New game started between " + LoginMenu.getLoggedInPlayer().getNickname() +
                        " and " + opponentNickName + "!\n");
                DuelMenu.run(LoginMenu.getLoggedInPlayer(), Player.getPlayerByUsername(opponentName), numOfRounds);
            }
        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}

