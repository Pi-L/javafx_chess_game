package chessgame.model.piece;

import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {


    public Rook(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public List<Position> getPossiblePositionList(Position position) {
        List<Position> possiblePositionList = new ArrayList<>();


        return possiblePositionList;
    }
}
