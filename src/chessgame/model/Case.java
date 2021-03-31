package chessgame.model;

public class Case {

    private final int coordinateX;
    private final int coordinateY;

    private String color;

    private Piece piece;

    public Case(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

        setColor();
    }


    private void setColor() {
        if((coordinateX + coordinateY % 2) == 0) color = "#ffffff";
        else color = "#f2f2f2";
    }

    public String getColor() {
        return color;
    }
}
