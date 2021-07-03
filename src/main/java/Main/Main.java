package Main;

import Model.MonsterCard;
import Model.TrapAndSpellCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MonsterCard.addMonster();
        TrapAndSpellCard.addTrapAndSpell();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Shop.fxml")));
        Scene scene = new Scene(root);
//        Rectangle2D screenBounds = Screen.getPrimary ().getVisualBounds ();
//        Scene scene = new Scene (root, screenBounds.getWidth (), screenBounds.getHeight ());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
