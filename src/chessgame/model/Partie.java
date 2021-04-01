package chessgame.model;

import chessgame.model.piece.Piece;
import chessgame.utils.GameStatusEnum;
import chessgame.utils.PlayerEnum;

import java.util.ArrayList;
import java.util.List;

public class Partie {

    // le plateau est l'etat au temps "t" de la partie
    private Plateau plateau;

    // historique des deplacements
    private List<Deplacement> deplacementList;

    private PlayerEnum currentPlayer;

    private GameStatusEnum gameStatusEnum;



    public Partie() {
        super();
        gameStatusEnum = GameStatusEnum.IDLE;
        currentPlayer = PlayerEnum.NONE;
    }

    public void newGame() {
        plateau = new Plateau();
        deplacementList = new ArrayList<>();
        currentPlayer  = PlayerEnum.WHITE;
        gameStatusEnum = GameStatusEnum.STARTED;
    }

    // only one needed
    public void selectCase(int x, int y) throws IllegalArgumentException {
        if(!gameStatusEnum.equals(GameStatusEnum.STARTED)) throw new IllegalArgumentException();

        Case currentCase = plateau.getCase(x, y);

        boolean isSelectionPossible = isSelectionPossible(currentCase);
        boolean isMovePossible = isMovePossible(currentCase);

        if(!isSelectionPossible && !isMovePossible) throw new IllegalArgumentException();

        if(isSelectionPossible) {
            boolean isCurrentCaseSelected = currentCase.isSelected();

            if(!isCurrentCaseSelected) {
                Deplacement deplacement = new Deplacement();
                deplacement.setStartCase(currentCase);
                deplacementList.add(deplacement);
            }

            if(isCurrentCaseSelected) deplacementList.remove(deplacementList.size() - 1);

            currentCase.setSelected(!isCurrentCaseSelected);
            return;
        }

        // if isMovePossible
        makeMove(currentCase);
    }

    public String getPieceImagePath(int x, int y) throws IllegalArgumentException {
        if(!gameStatusEnum.equals(GameStatusEnum.STARTED)) throw new IllegalArgumentException();

        Piece piece = plateau.getPiece(x, y);
        return piece.getImagePath();
    }


    public PlayerEnum getCurrentPlayer() {
       return currentPlayer;
    }

    public void resetMove() throws Exception {
        if(!gameStatusEnum.equals(GameStatusEnum.STARTED) || deplacementList == null || deplacementList.isEmpty()) throw new Exception();

        Deplacement lastDeplacement = deplacementList.get(deplacementList.size() - 1);

        // si on annule le dernier deplacmeent alors qu'on a juste fait une selection, on deselectionne le pion,
        // sinon on change le joueur et on defait le deplacement
           if(lastDeplacement.getStartCase() != null && lastDeplacement.getEndCase() == null) {
            lastDeplacement.getStartCase().setSelected(false);

        } else  {
            togglePlayer();
            lastDeplacement.undo();
        }

        deplacementList.remove(lastDeplacement);
    }

    // valeur necessaire pour l'affichage de la surbrillance
    public boolean isCaseSelected(int x, int y) throws IllegalArgumentException {
        Case currentCase = plateau.getCase(x, y);

        return currentCase.isSelected();
    }

    // valeur necessaire pour l'affichage de la surbrillance
    public boolean isCaseSelectable(int x, int y) throws IllegalArgumentException {
        Case currentCase = plateau.getCase(x, y);

        return currentCase.isSelectable();
    }

    public GameStatusEnum getGameStatusEnum() {
        return gameStatusEnum;
    }

    private void togglePlayer() {
        if(currentPlayer.equals(PlayerEnum.BLACK)) currentPlayer = PlayerEnum.WHITE;
        else currentPlayer = PlayerEnum.BLACK;
    }

    private void makeMove(Case pCase) {
        Deplacement lastDeplacement = deplacementList.get(deplacementList.size() - 1);

        lastDeplacement.setEndCase(pCase);

        Case startCase = lastDeplacement.getStartCase();
        Case endCase = lastDeplacement.getEndCase();

        if(endCase.getPiece() != null) {
            lastDeplacement.setPieceCaptured(endCase.getPiece());

            if("King".equals(endCase.getPiece().getClass().getSimpleName())) {
                gameStatusEnum = GameStatusEnum.ENDED;
            }
        }


        endCase.setPiece(lastDeplacement.getPiece());
        startCase.setSelected(false);
        startCase.setPiece(null);

        if(GameStatusEnum.STARTED.equals(gameStatusEnum)) togglePlayer();
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

        return true;
    }
}
