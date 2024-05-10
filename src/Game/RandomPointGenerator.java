package Game;

import Entities.Entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;

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
        var x = _random.nextInt(_maxX);
        var y = _random.nextInt(_maxY);

        return new Point(x, y);
    }

    public Point pickRandomPointExcept(Rectangle rectangle) {
        return tryRandomizeWithConstraint(rectangle, (p, rect) -> !rect.contains(p))
                .orElse(null);
    }

    public Point pickRandomPointExcept(Point point) {
        return pickRandomPointExcept(new Rectangle(point.x, point.y, 1, 1));
    }

    public Point pickRandomPointExcept(List<Entity> existingEntities) {

        return tryRandomizeWithConstraint(existingEntities, this::doesntCollideWithEntities)
                .orElseThrow();
    }

    public List<Point> pickRandomPointsExcept(int numberOfPoints, Rectangle rectangle) {
        var points = new ArrayList<Point>();

        for (var i = 0; i < numberOfPoints; i++) {
            tryRandomizeWithConstraint(rectangle, (p, rect) -> !rect.contains(p) && !points.contains(p))
                .ifPresent(points::add);
        }

        return points;
    }

    private void randomizePoint(Point point) {
        point.x = _random.nextInt(_maxX);
        point.y = _random.nextInt(_maxY);
    }

    private <TConstraint> Optional<Point> tryRandomizeWithConstraint(TConstraint constraint, BiPredicate<Point, TConstraint> predicate) {
        var point = new Point();
        for (var i = 0; i < MaxAttemptsPerPoint; i++) {
            randomizePoint(point);
            if (predicate.test(point, constraint)) {
                return Optional.of(point);
            }
        }

        return Optional.empty();
    }

    private boolean doesntCollideWithEntities(Point point, List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.isColliding(point)) {
                return false;
            }
        }
        return true;
    }
}
