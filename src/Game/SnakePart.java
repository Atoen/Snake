package Game;

/**
 * The SnakePart enum represents different parts of a snake sprite.
 */
public enum SnakePart {

    /**
     * Represents the first head sprite of a snake.
     */
    Head0("head0"),

    /**
     * Represents the second head sprite of a snake.
     */
    Head1("head1"),

    /**
     * Represents the body turn sprite 1 of a snake.
     */
    Turn1("bodyturn1"),

    /**
     * Represents the body turn sprite 2 of a snake.
     */
    Turn2("bodyturn2"),

    /**
     * Represents the body turn sprite 3 of a snake.
     */
    Turn3("bodyturn3"),

    /**
     * Represents the body turn sprite 4 of a snake.
     */
    Turn4("bodyturn4"),

    /**
     * Represents the body sprite of a snake.
     */
    Body("body"),

    /**
     * Represents the tail sprite of a snake.
     */
    Tail("tail");

    private final String _value;

    /**
     * Constructs a new SnakePart with the specified string value.
     *
     * @param stringValue The string value representing the snake part.
     */
    SnakePart(String stringValue) {
        this._value = stringValue;
    }

    /**
     * Gets the string value of the SnakePart.
     *
     * @return The string value of the SnakePart.
     */
    public String getValue() {
        return _value;
    }
}
