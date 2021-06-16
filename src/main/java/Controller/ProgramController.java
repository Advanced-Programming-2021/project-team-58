package Controller;

import View.*;

import java.io.IOException;


public class ProgramController {
    public static void main(String[] args) {
        try {
            jsonSaveAndLoad.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginMenu.addCards();
        LoginMenu.run();
    }
}
