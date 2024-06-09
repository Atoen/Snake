package Entities;

import Game.Direction;
import Game.SnakeColor;

import java.awt.*;
import java.util.Comparator;

/**
 * The AISnake class represents an AI-controlled snake entity in the game.
 * It extends the Snake class and adds AI behavior for calculating the next direction to move.
 */
public class AISnake extends Snake {

    /**
     * Constructs an AISnake at the specified position with the specified color and initial length.
     *
     * @param point         The initial position of the AISnake.
     * @param color         The color of the AISnake.
     * @param initialLength The initial length of the AISnake.
     */
    public AISnake(Point point, SnakeColor color, Integer initialLength) {
        super(point, color, initialLength);
    }

    /**
     * Calculates the next direction for the AI snake to move.
     * The AI targets the closest ScoreEntity and moves towards it.
     * If no target is found, it moves in a random valid direction.
     */
    public void CalculateNextDirection() {
        var closestTarget = EntityManager.findEntitiesImplementing(ScoreEntity.class)
                .stream()
                .min(Comparator.comparingDouble(this::distanceTo))
                .orElse(null);

        direction = closestTarget != null
                ? getNextDirection(getPosition(), closestTarget.getPosition())
                : Direction.getRandomValidDirection(this::directionConstraint);

        if (direction == null) {
            isAlive = false;
        }
    }

    /**
     * Determines the next direction to move from the current position to the target position.
     *
     * @param currentPosition The current position of the AISnake.
     * @param targetPosition  The target position of the AISnake.
     * @return The next direction to move.
     */
    private Direction getNextDirection(Point currentPosition, Point targetPosition) {
        var direction = Direction.getDirectionRelativeTo(currentPosition, targetPosition);
        return directionConstraint(direction)
                ? direction
                : Direction.getRandomValidDirection(this::directionConstraint);
    }

    /**
     * Checks if the specified direction is valid for the AISnake to move.
     *
     * @param direction The direction to check.
     * @return True if the direction is valid, otherwise false.
     */
    private boolean directionConstraint(Direction direction) {
        if (!isNextDirectionValid(direction)) return false;
        var newPosition = direction.translate(getPosition());
        return EntityManager.getGrid().isValidPosition(newPosition, true);
    }
}
