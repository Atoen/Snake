package Game;

import Utils.SharedRandom;

import java.awt.Point;
import java.util.function.Predicate;

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

    public static Direction getRandomValidDirection(Predicate<Direction> predicate) {
        var index = SharedRandom.nextInt(4);
        for (var i = 0; i < 4; i++) {
            var rotatedIndex = (index + i) % 4;
            var direction = Direction.fromInt(rotatedIndex);

            if (predicate.test(direction)) {
                return direction;
            }
        }

        return null;
    }

    public static Direction getDirectionRelativeTo(Point relativeTo, Point other) {
        var dx = other.x - relativeTo.x;
        var dy = other.y - relativeTo.y;

        return Math.abs(dx) > Math.abs(dy)
               ? (dx > 0 ? Direction.Right : Direction.Left)
               : (dy > 0 ? Direction.Down : Direction.Up);
    }

    public double getRadians() {
        return Math.toRadians(getDegrees());
    }
}
