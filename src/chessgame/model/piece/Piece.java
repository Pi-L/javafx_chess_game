package chessgame.model.piece;

import chessgame.model.PlayerEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Piece {

    protected static final String IMAGE_BASE_PATH = "/resources/pieces/";

    protected final PlayerEnum playerEnum;
    protected ImageView imageView;

    // todo: donner son deplacement ?

    public Piece(PlayerEnum playerEnum) {
        this.playerEnum = playerEnum;
        setImage();
    }

    /**
     * bad place ?
     */
    protected void setImage() {

        String className = this.getClass().getSimpleName().toLowerCase();
        String colorName = playerEnum.name().toLowerCase();

        imageView = new ImageView(IMAGE_BASE_PATH+colorName+"_"+className+".png");
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }
}
