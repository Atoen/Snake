package Entities;

import Game.Direction;
import Game.SnakeColor;

import java.awt.*;
import java.util.Comparator;
import java.util.Random;

public class AISnake extends Snake {
    public AISnake(Point point, SnakeColor color, Integer initialLength) {
        super(point, color, initialLength);
    }
    
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

    private Direction getNextDirection(Point currentPosition, Point targetPosition) {
        var direction = Direction.getDirectionRelativeTo(currentPosition, targetPosition);
        return directionConstraint(direction)
               ? direction
               : Direction.getRandomValidDirection(this::directionConstraint);
    }

    private boolean directionConstraint(Direction direction) {
        if (!isNextDirectionValid(direction)) return false;
        var newPosition = direction.translate(getPosition());
        return EntityManager.getGrid().isValidPosition(newPosition, true);
    }
}
