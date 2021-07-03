package Controller.graphics;

import Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class CardItemController {
    @FXML
    private ImageView imageItem;

    private Card card;

    public void setCard(Card card) {
        this.card = card;
    }

    public void setImageItem(ImageView imageItem) {
        this.imageItem = imageItem;
    }

    public void setItem(Card card){
        setCard(card);
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource(card.getImgSrc())).toString());
        imageItem = new ImageView();
//        imageItem.setFitHeight(218.5);
//        imageItem.setFitHeight(150);
        imageItem.setImage(image);
    }
}
