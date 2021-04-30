import java.util.regex.*;

public class Shop {
    public static void run() {

    }
    public void buy(){

    }
    public void showAllCards(){

    }
    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
