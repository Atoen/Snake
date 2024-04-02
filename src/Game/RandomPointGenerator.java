package Game;

import Entities.Entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPointGenerator {

    private static final int MaxAttemptsPerPoint = 20;
    private final Random _random = new Random();
    private final int _maxX;
    private final int _maxY;

    public RandomPointGenerator(int maxX, int maxY) {
        _maxX = maxX;
        _maxY = maxY;
    }

    public Point pickRandomPoint() {
        final var x = _random.nextInt(_maxX);
        final var y = _random.nextInt(_maxY);

        return new Point(x, y);
    }

    public Point pickRandomPointExcept(Rectangle rectangle) {
        final var point = new Point();
        do {
            point.x = _random.nextInt(_maxX);
            point.y = _random.nextInt(_maxY);
        } while (rectangle.contains(point));

        return point;
    }

    public Point pickRandomPointExcept(Point point) {
        return pickRandomPointExcept(new Rectangle(point.x, point.y, 1, 1));
    }

    public Point pickRandomPointExcept(List<Entity> existingEntities) {
        final var point = new Point();
        var collides = false;

        do {
            point.x = _random.nextInt(_maxX);
            point.y = _random.nextInt(_maxY);

            collides = collidesWithEntities(point, existingEntities);
        } while (collides);

        return point;
    }

    public List<Point> pickRandomPointsExcept(int numberOfPoints, Rectangle rectangle) {
        final var points = new ArrayList<Point>();

        for (var i = 0; i < numberOfPoints; i++) {
            var attempt = 0;
            var valid = true;

            final var point = new Point();
            do {
                if (attempt++ > MaxAttemptsPerPoint) {
                    valid = false;
                    break;
                }

                point.x = _random.nextInt(_maxX);
                point.y = _random.nextInt(_maxY);
            } while (pointExists(point, points) || rectangle.contains(point));

            if (valid) {
                points.add(point);
            }
        }

        return points;
    }

    private boolean pointExists(Point point, List<Point> points) {
        for (Point p : points) {
            if (p.equals(point)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesWithEntities(Point point, List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.isColliding(point)) {
                return true;
            }
        }
        return false;
    }
}
