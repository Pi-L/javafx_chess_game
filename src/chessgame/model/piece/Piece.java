package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.List;

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

    public abstract List<Case> getPossibleCaseList(Case[][] cases, Position position);

    protected void addLinearContinuousMove(Case[][] cases, PlayerEnum playerEnum, Position currPos, Position nextPosIncrement, List<Case> possibleCaseList) {
        Position newPos = new Position(currPos.getX() + nextPosIncrement.getX(), currPos.getY() + nextPosIncrement.getY());

        if(newPos.isOutOfBound()) return;

        Case nextCase = cases[newPos.getX()][newPos.getY()];

        // on quitte la recursion sans ajouter le mouvement si on tombe sur une piece de notre couleur
        if(nextCase.getPiece() != null && playerEnum.equals(nextCase.getPiece().playerEnum)) return;

        synchronized (this) {
            possibleCaseList.add(nextCase);
        }

        // on quitte la recursion en ayant ajouter le mouvement si on tombe sur une piece de l'autre couleur
        if(nextCase.getPiece() != null) return;

        addLinearContinuousMove(cases, playerEnum, newPos, nextPosIncrement, possibleCaseList);
    }


}
