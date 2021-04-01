package chessgame.model;

public enum PlayerEnum {
    BLACK("Noirs"),
    WHITE("Blancs");

    private final String name;

    private PlayerEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
