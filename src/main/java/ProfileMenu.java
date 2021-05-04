import java.util.regex.*;

public class ProfileMenu {
    public static void run(){}
    public void changePassword(String newPassword){

    }
    public void changeNickName(String nickName){

    }
    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
