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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField searchBox;
    public Button searchButton;
    public ImageView cardImageSelected;
    public Label cardName;
    public Label cardPrice;
    public Button infoButton;
    public Button buyButton;
    public Button backButton;
    public Label userMoney;

    public ScrollPane scrollpane;
    public GridPane gridpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;
        for (int i = 0; i < Card.getAllCards().size(); i++) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CardItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardItemController cardItemController = new CardItemController();
                cardItemController.setItem(Card.getAllCards().get(i));
                gridpane.add(anchorPane , column , row);
                GridPane.setMargin(anchorPane , new Insets(10));
                if(column != 2){
                    column++;
                }
                else{
                    column = 0;
                    row++;
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }



        }
    }
}
