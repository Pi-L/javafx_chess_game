package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public List<Case> getPossibleCaseList(Case[][] cases, Position position) {
        List<Case> possibleCaseList = new ArrayList<>();

        Case currentCase = cases[position.getX()][position.getY()];

        int[] possibleModifiers = new int[] {-2, -1, 1, 2};

        for (int x : possibleModifiers) {
            for (int y : possibleModifiers) {
                if(Math.abs(x) == Math.abs(y)) continue;

                Position movePosition = new Position(position.getX() + x, position.getY() + y);
                if(movePosition.isOutOfBound()) continue;

                Case moveCase = cases[movePosition.getX()][movePosition.getY()];
                if(currentCase.isSamePlayer(moveCase)) continue;

                possibleCaseList.add(moveCase);
            }
        }

        return possibleCaseList;
    }
}
