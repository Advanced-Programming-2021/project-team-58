import java.util.*;
import java.util.regex.*;

public class ProfileMenu {
    public void run() {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        //nickname changing
        if (getCommandMatcher(input, "^[ ]+ profile change nickname ([A-Za-z]+) [ ]+ $").find()) {
            if (getCommandMatcher(input, "^[ ]+ profile change nickname ([A-Za-z]+) [ ]+ $").group(1).equals(LoginMenu.getLoggedInPlayer().getNickname()))
                System.out.println("user with nickname " + getCommandMatcher(input, "^[ ]+ profile change nickname ([A-Za-z]+) [ ]+ $").group(1) + " already exists");
            else {
                changeNickName(getCommandMatcher(input, "^[ ]+ profile change nickname ([A-Za-z]+) [ ]+ $").group(1));
                System.out.println("nickname changed successfully!");
            }
        } else if (getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) nickname [ ]+ $").find()) {
            if (getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) nickname [ ]+ $").group(1).equals(LoginMenu.getLoggedInPlayer().getNickname()))
                System.out.println("user with nickname " + getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) nickname [ ]+ $").group(1) + " already exists");
            else {
                changeNickName(getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) nickname [ ]+ $").group(1));
                System.out.println("nickname changed successfully!");
            }
        }


        //password changing  q p j
        if (getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").find()) {
            if (getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").group(1).equals(getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").group(0)))
                System.out.println("please enter a new password");
            else if (getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").group(1)
                    .equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                changePassword(getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").group(2));
                System.out.println("password changed successfully!");
            } else if (getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").group(2)
                    .equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                changePassword(getCommandMatcher(input, "^[ ]+ profile change ([A-Za-z]+) password ([A-Za-z]+) [ ]+ $").group(1));
                System.out.println("password changed successfully!");
            } else System.out.println("current password is invalid");
        }
        //q j p
        if (getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").find()) {
            if (getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").group(0).equals(getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").group(1)))
                System.out.println("please enter a new password");
            else if (getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").group(1)
                    .equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                changePassword(getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").group(2));
                System.out.println("password changed successfully!");
            } else if (getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").group(2)
                    .equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                changePassword(getCommandMatcher(input, "^ [ ]+ profile change ([A-Za-z]+) ([A-Za-z]+) password [ ]+ $").group(1));
                System.out.println("password changed successfully!");
            } else System.out.println("current password is invalid");
        }
        //p q j
        if (getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").find()) {
            if (getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").group(1).equals(getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").group(0)))
                System.out.println("please enter a new password");
            else if (getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").group(1)
                    .equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                changePassword(getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").group(2));
                System.out.println("password changed successfully!");
            } else if (getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").group(2)
                    .equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                changePassword(getCommandMatcher(input, "^ [ ]+ profile change password ([A-Za-z]+) ([A-Za-z]+) [ ]+ $").group(1));
                System.out.println("password changed successfully!");
            } else System.out.println("current password is invalid");
        }


    }

    public void changePassword(String newPassword) {
        LoginMenu.getLoggedInPlayer().setPassword(newPassword);
    }

    public void changeNickName(String newNickName) {
        LoginMenu.getLoggedInPlayer().setNickname(newNickName);
    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
