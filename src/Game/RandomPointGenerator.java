package Game;

import Entities.Entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;

/**
 * The RandomPointGenerator class generates random points within specified constraints.
 */
public class RandomPointGenerator {

    /** The maximum number of attempts to generate a random point. */
    private static final int MaxAttemptsPerPoint = 20;
    private final Random _random = new Random();
    private final int _maxX;
    private final int _maxY;

    /**
     * Constructs a new RandomPointGenerator with the specified maximum x and y coordinates.
     *
     * @param maxX The maximum x-coordinate.
     * @param maxY The maximum y-coordinate.
     */
    public RandomPointGenerator(int maxX, int maxY) {
        _maxX = maxX;
        _maxY = maxY;
    }

    /**
     * Picks a random point that doesn't collide with existing entities.
     *
     * @param existingEntities The list of existing entities.
     * @return A random point.
     * @throws IllegalStateException If no valid point can be found after the maximum number of attempts.
     */
    public Point pickRandomPointExcept(List<Entity> existingEntities) {
        return tryRandomizeWithConstraint(existingEntities, this::doesntCollideWithEntities)
                .orElseThrow();
    }

    /**
     * Picks multiple random points that don't collide with a specified rectangle.
     *
     * @param numberOfPoints The number of random points to pick.
     * @param rectangle      The rectangle to avoid collisions with.
     * @return A list of random points.
     */
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

    /**
     * Tries to generate a random point that satisfies the given constraint.
     *
     * @param constraint The constraint to satisfy.
     * @param predicate  The predicate defining the constraint.
     * @param <TConstraint> The type of the constraint.
     * @return An optional containing the random point if found, or empty if no valid point can be generated.
     */
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

    /**
     * Checks if a given point doesn't collide with a list of entities.
     *
     * @param point    The point to check for collision.
     * @param entities The list of entities to check against.
     * @return True if the point doesn't collide with any entity, otherwise false.
     */
    private boolean doesntCollideWithEntities(Point point, List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.isColliding(point)) {
                return false;
            }
        }
        return true;
    }
}
