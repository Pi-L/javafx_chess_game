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

        Thread thread0 = new Thread(() -> addLinearContinuousMove(cases, playerEnum, position, new Position(1, 1), possibleCaseList));
        Thread thread1 = new Thread(() -> addLinearContinuousMove(cases, playerEnum, position, new Position(-1, -1), possibleCaseList));
        Thread thread2 = new Thread(() -> addLinearContinuousMove(cases, playerEnum, position, new Position(-1, 1), possibleCaseList));
        Thread thread3 = new Thread(() -> addLinearContinuousMove(cases, playerEnum, position, new Position(1, -1), possibleCaseList));

        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread0.join();
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("getPossibleCaseList -> thread interrupted");
        }

        return possibleCaseList;
    }
}
