package chessgame.model;

import chessgame.model.piece.King;
import chessgame.model.piece.Piece;
import chessgame.utils.GameStatusEnum;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Partie {

    // le plateau est l'etat au temps "t" de la partie
    private Plateau plateau;

    // historique des deplacements
    private List<Deplacement> deplacementList;

    // enum decrivant le joueur dont c'est le tour
    private PlayerEnum currentPlayer;

    // l'etat du jeu (attente, demarré, fini)
    private GameStatusEnum gameStatusEnum;

    // utile le reset. Permet de ne pas tout parcourir
    private List<Case> caseSelectableList;

    private boolean isCurrentPlayerKingChecked;

    /**
     * <p>On ne creait pas une nouvelle partie à l'instanciation</p>
     * <p>Seul le statut de la partie et celui du joueur sont initialisés</p>
     * <p>Il faudra appeler newGame() pour initialiser une partie</p>
     */
    public Partie() {
        super();
        gameStatusEnum = GameStatusEnum.IDLE;
        currentPlayer = PlayerEnum.NONE;
    }

    /**
     * <p>Permet d'initialiser une nouvelle partie</p>
     * <p>Utiliser par le controller quand le joueur clique sur nouvelle partie</p>
     * <p>En instanciant un nouveau Plateau, on s'assure que l'etat de la partie est remi à zero si on a deja effectué une nouvelle partie avant</p>
     */
    public void newGame() {
        plateau = new Plateau();
        deplacementList = new ArrayList<>();
        currentPlayer  = PlayerEnum.WHITE;
        gameStatusEnum = GameStatusEnum.STARTED;

        cleanUpCaseSelectableList();
    }

    /**
     * <p>Methode centrale de la gestion du jeu</p>
     * <p>Gere la selection d'une Case</p>
     * <p>Gestion en fonction de l'etat de la partie, du plateau, du joueur courant, de la piece presente ou non sur la case</p>
     * @param position la position de la case selectionnée
     * @throws IllegalArgumentException si la case selectionnée est invalide en fonction des toutes les conditions du jeu
     */
    public void select(Position position) throws IllegalArgumentException {

        if(!gameStatusEnum.equals(GameStatusEnum.STARTED)) throw new IllegalArgumentException();

        Case currentCase = plateau.getCase(position);

        // une selection correspond au fait de choisir une piece à jouer
        boolean isSelectionPossible = isSelectionPossible(currentCase);

        // un mouvement correspond à deplacer une piece deja selectionnée
        boolean isMovePossible = isMovePossible(currentCase);

        if(!isSelectionPossible && !isMovePossible) throw new IllegalArgumentException();

        if(isSelectionPossible) {
            selectCase(currentCase);
            return;
        }

        // if isMovePossible (par elminination)
        makeMove(currentCase);

        if(GameStatusEnum.STARTED.equals(gameStatusEnum)) togglePlayer();
        setIsCurrentPlayerKingChecked();
        System.out.println(currentPlayer+ "  " +isCurrentPlayerKingChecked);

        if(isCurrentPlayerKingChecked) {
            List<Case> caseListCurrentPlayer = plateau.getPlayerCases(currentPlayer);
            long movebleCaseLeft = caseListCurrentPlayer.stream().filter(aCase -> !plateau.getPossibleMoveList(aCase).isEmpty()).count();
            if(movebleCaseLeft == 0.0) gameStatusEnum = GameStatusEnum.ENDED;
        }
    }

    /**
     *
     * @param position une position sur le plateau
     * @return le chemin du fichier image d'une piece
     * @throws IllegalArgumentException si la position n'est pas sur le plateau ou qu'il n'y a pas de piece sur cette case
     */
    public String getPieceImagePath(Position position) throws IllegalArgumentException {
        if(GameStatusEnum.IDLE.equals(gameStatusEnum)) throw new IllegalArgumentException();

        Piece piece = plateau.getPiece(position);
        return piece.getImagePath();
    }


    public PlayerEnum getCurrentPlayer() {
       return currentPlayer;
    }

    /**
     * Annule le dernier deplacement
     * @throws Exception s'il n'y a pas de deplacement ou que la partie n'a pas demarrée
     */
    public void resetMove() throws Exception {
        if(!gameStatusEnum.equals(GameStatusEnum.STARTED) || deplacementList == null || deplacementList.isEmpty()) throw new Exception();

        Deplacement lastDeplacement = deplacementList.get(deplacementList.size() - 1);

        // si on annule le dernier deplacmeent alors qu'on a juste fait une selection, on deselectionne le pion,
        // sinon on change le joueur et on defait le deplacement
       if(lastDeplacement.getStartCase() != null && lastDeplacement.getEndCase() == null) {
            lastDeplacement.getStartCase().setSelected(false);
            cleanUpCaseSelectableList();

       } else  {
            togglePlayer();
            lastDeplacement.undo();
            // todo: verifier si ok
            isCurrentPlayerKingChecked = false;
        }

        deplacementList.remove(lastDeplacement);
    }

    /**
     * Renvoie si une case est selectionné. Utile lors dur refresh de la vue du plateau
     * @param position la position d'une case
     * @return boolean
     * @throws IllegalArgumentException si la position de correspond pas à une case du plateau
     */
    public boolean isCaseSelected(Position position) throws IllegalArgumentException {
        Case currentCase = plateau.getCase(position);

        return currentCase.isSelected();
    }

    /**
     * Renvoie si une case est selectionnable. Utile lors dur refresh de la vue du plateau
     * @param position la position d'une case
     * @return boolean
     * @throws IllegalArgumentException si la position de correspond pas à une case du plateau
     */
    public boolean isCaseSelectable(Position position) throws IllegalArgumentException {
        Case currentCase = plateau.getCase(position);

        return currentCase.isSelectable();
    }

    public GameStatusEnum getGameStatusEnum() {
        return gameStatusEnum;
    }

    public boolean isCurrentPlayerKingChecked() {
        return isCurrentPlayerKingChecked;
    }

    /**
     * Permet le changement de tour
     */
    private void togglePlayer() {
       currentPlayer = currentPlayer.getOpposit();
    }

    /**
     * <p>Gestion de la selection / deselection d'une piece</p>
     * <p>On creer un Deplacement au moment de la selection</p>
     * @param pCase une case contenant une piece (ceci etant verifié par isSelectionPossible())
     */
    private void selectCase(Case pCase) {
        boolean isCurrentCaseSelected = pCase.isSelected();

        // selection
        if(!isCurrentCaseSelected) {
            Deplacement deplacement = new Deplacement();
            deplacement.setStartCase(pCase);

            deplacementList.add(deplacement);

            caseSelectableList = plateau.getPossibleMoveList(pCase);
            caseSelectableList.forEach(aCase -> aCase.setSelectable(true));
        }

        // deselection
        if(isCurrentCaseSelected) {
            deplacementList.remove(deplacementList.size() - 1);
            cleanUpCaseSelectableList();
        }

        pCase.setSelected(!isCurrentCaseSelected);
    }

    /**
     * <p>Gestion du mouvement d'une piece</p>
     * @param pCase une case ou une piece peut etre deplacée (verifié par isMovePossible())
     */
    private void makeMove(Case pCase) {
        Deplacement lastDeplacement = deplacementList.get(deplacementList.size() - 1);

        cleanUpCaseSelectableList();

        lastDeplacement.setEndCase(pCase);

        Case startCase = lastDeplacement.getStartCase();
        Case endCase = lastDeplacement.getEndCase();

        if(endCase.getPiece() != null) {
            lastDeplacement.setPieceCaptured(endCase.getPiece());

//            if(endCase.getPiece() instanceof King) gameStatusEnum = GameStatusEnum.ENDED;
        }


        endCase.setPiece(lastDeplacement.getPiece());
        startCase.setSelected(false);
        startCase.setPiece(null);
    }

    private boolean isSelectionPossible(Case pCase) {
        Piece currentPiece = pCase.getPiece();

        // si on veut selectionner une case vide ou une piece pas de notre couleur : return false
        if(currentPiece == null || !currentPiece.getPlayerEnum().equals(currentPlayer)) return false;

        Deplacement deplacement = null;

        if(deplacementList != null && !deplacementList.isEmpty()) deplacement = deplacementList.get(deplacementList.size() - 1);

        // si une piece est deja selectionnée et qu'on cherche a selectionner une autre piece : return false
        if(deplacement != null
                && deplacement.getStartCase() != null && deplacement.getEndCase() == null
                && currentPiece != deplacement.getPiece()) return false;

        return true;
    }

    private boolean isMovePossible(Case pCase) {
        Piece currentPiece = pCase.getPiece();

        Deplacement deplacement = null;

        // si on veut selectionner une case non vide et de notre couleur : return false
        if(currentPiece != null && currentPiece.getPlayerEnum().equals(currentPlayer)) return false;

        if(deplacementList != null && !deplacementList.isEmpty()) deplacement = deplacementList.get(deplacementList.size() - 1);

        if(deplacement == null || deplacement.getEndCase() != null || deplacement.getStartCase() == null) return false;

        if(caseSelectableList == null || caseSelectableList.isEmpty() || !caseSelectableList.contains(pCase)) return false;


        return true;
    }

    /**
     * Vide la liste des cases ou une piece peut se deplacer et repasse à faux l'etat "selectionnable" de chacune de ces cases
     */
    private void cleanUpCaseSelectableList() {
        if(caseSelectableList == null) return;

        caseSelectableList.forEach(aCase -> aCase.setSelectable(false));
        caseSelectableList.clear();
    }

    private void setIsCurrentPlayerKingChecked() {
        List<Case> caseListCurrentPlayer = plateau.getPlayerCases(currentPlayer);
        List<Case> caseListOppositPlayer = plateau.getPlayerCases(currentPlayer.getOpposit());
        Case caseCurrentPlayerKing = plateau.getPlayerKing(caseListCurrentPlayer);

        List<Case> caseListOppositPlayerCheckingPieces = plateau.getCheckingPieceCaseList(caseListOppositPlayer, caseCurrentPlayerKing);

        isCurrentPlayerKingChecked = !caseListOppositPlayerCheckingPieces.isEmpty();
    }

}
