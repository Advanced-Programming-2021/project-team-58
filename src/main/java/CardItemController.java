import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardItemController {
    public ImageView imageItem;

    private Card card;

    public void setCard(Card card) {
        this.card = card;
    }

    public void setImageItem(ImageView imageItem) {
        this.imageItem = imageItem;
    }

    public void setItem(Card card){
        setCard(card);
        Image image = new Image(getClass().getResource(card.getImgSrc()).toExternalForm());
        imageItem.setImage(image);
    }
}
