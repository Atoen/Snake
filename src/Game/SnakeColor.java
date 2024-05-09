package Game;

public enum SnakeColor {
    Red("Red"),
    Green("Green"),
    Blue("Blue");

    private final String _value;

    SnakeColor(String stringValue) {
        _value = stringValue;
    }

    public String getValue() {
        return _value;
    }
}

