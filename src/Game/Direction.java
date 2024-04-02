package Game;

import java.awt.*;

public enum Direction {
    Up,
    Down,
    Left,
    Right;

    public Point translate(Point point) {
        return switch (this) {
            case Up -> new Point(point.x, point.y - 1);
            case Down -> new Point(point.x, point.y + 1);
            case Left -> new Point(point.x - 1, point.y);
            case Right -> new Point(point.x + 1, point.y);
        };
    }
}
