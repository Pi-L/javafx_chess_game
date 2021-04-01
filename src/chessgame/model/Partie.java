package chessgame.model;

import chessgame.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Partie {

    // le plateau est l'etat au temps "t" de la partie
    private Plateau plateau;

    // historique des deplacements
    private List<Deplacement> deplacementList;

    private PlayerEnum currentPlayer;


    public Partie() {
        super();
    }

    public void newGame() {
        plateau = new Plateau();
        deplacementList = new ArrayList<>();
        currentPlayer  = PlayerEnum.WHITE;
    }

    public void selectPiece(int x, int y) throws IllegalArgumentException {
        // plateau.!cases[x][y].isBelongTo(partie.getCurrentPlayer())
    }

    public String getPieceImagePath(int x, int y) throws IllegalArgumentException {
        Piece piece = plateau.getPiece(x, y);
        return piece.getImagePath();
    }


    public void makeMove(int x1, int y1, int x2, int y2) {

    }

    public void resetMove() {

    }

    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }

    public Plateau getPlateau() {
        return new Plateau(plateau);
    }
}
