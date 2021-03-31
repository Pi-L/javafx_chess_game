package chessgame.model;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private final Case[][] caseArray = new Case[8][8];
    private final List<Piece> pieceBlancheList;
    private final List<Piece> pieceNoirList;

    public Plateau(List<Piece> pieceBlancheList, List<Piece> pieceNoirList) {
        this.pieceBlancheList = pieceBlancheList;
        this.pieceNoirList = pieceNoirList;
    }

    public List<Deplacement> deplacementsPossible(Piece piece) {
        List<Deplacement> deplacementList = new ArrayList<>();

        return deplacementList;
    }
}
