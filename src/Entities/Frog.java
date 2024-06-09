package Entities;

import Game.Direction;
import Sprites.SpriteManager;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Comparator;

/**
 * The Frog class represents a frog entity in the game.
 * It implements ScoreEntity and MovingEntity interfaces.
 */
public class Frog extends Entity implements ScoreEntity, MovingEntity {

    /**
     * Constructs a Frog at the specified position.
     *
     * @param point The initial position of the Frog.
     */
    public Frog(Point point) {
        super(point);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return 5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGrowLength() {
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

    /**
     * Moves the frog. The frog either avoids the nearest snake if it is close enough or moves randomly.
     */
    public void move() {
        var closestSnakeEntry = EntityManager.findEntitiesOfClass(Snake.class)
                .stream()
                .map(x -> new AbstractMap.SimpleEntry<>(x, distanceTo(x)))
                .min(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .orElse(null);

        if (closestSnakeEntry != null && closestSnakeEntry.getValue() < 8) {
            avoidSnake(closestSnakeEntry.getKey());
        } else {
            moveRandomly();
        }
    }

    /**
     * Avoids the nearest snake by moving in the opposite direction.
     *
     * @param snake The snake to avoid.
     */
    private void avoidSnake(Snake snake) {
        var avoidDirection = Direction.getDirectionRelativeTo(snake.getPosition(), getPosition());
        if (directionConstraint(avoidDirection)) {
            setPosition(avoidDirection.translate(getPosition()));
        } else {
            moveRandomly();
        }
    }

    /**
     * Moves the frog in a random valid direction.
     */
    private void moveRandomly() {
        var direction = Direction.getRandomValidDirection(this::directionConstraint);
        if (direction != null) {
            setPosition(direction.translate(getPosition()));
        }
    }

    /**
     * Checks if the given direction is valid for the frog to move.
     *
     * @param direction The direction to check.
     * @return True if the direction is valid, otherwise false.
     */
    private boolean directionConstraint(Direction direction) {
        var newPosition = direction.translate(getPosition());
        return EntityManager.getGrid().isValidPosition(newPosition, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getSprite() {
        return SpriteManager.getFrogSprite();
    }
}
