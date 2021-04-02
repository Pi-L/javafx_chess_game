package chessgame.utils;

public enum PlayerEnum {
    NONE("Demarrer un partie!"),
    BLACK("Noirs"),
    WHITE("Blancs");

    private final String name;

    private PlayerEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PlayerEnum getOpposit() {

        return switch (this) {
            case BLACK -> PlayerEnum.WHITE;
            case WHITE -> PlayerEnum.BLACK;
            default -> PlayerEnum.NONE;
        };
    }
}
