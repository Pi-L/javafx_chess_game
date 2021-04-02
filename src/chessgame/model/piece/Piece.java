package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.List;

public abstract class Piece {

    protected static final String IMAGE_BASE_PATH = "/resources/pieces/";

    protected final PlayerEnum playerEnum;
    protected String imagePath;

    /**
     *
     * @param playerEnum un Enum representant le joueur (Noirs ou Blancs)
     */
    public Piece(PlayerEnum playerEnum) {
        this.playerEnum = playerEnum;
        setImage();
    }

    /**
     * <p>Les images de chaques pieces sont dans le dossier src/resources/pieces</p>
     * <p>On "set" dynamiquement le chemin de l'image en fonction de la couleur du joueur auquel apparatient cette piece, et du nom de la piece</p>
     *
     * <p>todo: peut-etre donner une propriété nom à la piece plutot que de se baser sur this.getClass().getSimpleName().toLowerCase()</p>
     */
    protected void setImage() {

        String className = this.getClass().getSimpleName().toLowerCase();
        String colorName = playerEnum.name().toLowerCase();

        imagePath = IMAGE_BASE_PATH+colorName+"_"+className+".png";
    }

    /**
     *
     * @return le chemin de l'image de la piece
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *
     * @return l'instance de l'enum representant le joueur auquel cette piece appartient
     */
    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }

    /**
     * Methode permettant de recuperer la liste des cases possibles sur lesquelles une piece peut se deplacer à un instant donné de la partie.
     * Cette instant est representé par l'etat du tableau de cases (Case[][]) en param.
     *
     * @param cases Le tableau 2D de cases representant l'état de la partie à un instant donné
     * @param position La position de la piece sur le plateau
     * @return une liste de case ou la piece pourra se deplacer
     *
     * <p>todo: à la place de la position, passer la case de la piece directement</p>
     */
    public abstract List<Case> getPossibleCaseList(Case[][] cases, Position position);

    /**
     * <p>Methode recursive permettant d'ajouter une case à chaque recursion. Chaque case represente un mouvement possible d'une piece.</p>
     * <p>Cette methode est utilisée pour les pieces qui ont un mouvement "lineaire continue" (reine, fou, tour), c'est à dire, un mouvement lineaire qui ne peut etre arrété que par les bord du plateau ou une autre piece</p>
     * <p>On parcours donc le mouvement potentiel d'une piece sur le plateau dans une direction jusqu'a ce quelle rencontre un obstacle</p>
     *
     * @param cases Le tableau 2D de cases representant l'état de la partie à un instant donné
     * @param playerEnum la couleur du joueur courant. Etant donné qu'on avance de case en case, on ne peut pas se baser sur la case precedente pour determiner la couleur du joueur courant.
     * @param currPos La position de la case sur laquelle on est dans notre "parcours"
     * @param nextPosIncrement Pas une position en soit, mais represente l'increment de currPos a effectuer pour avoir la position possible suivante de la piece
     * @param possibleCaseList la liste a remplir avec des Case representant des mouvment possibles
     */
    protected void addLinearContinuousMove(Case[][] cases, PlayerEnum playerEnum, Position currPos, Position nextPosIncrement, List<Case> possibleCaseList) {
        Position newPos = new Position(currPos.getX() + nextPosIncrement.getX(), currPos.getY() + nextPosIncrement.getY());

        if(newPos.isOutOfBound()) return;

        Case nextCase = cases[newPos.getX()][newPos.getY()];

        // on quitte la recursion sans ajouter le mouvement si on tombe sur une piece de notre couleur
        if(nextCase.getPiece() != null && playerEnum.equals(nextCase.getPiece().playerEnum)) return;

        // une piece comme la reine, par exemple, peut partir dans 8 direction differentes. De ce cas on utilisera donc 8 Threads
        // on synchronise l'ajout dans la list pour être sur que cette operation se fait de façon atomique
        // on aurait aussi pu utiliser un autre type qu'une ArrayList
        synchronized (this) {
            possibleCaseList.add(nextCase);
        }

        // on quitte la recursion en ayant ajouter le mouvement si on tombe sur une piece de l'autre couleur
        if(nextCase.getPiece() != null) return;

        // Si on a pas rencontré d'obstacle, on passe à la recursion suivante, la position courrant devenant la nouvelle position
        addLinearContinuousMove(cases, playerEnum, newPos, nextPosIncrement, possibleCaseList);
    }

    /**
     * <p>Methode non recursive appelant la methode recursive addLinearContinuousMove()</p>
     * <p>Cette methode est utilisée pour les pieces qui ont un mouvement "lineaire continue" (reine, fou, tour)</p>
     *
     * @param cases Le tableau 2D de cases representant l'état de la partie à un instant donné
     * @param playerEnum la couleur du joueur courant. Etant donné qu'on avance de case en case, on ne peut pas se baser sur la case precedente pour determiner la couleur du joueur courant.
     * @param initPos La position initiale de notre piece
     * @param positionsIncrements La liste des increments de position possible pour une piece. Decrit le mouvement de celle-ci
     * @param possibleCaseList la liste a remplir avec des Case representant des mouvement possibles
     */
    protected void addAllLinearContinuousMove(Case[][] cases, PlayerEnum playerEnum, Position initPos, Position[] positionsIncrements, List<Case> possibleCaseList) {

        Thread[] threads = new Thread[positionsIncrements.length];

        // on lance chacune de 8 recursions dans un thread different
        for (int i = 0; i < positionsIncrements.length; i++) {

            Position curPosIncrement = positionsIncrements[i];

            threads[i] = new Thread(() -> addLinearContinuousMove(cases, playerEnum, initPos, curPosIncrement, possibleCaseList));
            threads[i].start();

            try{
                // on attend que tous les Threads soient terminées pour continuer
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("getPossibleCaseList -> thread interrupted");
            }
        }
    }


}
