package View;

import Controller.*;
import Model.*;

import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class ProfileMenu {
    public static void run() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        while (!(input = scan.nextLine()).equals("menu exit")) {

            Matcher nicknameMatcher = getCommandMatcher(input.trim(), "^profile change --nickname (.+)$");
            Matcher passwordMatcher1 = getCommandMatcher(input.trim(), "^profile change --password --current ([A-Za-z0-9]+) --new ([A-Za-z0-9]+)$");
            Matcher passwordMatcher2 = getCommandMatcher(input.trim(), "^profile change --password --new ([A-Za-z0-9]+) --current ([A-Za-z0-9]+)$");
            Matcher passwordMatcher3 = getCommandMatcher(input.trim(), "^profile change --current ([A-Za-z0-9]+) --password --new ([A-Za-z0-9]+)$");
            Matcher passwordMatcher4 = getCommandMatcher(input.trim(), "^profile change --current ([A-Za-z0-9]+) --new ([A-Za-z0-9]+) --password $");
            Matcher passwordMatcher5 = getCommandMatcher(input.trim(), "^profile change --new ([A-Za-z0-9]+) --password --current ([A-Za-z0-9]+)$");
            Matcher passwordMatcher6 = getCommandMatcher(input.trim(), "^profile change --new ([A-Za-z0-9]+) --current ([A-Za-z0-9]+) --password$");


            if (input.matches("menu enter [A-Za-z ]+"))
                System.out.println("menu navigation is not possible");
            else if (input.equals("menu show-current"))
                System.out.println("Profile Menu");
            else if (nicknameMatcher.find()) {
                if (Player.getPlayerByNickName(nicknameMatcher.group(1)) != null)
                    System.out.println("user with nickname " + nicknameMatcher.group(1) + " already exists");
                else changeNickName(nicknameMatcher.group(1));
            } else if (passwordMatcher3.find()) {
                if (passwordMatcher3.group(1).equals(passwordMatcher3.group(2)))
                    System.out.println("please enter a new password");
                else if (passwordMatcher3.group(1).equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                    changePassword(passwordMatcher3.group(2));
                    System.out.println("password changed successfully!");
                } else System.out.println("current password is invalid");
            } else if (passwordMatcher1.find()) {
                if (passwordMatcher1.group(1).equals(passwordMatcher1.group(2)))
                    System.out.println("please enter a new password");
                else if (passwordMatcher1.group(1).equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                    changePassword(passwordMatcher1.group(2));
                    System.out.println("password changed successfully!");
                } else System.out.println("current password is invalid");
            } else if (passwordMatcher4.find()) {
                if (passwordMatcher4.group(1).equals(passwordMatcher4.group(2)))
                    System.out.println("please enter a new password");
                else if (passwordMatcher4.group(1).equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                    changePassword(passwordMatcher4.group(2));
                    System.out.println("password changed successfully!");
                } else System.out.println("current password is invalid");
            } else if (passwordMatcher2.find()) {
                if (passwordMatcher2.group(1).equals(passwordMatcher2.group(2)))
                    System.out.println("please enter a new password");
                else if (passwordMatcher2.group(2).equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                    changePassword(passwordMatcher2.group(1));
                    System.out.println("password changed successfully!");
                } else System.out.println("current password is invalid");
            } else if (passwordMatcher6.find()) {
                if (passwordMatcher6.group(1).equals(passwordMatcher6.group(2)))
                    System.out.println("please enter a new password");
                else if (passwordMatcher6.group(2).equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                    changePassword(passwordMatcher6.group(1));
                    System.out.println("password changed successfully!");
                } else System.out.println("current password is invalid");
            } else if (passwordMatcher5.find()) {
                if (passwordMatcher5.group(1).equals(passwordMatcher5.group(2)))
                    System.out.println("please enter a new password");
                else if (passwordMatcher5.group(2).equals(LoginMenu.getLoggedInPlayer().getPassword())) {
                    changePassword(passwordMatcher5.group(1));
                    System.out.println("password changed successfully!");
                } else System.out.println("current password is invalid");
            } else if (input.equals("--help"))
                System.out.println("menu exit\n" +
                        "menu enter (menu name)\n" +
                        "menu show-current\n" +
                        "profile change --nickname (new nickname)\n" +
                        "profile change --password --current (current password) --new (new password)");
            else System.out.println("invalid command");
        }
        try {
            jsonSaveAndLoad.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenu.run();
    }

    public static void changePassword(String newPassword) {
        LoginMenu.getLoggedInPlayer().setPassword(newPassword);
    }

    public static void changeNickName(String newNickName) {
        LoginMenu.getLoggedInPlayer().setNickname(newNickName);
        System.out.println("nickname changed successfully!");
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}