package Game;

/**
 * The SnakeColor enum represents the colors available for snakes in the game.
 */
public enum SnakeColor {

    /**
     * Red color for snakes.
     */
    Red("Red"),

    /**
     * Green color for snakes.
     */
    Green("Green"),

    /**
     * Blue color for snakes.
     */
    Blue("Blue");

    private final String _value;

    /**
     * Constructs a new SnakeColor with the specified string value.
     *
     * @param stringValue The string value representing the color.
     */
    SnakeColor(String stringValue) {
        _value = stringValue;
    }

    /**
     * Gets the string value of the SnakeColor.
     *
     * @return The string value of the SnakeColor.
     */
    public String getValue() {
        return _value;
    }
}
