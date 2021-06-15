import View.LoginMenu;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField usernameLoginField;
    @FXML
    private TextField passwordLoginField;
    @FXML
    private TextField usernameRegisterField;
    @FXML
    private TextField nicknameRegisterField;
    @FXML
    private TextField passwordRegisterField;

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRegisterMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToScoreBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scoreboard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToProfileMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Profile.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Login(ActionEvent event) throws IOException {
        String username = usernameLoginField.getText();
        String password = passwordLoginField.getText();
        if (username == null) return;
        if (password == null) return;
        LoginMenu.logInPlayer(username, password);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToWelcomeMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WelcomeMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Register(ActionEvent event) throws IOException {
        try {
            String username = usernameRegisterField.getText();
            String password = passwordRegisterField.getText();
            String nickname = nicknameRegisterField.getText();
            if (username == null) return;
            if (nickname == null) return;
            if (password == null) return;
            LoginMenu.registerPlayer(username, password, nickname);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Enter a Valid Input");
            alert.show();
        }
    }

    public void Exit(ActionEvent event) {
        stage.close();
    }

    public void LogOut(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
