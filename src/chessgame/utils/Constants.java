package chessgame.utils;

public class Constants {

    public static final int GRID_SIDE_SIZE = 8;

    private static final String BACKGROUND_COLOR_STYLE = "-fx-background-color: %s; ";

    private static final String BORDER_COLOR_STYLE = "-fx-border-color: %s; ";

    public static final String PANE_BG_EVEN_COLOR_STYLE = String.format(BACKGROUND_COLOR_STYLE, "#FFFFFF");

    public static final String PANE_BG_ODD_COLOR_STYLE = String.format(BACKGROUND_COLOR_STYLE, "#E0E0E0");

    public static final String PANE_BG_SELECTED_COLOR_STYLE = String.format(BACKGROUND_COLOR_STYLE, "#bb4444");

    public static final String PANE_BG_SELECTABLE_COLOR_STYLE = String.format(BACKGROUND_COLOR_STYLE, "#bb4444");

    public static final String PANE_BORDER_COLOR_STYLE = String.format(BORDER_COLOR_STYLE, "#2f2f2f");

}
