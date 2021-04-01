package chessgame.model.piece;

import chessgame.utils.PlayerEnum;

public abstract class Piece {

    protected static final String IMAGE_BASE_PATH = "/resources/pieces/";

    protected final PlayerEnum playerEnum;
    protected String imagePath;

    // todo: donner son deplacement ?

    public Piece(PlayerEnum playerEnum) {
        this.playerEnum = playerEnum;
        setImage();
    }

    protected void setImage() {

        String className = this.getClass().getSimpleName().toLowerCase();
        String colorName = playerEnum.name().toLowerCase();

        imagePath = IMAGE_BASE_PATH+colorName+"_"+className+".png";
    }

    public String getImagePath() {
        return imagePath;
    }


    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }
}