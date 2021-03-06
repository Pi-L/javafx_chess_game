package chessgame.controller;

import chessgame.model.Case;
import chessgame.model.Partie;
import chessgame.model.piece.Piece;
import chessgame.utils.Constants;
import chessgame.utils.GameStatusEnum;
import chessgame.utils.Position;
import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Roles :
 * - Initialisation et actualisation de l'affichage
 * - Gestion des evenements
 *
 * doit avoir accès à :
 *  - partie
 *  - partie reset
 *  - image path
 *  - select piece
 *  - select move
 *  - coordinates possible moves
 *  - undo move
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane anchorPaneMain;

    @FXML
    private Label mainTitle;

    @FXML
    private Pane case00, case10, case20, case30, case40, case50, case60, case70, case01,
                    case11, case21, case31, case41, case51, case61, case71,
                    case02, case12, case22, case32, case42, case52, case62, case72,
                    case03, case13, case23, case33, case43, case53, case63, case73,
                    case04, case14, case24, case34, case44, case54, case64, case74,
                    case05, case15, case25, case35, case45, case55, case65, case75,
                    case06, case16, case26, case36, case46, case56, case66, case76,
                    case07, case17, case27, case37, case47, case57, case67, case77;

    @FXML
    private Button buttonReset, buttonRestart, buttonExit;

    @FXML
    private Label currentPlayer;

    @FXML
    private Label labelIsChecked;

    @FXML
    private Label endGame;

    private Pane[][] panes;

    private Partie partie;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partie = new Partie();

        mainTitle.setFont(new Font(20.0));

        initPanes();

        initButtonsEvents();

        initCasePanesEvents();

        currentPlayer.textProperty().setValue(partie.getCurrentPlayer().getName());

    }

    private void initPanes() {

        // idee Leonie - pour ne pas avoir a constituer le tableau manuellement:
       // this.getClass().getDeclaredField("fieldName");

        // on met tous les Pane dans un tableau correspondant à celui dans la classe Plateau
        panes = new Pane[][] {
                {case00, case01, case02, case03, case04, case05, case06, case07},
                {case10, case11, case12, case13, case14, case15, case16, case17},
                {case20, case21, case22, case23, case24, case25, case26, case27},
                {case30, case31, case32, case33, case34, case35, case36, case37},
                {case40, case41, case42, case43, case44, case45, case46, case47},
                {case50, case51, case52, case53, case54, case55, case56, case57},
                {case60, case61, case62, case63, case64, case65, case66, case67},
                {case70, case71, case72, case73, case74, case75, case76, case77}
        };

        // set initial pane color
        for (int i = 0; i < panes.length; i++) {
            for (int j = 0; j < panes[i].length; j++) {

                Pane currentPane = panes[i][j];

                if ((i + j) % 2 == 0) currentPane.setStyle(Constants.PANE_BG_EVEN_COLOR_STYLE + Constants.PANE_BORDER_COLOR_STYLE);
                else currentPane.setStyle(Constants.PANE_BG_ODD_COLOR_STYLE + Constants.PANE_BORDER_COLOR_STYLE);
            }
        }
    }

    private void newGame() {
        partie.newGame();

        endGame.setStyle("");
        endGame.textProperty().setValue("");

        refreshView();
    }

    /**
     * <p>Actualisation de la vue</p>
     * <p>On reparcours toutes les cases à chaque fois pour recuperer leur état</p>
     */
    private void refreshView() {
        for (int i = 0; i < panes.length; i++) {
            for (int j = 0; j < panes[i].length; j++) {

                Position position = new Position(i, j);
                Pane currentPane = panes[i][j];

                // on initialise la couleur du bg de chaque case (le damier)
                if((i + j) % 2 == 0) currentPane.setStyle(Constants.PANE_BG_EVEN_COLOR_STYLE+Constants.PANE_BORDER_COLOR_STYLE);
                else currentPane.setStyle(Constants.PANE_BG_ODD_COLOR_STYLE+Constants.PANE_BORDER_COLOR_STYLE);

                // Re-initialisation de la couleur de bg en fonction des cas particulier selectionnable / selectionné
                // todo: refacto/opti
                try {
                    if(partie.isCaseSelected(position)) currentPane.setStyle(Constants.PANE_BG_SELECTED_COLOR_STYLE+Constants.PANE_BORDER_COLOR_STYLE);

                    if(partie.isCaseSelectable(position)) currentPane.setStyle(Constants.PANE_BG_SELECTABLE_COLOR_STYLE+Constants.PANE_BORDER_COLOR_STYLE);

                } catch (IllegalArgumentException e) {
                    System.out.println("GameController -> refresh() : "+ e.toString());
                }

                // s'il y a un piece sur la case, on peut recuperer son image
                try {
                    String imagePath = partie.getPieceImagePath(position);
                    String imagePathURI = new File(imagePath).toURI().toString();
                    ImageView imageView = new ImageView(imagePathURI);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);

                    currentPane.getChildren().clear();

                    currentPane.getChildren().add(imageView);

                } catch(IllegalArgumentException e) {
                    // sinon une exception est levée et on peut supprimer les images "enfant" presente sur la case
                    currentPane.getChildren().clear();
                }
            }
        }

        currentPlayer.textProperty().setValue(partie.getCurrentPlayer().getName());

        boolean isGameOver = GameStatusEnum.ENDED.equals(partie.getGameStatusEnum());

        if(isGameOver || partie.isCurrentPlayerKingChecked()) {
            labelIsChecked.setTextFill(Color.web("#FFFFFF"));
            labelIsChecked.setFont(new Font(15.0));
            labelIsChecked.setStyle(Constants.PANE_BG_SELECTED_COLOR_STYLE+Constants.PANE_BORDER_COLOR_STYLE);

        } else {
            labelIsChecked.setStyle("");
        }

        if(partie.isCurrentPlayerKingChecked()) {
            labelIsChecked.textProperty().setValue("Echec !");
        }

        if(isGameOver) {
            endGame.setTextFill(Color.web("#FFFFFF"));
            endGame.setFont(new Font(17.0));
            endGame.setStyle(Constants.PANE_BG_SELECTABLE_COLOR_STYLE+Constants.PANE_BORDER_COLOR_STYLE);
            endGame.textProperty().setValue("Partie Terminée \n"
                                            +"Victoire : "+partie.getCurrentPlayer().getOpposit().getName() +" !!");

            labelIsChecked.textProperty().setValue("Echec et Mat !");
        }
    }

    /**
     * Initialisation de l'evenement "click" sur chaque bouton
     */
    private void initButtonsEvents() {
        buttonExit.setOnMouseClicked(event ->  System.exit(0));

        buttonRestart.setOnMouseClicked(event -> newGame());

        buttonReset.setOnMouseClicked(event -> {
            if(partie == null || !partie.getGameStatusEnum().equals(GameStatusEnum.STARTED)) return;

            try {
                partie.resetMove();
                refreshView();

            } catch (Exception e) { }
        });
    }

    /**
     * <p>initialisation de l'evenement click sur chaque case du tableau</p>
     */
    private void initCasePanesEvents() {
        for (int i = 0; i < panes.length; i++) {
            for (int j = 0; j < panes[i].length; j++) {

                Pane currentPane = panes[i][j];

                currentPane.setOnMouseClicked(event -> {

                    if(partie == null || !GameStatusEnum.STARTED.equals(partie.getGameStatusEnum())) return;

                    Position position = panePosition(currentPane);

                    try {
                        partie.select(position);
                        refreshView();

                    } catch (IllegalArgumentException e) {
                        System.out.println("Bad selection");
                    }
                });
            }
        }
    }

    /**
     *
     * @param pane
     * @return la position d'un pane dans la grille en fonction de son nom
     */
    private Position panePosition(Pane pane) {
        Position pos = new Position();

        String id = pane.getId();
        pos.setX(Integer.parseInt(id.substring(4,5)));
        pos.setY(Integer.parseInt(id.substring(5,6)));

        return pos;
    }

}
