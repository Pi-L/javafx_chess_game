package chessgame.utils;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position() {
        super();
    }

    public Position(int x, int y) {
        super();

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOutOfBound() {
        return x < 0 || y < 0 || x > Constants.GRID_SIDE_SIZE - 1 || y > Constants.GRID_SIDE_SIZE -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
