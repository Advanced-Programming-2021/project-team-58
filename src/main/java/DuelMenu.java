import java.util.regex.*;

public class DuelMenu {

    private int round;

    public static void run() {

    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
