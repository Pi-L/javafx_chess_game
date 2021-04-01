package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {


    public Rook(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public List<Case> getPossibleCaseList(Case[][] cases, Position position) {
        List<Case> possibleCaseList = new ArrayList<>();


        return possibleCaseList;
    }
}
