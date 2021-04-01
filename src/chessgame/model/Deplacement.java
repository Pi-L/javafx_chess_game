package chessgame.model;

import chessgame.model.piece.Piece;

/**
 * Le Mouvement d'une piece d'une case à une autre
 */
public class Deplacement {

    private Case startCase;
    private Case endCase;
    private Piece piece;

    public Deplacement(Case startCase, Case endCase, Piece piece) {
        this.startCase = startCase;
        this.endCase = endCase;
        this.piece = piece;
    }
}
