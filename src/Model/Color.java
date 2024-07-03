package Model;

import java.io.Serializable;

public enum Color implements Serializable {
    BLUE,
    VIOLET,
    GREEN,
    PINK, //FF63C7
    HONEY; //EBA937

    /**
     * @param color The color in the Color enum
     * @return A java.awt.Color Object, matching the color in the enum
     */
    public static java.awt.Color getColor(Model.Color color) {
        return switch (color) {
            case GREEN -> new java.awt.Color(127, 186, 0);
            case PINK -> new java.awt.Color(255, 99, 199);
            case HONEY -> new java.awt.Color(235, 169, 55);
            case BLUE -> new java.awt.Color(0, 164, 239);
            case VIOLET -> new java.awt.Color(177, 58, 184);
        };
    }

    /**
     * Intended to be used on ordering lists of Color
     * @param color
     * @return The priority in the Color Hierarchy
     */
    public static int getColorPriority(Color color) {
        return switch (color) {
            case VIOLET -> 1;
            case PINK -> 2;
            case HONEY -> 3;
            case GREEN -> 4;
            case BLUE -> 5;
        };
    }
}


