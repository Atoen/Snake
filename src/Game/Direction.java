package Game;

import java.awt.Point;

public enum Direction {
    Up,
    Down,
    Left,
    Right;

    public static Direction fromInt(int value) {
        return switch (value) {
            case 0 -> Direction.Up;
            case 1 -> Direction.Down;
            case 2 -> Direction.Left;
            case 3 -> Direction.Right;
            default -> throw new IllegalStateException(STR."Unexpected value: \{value}");
        };
    }

    public Point translate(Point point) {
        return switch (this) {
            case Up -> new Point(point.x, point.y - 1);
            case Down -> new Point(point.x, point.y + 1);
            case Left -> new Point(point.x - 1, point.y);
            case Right -> new Point(point.x + 1, point.y);
        };
    }

    public float getDegrees() {
        return switch (this) {
            case Up -> 0;
            case Down -> 180;
            case Left -> 270;
            case Right -> 90;
        };
    }

    public double getRadians() {
        return Math.toRadians(getDegrees());
    }
}
