package chessgame.model.piece;

import chessgame.model.Case;
import chessgame.utils.Constants;
import chessgame.utils.PlayerEnum;
import chessgame.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {


    public Pawn(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public List<Case> getPossibleCaseList(Case[][] cases, Position position) {

        List<Case> possibleCaseList = new ArrayList<>();
        Case currentCase = cases[position.getX()][position.getY()];

        int yFactor = PlayerEnum.BLACK.equals(playerEnum) ? 1 : -1;

        // base move 1 front square
        Position baseMove = new Position(position.getX(), position.getY() + yFactor);

        if(!baseMove.isOutOfBound()) {
            Case baseMoveCase = cases[baseMove.getX()][baseMove.getY()];

            if(baseMoveCase.getPiece() == null) possibleCaseList.add(baseMoveCase);
        }

        // initial 2 square jump move
        boolean hasMoved = PlayerEnum.BLACK.equals(playerEnum) ? position.getY() != 1 : position.getY() != Constants.GRID_SIDE_SIZE - 2;

        if(!hasMoved) {
            Position jumpPos = new Position(position.getX(), position.getY() + (yFactor * 2));

            Case jumpCase = cases[jumpPos.getX()][jumpPos.getY()];

            // 2eme condition pas très clair (si on est bloqué par une autre piece pour faire le jump) - todo: modify ?
            if(jumpCase.getPiece() == null && !possibleCaseList.isEmpty()) possibleCaseList.add(jumpCase);
        }

        // kill move
        int[] killPosAdjustementArray = new int[]{-1, 1};

        for (int killPosAdjustement : killPosAdjustementArray) {
            Position killPos = new Position(position.getX() + killPosAdjustement, position.getY() + yFactor);

            if(!killPos.isOutOfBound()) {
                Case killCase = cases[killPos.getX()][killPos.getY()];
                if(killCase.getPiece() != null && !currentCase.isSamePlayer(killCase)) possibleCaseList.add(killCase);
            }
        }

        possibleCaseList.forEach(aCase -> System.out.println(aCase));

        return possibleCaseList;
    }
}
