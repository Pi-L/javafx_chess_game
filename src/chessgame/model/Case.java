package chessgame.model;

import chessgame.model.piece.Piece;

public class Case {

    private final int coordinateX;
    private final int coordinateY;

    private Piece piece;

    public Case(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

    }

    // todo: move to plateau ? - shouldn't be here
    public boolean isBelongTo(String playerName) {
        if(playerName == null || piece == null) return false;

        if(PlayerEnum.BLACK.getName().equals(playerName)) return PlayerEnum.BLACK.equals(piece.getPlayerEnum());

        if(PlayerEnum.WHITE.getName().equals(playerName)) return PlayerEnum.WHITE.equals(piece.getPlayerEnum());

        return false;
    }


    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
