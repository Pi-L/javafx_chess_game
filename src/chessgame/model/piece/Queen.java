package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {


    public Queen(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public List<Case> getPossibleCaseList(Case[][] cases, Position position) {
        List<Case> possibleCaseList = new ArrayList<>();
        PlayerEnum playerEnum = cases[position.getX()][position.getY()].getPiece().playerEnum;

        // tableau des increments de position possibles pour la reine
        Position[] positionsIncrements = new Position[] {
                                                    new Position(1, 1),
                                                    new Position(-1, -1),
                                                    new Position(-1, 1),
                                                    new Position(1, -1),
                                                    new Position(0, 1),
                                                    new Position(0, -1),
                                                    new Position(1, 0),
                                                    new Position(-1, 0)
                                                };

        Thread[] threads = new Thread[positionsIncrements.length];

        // on lance chacune de 8 recursions dans un thread different
        for (int i = 0; i < positionsIncrements.length; i++) {

            Position curPosIncrement = positionsIncrements[i];
            threads[i] = new Thread(() -> addLinearContinuousMove(cases, playerEnum, position, curPosIncrement, possibleCaseList));
            threads[i].start();

            try{
                // on attend que tous les Threads soient terminées pour continuer
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("getPossibleCaseList -> thread interrupted");
            }
        }

        return possibleCaseList;
    }
}
