package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(PlayerEnum playerEnum) {
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
                new Position(1, -1)
        };

        addAllLinearContinuousMove(cases, playerEnum, position, positionsIncrements, possibleCaseList);

        return possibleCaseList;
    }
}
