package Model;

public enum Color{
    CYAN, //OOFFFF
    PINK, //FF63C7
    BURGUNDY, //800020
    HONEY, //EBA937
    ULTRAMARINE; //0437F2

    /**
     * @param color The color in the Color enum
     * @return A java.awt.Color Object, matching the color in the enum
     */
    public static java.awt.Color getColor(Color color) {
        return switch (color) {
            case CYAN -> new java.awt.Color(0, 255, 255);
            case PINK -> new java.awt.Color(255, 99, 199);
            case HONEY -> new java.awt.Color(235, 169, 55);
            case BURGUNDY -> new java.awt.Color(128, 0, 32);
            case ULTRAMARINE -> new java.awt.Color(4, 55, 242);
        };
    }
}


