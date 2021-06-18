package Controller;

import View.*;

import java.io.File;
import java.io.IOException;


public class ProgramController {
    public static void main(String[] args) {
        File file = new File("Players.txt");
        if(file.length() != 0) {
            try {
                jsonSaveAndLoad.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LoginMenu.addCards();
        LoginMenu.run();
    }
}
