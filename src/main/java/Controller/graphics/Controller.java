package Controller.graphics;

import Controller.graphics.CardItemController;
import Model.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    @FXML
    private ImageView cardImageSelected;

    @FXML
    private Label cardName;

    @FXML
    private Label cardPrice;

    @FXML
    private Button infoButton;

    @FXML
    private Button buyButton;

    @FXML
    private Button backButton;

    @FXML
    private Label userMoney;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private GridPane gridpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;
        for (int i = 0; i < Card.getAllCards().size(); i++) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getClassLoader().getResource("CardItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardItemController cardItemController = new CardItemController();
                cardItemController.setItem(Card.getAllCards().get(i));
                if(column == 8){
                    column = 0;
                    row++;
                }

                gridpane.add(anchorPane , column++ , row);

                gridpane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridpane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridpane.setMaxHeight(Region.USE_PREF_SIZE);

                gridpane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridpane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridpane.setMaxWidth(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane , new Insets(10));

            }
            catch (IOException e){
                e.printStackTrace();
            }



        }
    }
}
