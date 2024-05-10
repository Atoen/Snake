package Entities;

import Game.Direction;
import Sprites.SpriteManager;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Comparator;

public class Frog extends Entity implements ScoreEntity, MovingEntity {
    public Frog(Point point) {
        super(point);
    }

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public int getGrowLength() {
        return 2;
    }

    @Override
    public void Draw(Graphics g) {
        super.Draw(g);
    }

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

    private void avoidSnake(Snake snake) {
        var avoidDirection = Direction.getDirectionRelativeTo(snake.getPosition(), getPosition());
        if (directionConstraint(avoidDirection)) {
            setPosition(avoidDirection.translate(getPosition()));
        } else {
            moveRandomly();
        }
    }

    private void moveRandomly() {
        var direction = Direction.getRandomValidDirection(this::directionConstraint);
        if (direction != null) {
            setPosition(direction.translate(getPosition()));
        }
    }

    private boolean directionConstraint(Direction direction) {
        var newPosition = direction.translate(getPosition());
        return EntityManager.getGrid().isValidPosition(newPosition, false);
    }

    @Override
    public Image getSprite() {
        return SpriteManager.getFrogSprite();
    }
}
