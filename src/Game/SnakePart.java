package Game;

public enum SnakePart {
    Head0("head0"),
    Head1("head1"),
    Turn1("bodyturn1"),
    Turn2("bodyturn2"),
    Turn3("bodyturn3"),
    Turn4("bodyturn4"),
    Body("body"),
    Tail("tail");

    private final String _value;

    SnakePart(String stringValue) {
        this._value = stringValue;
    }

    public String getValue() {
        return _value;
    }
}
