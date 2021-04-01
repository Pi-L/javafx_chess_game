package chessgame.model;

import chessgame.model.piece.Piece;

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

    // todo: move to plateau ? - shouldn't be here
//    public boolean isBelongTo(String playerName) {
//        if(playerName == null || piece == null) return false;
//
//        if(PlayerEnum.BLACK.getName().equals(playerName)) return PlayerEnum.BLACK.equals(piece.getPlayerEnum());
//
//        if(PlayerEnum.WHITE.getName().equals(playerName)) return PlayerEnum.WHITE.equals(piece.getPlayerEnum());
//
//        return false;
//    }


    Piece getPiece() {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
