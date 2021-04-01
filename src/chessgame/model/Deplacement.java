package chessgame.model;

import chessgame.model.piece.Piece;

/**
 * Le Mouvement d'une piece d'une case à une autre
 */
public class Deplacement {

    private Case startCase;
    private Case endCase;
    private Piece piece;
    private Piece pieceCaptured;

    public Deplacement() {
        super();
    }

    Case getStartCase() {
        return startCase;
    }

    void setStartCase(Case startCase) throws IllegalArgumentException {
        if(startCase.getPiece() == null) throw new IllegalArgumentException();

        this.piece = startCase.getPiece();
        this.startCase = startCase;
    }

    Case getEndCase() {
        return endCase;
    }

    void setEndCase(Case endCase) throws IllegalArgumentException {
        if(startCase == null) throw new IllegalArgumentException();

        if(endCase.getPiece() != null && piece.getPlayerEnum().equals( endCase.getPiece().getPlayerEnum() )) {
            throw new IllegalArgumentException();
        }

        if(endCase.getPiece() != null) setPieceCaptured(endCase.getPiece());

        this.endCase = endCase;
    }

    Piece getPiece() {
        return piece;
    }

    void undo() {
        startCase.setPiece(piece);

        if(pieceCaptured != null) endCase.setPiece(pieceCaptured);
        else endCase.setPiece(null);
    }

    Piece getPieceCaptured() {
        return pieceCaptured;
    }

    void setPieceCaptured(Piece pieceCaptured) {
        this.pieceCaptured = pieceCaptured;
    }

    @Override
    public String toString() {
        return "Deplacement{" +
                "startCase=" + startCase +
                ", endCase=" + endCase +
                ", piece=" + piece +
                ", pieceCaptured=" + pieceCaptured +
                '}';
    }
}
