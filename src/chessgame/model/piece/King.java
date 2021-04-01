package chessgame.model.piece;

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
    public List<Position> getPossiblePositionList(Position position) {

        List<Position> tempPossiblePositionList = new ArrayList<>();

        for (int i = 0; i <= 1 ; i++) {
            for (int j = 0; j <=1 ; j++) {

                if(i == 0 && j == 0) continue;

                Position tempPositionDown = new Position(position.getX() + i, position.getY() + j);
                Position tempPositionDiagDown = new Position(position.getX() - i, position.getY() + j);
                Position tempPositionDiagUp = new Position(position.getX() + i, position.getY() - j);
                Position tempPositionUp = new Position(position.getX() - i, position.getY() - j);

                tempPossiblePositionList.add(tempPositionDiagDown);
                tempPossiblePositionList.add(tempPositionDiagUp);
                tempPossiblePositionList.add(tempPositionUp);
                tempPossiblePositionList.add(tempPositionDown);
            }
        }

        return tempPossiblePositionList.stream().distinct().filter(pos -> !pos.isOutOfBound()).collect(Collectors.toList());
    }
}
