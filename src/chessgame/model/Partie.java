package chessgame.model;

import java.util.List;

public class Partie {

    private Plateau plateau;
    private List<Deplacement> deplacementList;
    private JoueurEnum currentPlayer;


    public Partie(Plateau plateau, List<Deplacement> deplacementList, JoueurEnum currentPlayer) {
        this.plateau = plateau;
        this.deplacementList = deplacementList;
        this.currentPlayer = currentPlayer;
    }

    public void makeMove(Deplacement deplacement) {

    }

    public void resetMove(Deplacement deplacement) {

    }
}
