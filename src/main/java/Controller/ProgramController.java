package Controller;

import Model.Player;
import View.*;

import java.io.IOException;
import java.util.ArrayList;


public class ProgramController {
    public static void main(String[] args) throws Exception {
        JsonSaveAndLoad.load();
        LoginMenu.addCards();
        LoginMenu.run();
    }
}
