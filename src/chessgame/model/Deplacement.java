package chessgame.model;

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
