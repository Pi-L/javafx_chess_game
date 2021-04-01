package chessgame.model;

import chessgame.model.piece.*;
import chessgame.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Etat de la partie à un instant t
 */
public class Plateau {

    private final Case[][] caseArray = new Case[Constants.GRID_SIDE_SIZE][Constants.GRID_SIDE_SIZE];

    // why ?
//    private final Piece[] pieceBlancheList = { new Rook()};
//    private final Piece[] pieceNoirList = new ArrayList<>();

    public Plateau() {
        initPlateau();
    }

    public List<Deplacement> deplacementsPossible(Piece piece) {
        List<Deplacement> deplacementList = new ArrayList<>();

        return deplacementList;
    }

    /**
     * public car restart button
     * permet d'initialiser les pieces
     */
    public void initPlateau() {

        for (int i = 0; i < Constants.GRID_SIDE_SIZE; i++) {
            for (int j = 0; j < Constants.GRID_SIDE_SIZE; j++) {
                caseArray[i][j] = new Case(i, j);
            }
        }

        int blackFirstLineY = 0;
        int blackSecondLineY = 1;
        int whiteFirstLineY = Constants.GRID_SIDE_SIZE - 1;
        int whiteSecondLineY = Constants.GRID_SIDE_SIZE - 2;

        // init pawns
        for (int i = 0; i < Constants.GRID_SIDE_SIZE; i++) {

            caseArray[i][blackSecondLineY].setPiece(new Pawn(PlayerEnum.BLACK));
            caseArray[i][whiteSecondLineY].setPiece(new Pawn(PlayerEnum.WHITE));
        }

        // init rooks
        caseArray[0][blackFirstLineY].setPiece(new Rook(PlayerEnum.BLACK));
        caseArray[7][blackFirstLineY].setPiece(new Rook(PlayerEnum.BLACK));
        caseArray[0][whiteFirstLineY].setPiece(new Rook(PlayerEnum.WHITE));
        caseArray[7][whiteFirstLineY].setPiece(new Rook(PlayerEnum.WHITE));

        // init knights
        caseArray[1][blackFirstLineY].setPiece(new Knight(PlayerEnum.BLACK));
        caseArray[6][blackFirstLineY].setPiece(new Knight(PlayerEnum.BLACK));
        caseArray[1][whiteFirstLineY].setPiece(new Knight(PlayerEnum.WHITE));
        caseArray[6][whiteFirstLineY].setPiece(new Knight(PlayerEnum.WHITE));

        // init bishop
        caseArray[2][blackFirstLineY].setPiece(new Bishop(PlayerEnum.BLACK));
        caseArray[5][blackFirstLineY].setPiece(new Bishop(PlayerEnum.BLACK));
        caseArray[2][whiteFirstLineY].setPiece(new Bishop(PlayerEnum.WHITE));
        caseArray[5][whiteFirstLineY].setPiece(new Bishop(PlayerEnum.WHITE));

        // init Queen
        caseArray[3][blackFirstLineY].setPiece(new Queen(PlayerEnum.BLACK));
        caseArray[3][whiteFirstLineY].setPiece(new Queen(PlayerEnum.WHITE));

        // init King
        caseArray[4][blackFirstLineY].setPiece(new King(PlayerEnum.BLACK));
        caseArray[4][whiteFirstLineY].setPiece(new King(PlayerEnum.WHITE));

    }

    public Case[][] getCaseArray() {
        return caseArray;
    }
}
