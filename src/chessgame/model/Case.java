package chessgame.model;

import chessgame.model.piece.Piece;

public class Case {

    private final int x;
    private final int y;

    private Piece piece;

    // Definie si une case est selectionnée
    private boolean isSelected;
    // definie si une case est selectionnable ("set" dans la classe Partie en fonction d'une piece selectionnée et ses capacité de mouvement)
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
