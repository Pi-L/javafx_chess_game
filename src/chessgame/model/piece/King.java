package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class King extends Piece{
    public King(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public List<Case> getPossibleCaseList(Case[][] cases, Position position) {

        List<Position> possiblePositionList = new ArrayList<>();
        Case currentCase = cases[position.getX()][position.getY()];

        for (int i = 0; i <= 1 ; i++) {
            for (int j = 0; j <=1 ; j++) {

                if(i == 0 && j == 0) continue;

                Position tempPositionDown = new Position(position.getX() + i, position.getY() + j);
                Position tempPositionDiagDown = new Position(position.getX() - i, position.getY() + j);
                Position tempPositionDiagUp = new Position(position.getX() + i, position.getY() - j);
                Position tempPositionUp = new Position(position.getX() - i, position.getY() - j);

                possiblePositionList.add(tempPositionDiagDown);
                possiblePositionList.add(tempPositionDiagUp);
                possiblePositionList.add(tempPositionUp);
                possiblePositionList.add(tempPositionDown);
            }
        }

        List<Position> possiblePositionListFiltered = possiblePositionList.stream().distinct().filter(pos -> !pos.isOutOfBound()).collect(Collectors.toList());

        return possiblePositionListFiltered.stream()
                .filter(pos -> !currentCase.isSamePlayer(cases[pos.getX()][pos.getY()]))
                .map(pos -> cases[pos.getX()][pos.getY()])
                .collect(Collectors.toList());
    }
}
