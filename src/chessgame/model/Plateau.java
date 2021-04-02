package chessgame.model;

import chessgame.model.piece.*;
import chessgame.utils.Constants;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.List;

/**
 * Etat de la partie à un instant t
 */
public class Plateau {

    /**
     * Tableau 2D de case representant l'état de la partie
     */
    private final Case[][] caseArray = new Case[Constants.GRID_SIDE_SIZE][Constants.GRID_SIDE_SIZE];


    /**
     * On initialise le tableau une seul fois à la construction
     * Pour redemarrer un nouvelle partie il suffira de re-intancier un nouvel objet plateau
     */
    public Plateau() {
        initPlateau();
    }

    /**
     * <p>Chaque case du tableau est instancié avec ses coordonnées dans le tableau</p>
     * <p>Chaque case instanciée a sa propriété piece à null, puis on positionne les pieces une par une</p>
     * <p>Chaque case correspond à un Pane dans la vue. Le lien entre les 2 se fait par correspondance de coordonnées entre
     * Pane[][] panes dans le controller et Case[][]</p>

     *
     * todo: utiliser la class Position à la place de 2 entier dans le constructeur de Case
     */
    private void initPlateau() {

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

    /**
     *
     * @param position La classe Position contient 2 entiers, un pour l'axe des x et un pour l'axe des y, qui sont utilisés comme indice d'un tableau
     * @return une case située sur le plateau
     * @throws IllegalArgumentException la classe Position permet de dire si cette position est située en dehors du plateau. Ici, si c'est le cas, on lance une exception
     */
    Case getCase(Position position) throws IllegalArgumentException {
        if(position.isOutOfBound()) throw new IllegalArgumentException();

        return caseArray[position.getX()][position.getY()];
    }


    /**
     *
     * @param position La classe Position contient 2 entiers, un pour l'axe des x et un pour l'axe des y
     * @return une piece
     * @throws IllegalArgumentException exception lancée par getCase(Position pos) si la case est située en dehors du plateau
     */
    Piece getPiece(Position position) throws IllegalArgumentException {
        Piece piece = getCase(position).getPiece();

        return piece;
    }

    /**
     * Permet de recuperer un liste de mouvement possibles pour une piece en fonction de l'etat du plateau, du type de piece, et de sa position
     * @param pCase une case qui a une piece dessus (la case actuellement selectionnée). La classe Partie se charge de verifier que la case séléctionnée à bien un piece dessus.
     * @return une liste de case sur laquelle la piece de la case en parametre peut se deplacer. Celle-ci sera stoquée dans la propriété caseSelectableList de la classe Partie
     * @throws IllegalArgumentException si la case en parametre n'a pas de piece dessus
     */
    List<Case> getPossibleMoveList(Case pCase) throws IllegalArgumentException {

        Piece currentPiece = pCase.getPiece();

        if(currentPiece == null) throw new IllegalArgumentException();

        Position casePosition = new Position(pCase.getX(), pCase.getY());

        return currentPiece.getPossibleCaseList(caseArray, casePosition);



    }

}
