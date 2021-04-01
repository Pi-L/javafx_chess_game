package chessgame.model;

import java.util.ArrayList;
import java.util.List;

public class Partie {

    // le plateau est l'etat au temps "t" de la partie
    private Plateau plateau;

    // historique des deplacements
    private List<Deplacement> deplacementList;

    private PlayerEnum currentPlayer;


    public Partie() {
        plateau = new Plateau();
        deplacementList = new ArrayList<>();
        currentPlayer  = PlayerEnum.WHITE;
    }

    public void makeMove(int x1, int y1, int x2, int y2) {

    }

    public void resetMove() {

    }

    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
