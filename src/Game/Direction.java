package Game;

import Utils.SharedRandom;

import java.awt.Point;
import java.util.function.Predicate;

/**
 * The Direction enum represents cardinal directions in a two-dimensional space.
 */
public enum Direction {
    Up,
    Down,
    Left,
    Right;

    /**
     * Converts an integer value to a Direction enum.
     *
     * @param value The integer value representing the direction (0 for Up, 1 for Down, 2 for Left, 3 for Right).
     * @return The corresponding Direction enum.
     * @throws IllegalStateException If the integer value is unexpected.
     */
    public static Direction fromInt(int value) {
        return switch (value) {
            case 0 -> Direction.Up;
            case 1 -> Direction.Down;
            case 2 -> Direction.Left;
            case 3 -> Direction.Right;
            default -> throw new IllegalStateException("Unexpected value" + value);
        };
    }

    /**
     * Translates a point by one unit in the direction.
     *
     * @param point The point to translate.
     * @return The translated point.
     */
    public Point translate(Point point) {
        return switch (this) {
            case Up -> new Point(point.x, point.y - 1);
            case Down -> new Point(point.x, point.y + 1);
            case Left -> new Point(point.x - 1, point.y);
            case Right -> new Point(point.x + 1, point.y);
        };
    }

    /**
     * Gets the angle in degrees of the direction.
     *
     * @return The angle in degrees.
     */
    public float getDegrees() {
        return switch (this) {
            case Up -> 0;
            case Down -> 180;
            case Left -> 270;
            case Right -> 90;
        };
    }

    /**
     * Gets a random valid direction based on the provided predicate.
     *
     * @param predicate The predicate to validate the direction.
     * @return A random valid direction.
     */
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

    /**
     * Gets the direction relative to a reference point.
     *
     * @param relativeTo The reference point.
     * @param other      The point to get the direction to.
     * @return The direction relative to the reference point.
     */
    public static Direction getDirectionRelativeTo(Point relativeTo, Point other) {
        var dx = other.x - relativeTo.x;
        var dy = other.y - relativeTo.y;

        return Math.abs(dx) > Math.abs(dy)
                ? (dx > 0 ? Direction.Right : Direction.Left)
                : (dy > 0 ? Direction.Down : Direction.Up);
    }

    /**
     * Gets the angle in radians of the direction.
     *
     * @return The angle in radians.
     */
    public double getRadians() {
        return Math.toRadians(getDegrees());
    }
}
