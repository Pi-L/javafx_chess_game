package chessgame.model;

import chessgame.model.piece.Piece;
import chessgame.utils.Position;

import java.util.List;

public class Case {

    private final int x;
    private final int y;

    private Piece piece;

    private boolean isSelected;
    private boolean isSelectable;

    public Case(int coordinateX, int coordinateY) {
        this.x = coordinateX;
        this.y = coordinateY;

    }

    public Piece getPiece() {
        return piece;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }


    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelectable() {
        return isSelectable;
    }

    void setSelectable(boolean selectable) {
        isSelectable = selectable;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public boolean isSamePlayer(Case pCase) {
        if(piece == null || pCase.getPiece() == null) return false;

        return piece.getPlayerEnum().equals(pCase.getPiece().getPlayerEnum());
    }

}
