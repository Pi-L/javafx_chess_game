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

        switch (this) {
            case BLACK:
                return PlayerEnum.WHITE;

            case WHITE:
                return PlayerEnum.BLACK;

            default:
                return PlayerEnum.NONE;
        }
    }
}
