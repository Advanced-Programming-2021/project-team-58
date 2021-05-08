import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public static void run() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        while (!input.equals("menu exit")) {

            input = scan.nextLine();

            Matcher matchChangeMenu = getCommandMatcher(input, "^menu enter (Main|Duel|Deck|Scoreboard|Profile|Shop)$");

            Matcher matchLogin1 = getCommandMatcher(input, "^user login username ([a-zA-Z0-9]+) password ([a-zA-Z0-9]+)$");
            Matcher matchLogin2 = getCommandMatcher(input, "^user login password ([a-zA-Z0-9]+) username ([a-zA-Z0-9]+)$");

            Matcher matchRegister1 = getCommandMatcher(input, "^user create username ([a-zA-Z0-9]+) nickname ([a-zA-Z0-9]+) password ([a-zA-Z0-9]+)$");
            Matcher matchRegister2 = getCommandMatcher(input, "^user create username ([a-zA-Z0-9]+) password ([a-zA-Z0-9]+) nickname ([a-zA-Z0-9]+)$");
            Matcher matchRegister3 = getCommandMatcher(input, "^user create nickname ([a-zA-Z0-9]+) username ([a-zA-Z0-9]+) password ([a-zA-Z0-9]+)$");
            Matcher matchRegister4 = getCommandMatcher(input, "^user create nickname ([a-zA-Z0-9]+) password ([a-zA-Z0-9]+) username ([a-zA-Z0-9]+)$");
            Matcher matchRegister5 = getCommandMatcher(input, "^user create password ([a-zA-Z0-9]+) nickname ([a-zA-Z0-9]+) username ([a-zA-Z0-9]+)$");
            Matcher matchRegister6 = getCommandMatcher(input, "^user create password ([a-zA-Z0-9]+) username ([a-zA-Z0-9]+) nickname ([a-zA-Z0-9]+)$");

            if (matchRegister1.find()) {
                registerPlayer(matchRegister1.group(1), matchRegister1.group(3), matchRegister1.group(2));
            } else if (matchRegister2.find()) {
                registerPlayer(matchRegister2.group(1), matchRegister2.group(2), matchRegister2.group(3));
            } else if (matchRegister3.find()) {
                registerPlayer(matchRegister3.group(2), matchRegister3.group(3), matchRegister3.group(1));
            } else if (matchRegister4.find()) {
                registerPlayer(matchRegister4.group(3), matchRegister4.group(2), matchRegister4.group(1));
            } else if (matchRegister5.find()) {
                registerPlayer(matchRegister5.group(3), matchRegister5.group(1), matchRegister5.group(2));
            } else if (matchRegister6.find()) {
                registerPlayer(matchRegister6.group(2), matchRegister6.group(1), matchRegister6.group(3));
            } else if (matchLogin1.find()) {
                logInPlayer(matchLogin1.group(1), matchLogin1.group(2));
            } else if (matchLogin2.find()) {
                logInPlayer(matchLogin2.group(2), matchLogin2.group(1));
            } else if (matchChangeMenu.find()) {
                System.out.println("please login first");
            } else if (input.equals("menu exit")) {
                System.out.println("You Exited The Game Successfully!");
                return;
            } else if (input.equals("menu show-current")) {
                System.out.println("Login Menu");
            } else System.out.println("invalid command");
        }
    }

    public static void logInPlayer(String username, String password) {
        if (Player.getPlayerByUsername(username) == null) {
            System.out.println("Username and password didn't match!");
        } else {
            if (Player.getPlayerByUsername(username).getPassword() != null) {
                if (!Player.getPlayerByUsername(username).getPassword().equals(password)) {
                    System.out.println("Username and password didn't match!");
                } else {
                    setLoggedInPlayer(Player.getPlayerByUsername(username));
                    System.out.println("user logged in successfully!");
                    MainMenu.run();
                }
            }
        }
    }

    public static void registerPlayer(String username, String password, String nickname) {
        if (Player.getPlayerByUsername(username) != null) {
            System.out.println("user with username " + username + " already exists");
        } else {
            if (Player.isNicknameExists(nickname)) {
                System.out.println("user with nickname " + nickname + " already exists");
            } else {
                Player player = new Player(username, password, nickname);
                System.out.println("user created successfully!");
            }
        }
    }
}

