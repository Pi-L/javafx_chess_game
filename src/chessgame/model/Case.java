package chessgame.model;

import chessgame.model.piece.Piece;

public class Case {

    private final int coordinateX;
    private final int coordinateY;

    private String color;

    private Piece piece;

    public Case(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

        setColor();
    }

    public boolean isBelongTo(String playerName) {
        if(playerName == null || piece == null) return false;

        if(PlayerEnum.BLACK.getName().equals(playerName)) return PlayerEnum.BLACK.equals(piece.getPlayerEnum());

        if(PlayerEnum.WHITE.getName().equals(playerName)) return PlayerEnum.WHITE.equals(piece.getPlayerEnum());

        return false;
    }

    private void setColor() {
        if((coordinateX + coordinateY) % 2 == 0) color = "#FFFFFF";
        else color = "#E0E0E0";
    }

    public String getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
