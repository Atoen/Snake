package Entities;

import Game.Direction;
import Game.Grid;
import Game.SnakeColor;
import Game.SnakePart;
import Sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Snake class represents a snake entity in the game.
 * It implements the MovingEntity interface.
 */
public class Snake extends Entity implements MovingEntity {

    private final List<Part> _parts = new ArrayList<>();
    private final Part _head;
    private int _targetLength;

    /**
     * The direction the snake is moving in. Default is Up.
     */
    public Direction direction = Direction.Up;

    /**
     * Indicates if the snake is alive.
     */
    public boolean isAlive = true;

    /**
     * The color of the snake.
     */
    public final SnakeColor color;

    /**
     * Constructs a Snake at the specified position with the specified color and initial length.
     *
     * @param point         The initial position of the Snake.
     * @param color         The color of the Snake.
     * @param initialLength The initial length of the Snake.
     */
    public Snake(Point point, SnakeColor color, Integer initialLength) {
        super(point);
        this.color = color;
        _targetLength = initialLength;
        _head = new Part(point);
        _parts.add(_head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getPosition() {
        return _head.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        _head.direction = direction;

        if (_parts.size() < _targetLength) {
            growOne();
        }

        for (int i = _parts.size() - 1; i >= 1; i--) {
            var currentPart = _parts.get(i);
            var previousPart = _parts.get(i - 1);

            currentPart.position = new Point(previousPart.position);
            currentPart.direction = previousPart.direction;
        }

        switch (direction) {
            case Up -> _head.position.y--;
            case Down -> _head.position.y++;
            case Left -> _head.position.x--;
            case Right -> _head.position.x++;
        }
    }

    /**
     * Checks if the next direction is valid.
     *
     * @param nextDirection The next direction to check.
     * @return True if the next direction is valid, otherwise false.
     */
    public boolean isNextDirectionValid(Direction nextDirection) {
        return switch (nextDirection) {
            case Up -> direction != Direction.Down;
            case Down -> direction != Direction.Up;
            case Left -> direction != Direction.Right;
            case Right -> direction != Direction.Left;
            case null -> false;
        };
    }

    /**
     * Sets the direction of the snake.
     *
     * @param nextDirection The new direction of the snake.
     */
    public void setDirection(Direction nextDirection) {
        if (isNextDirectionValid(nextDirection)) {
            direction = nextDirection;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isColliding(Point position) {
        for (var part : _parts) {
            if (part.position.equals(position)) return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Draw(Graphics g) {
        EntityManager.getGrid().markAsOccupied(_head.position, Grid.Obstacle);

        if (_parts.size() == 1) {
            SpriteManager.DrawSnakeSprite(g, color, SnakePart.Head0, _head.position, _head.direction);
            return;
        }

        SpriteManager.DrawSnakeSprite(g, color, SnakePart.Head1, _head.position, _head.direction);

        for (int i = 1; i < _parts.size() - 1; i++) {
            var part = _parts.get(i);
            EntityManager.getGrid().markAsOccupied(part.position, Grid.Obstacle);
            var nextDirection = _parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                SpriteManager.DrawSnakeSprite(g, color, SnakePart.Body, part.position, part.direction);
            } else {
                SpriteManager.DrawSprite(g, getTurnImage(part.direction, nextDirection), part.position);
            }
        }

        var tail = _parts.getLast();
        SpriteManager.DrawSnakeSprite(g, color, SnakePart.Tail, tail.position, tail.direction);
        EntityManager.getGrid().markAsOccupied(tail.position, Grid.Obstacle);
    }

    /**
     * Gets the image for a turn in the snake's body.
     *
     * @param current The current direction.
     * @param next    The next direction.
     * @return The image representing the turn.
     */
    private Image getTurnImage(Direction current, Direction next) {
        var part = switch (current) {
            case Up -> next == Direction.Left ? SnakePart.Turn3 : SnakePart.Turn2;
            case Down -> next == Direction.Left ? SnakePart.Turn4 : SnakePart.Turn1;
            case Left -> next == Direction.Down ? SnakePart.Turn2 : SnakePart.Turn1;
            case Right -> next == Direction.Down ? SnakePart.Turn3 : SnakePart.Turn4;
        };

        return SpriteManager.getSnakeSprite(color, part);
    }

    /**
     * Increases the target length of the snake.
     *
     * @param length The amount to increase the target length by.
     */
    public void grow(int length) {
        _targetLength += length;
    }

    /**
     * Grows the snake by one part.
     */
    private void growOne() {
        var tail = _parts.getLast();
        var newPosition = new Point(tail.position);
        var newDirection = tail.direction;

        var newPart = new Part(newPosition);
        newPart.direction = newDirection;

        _parts.add(newPart);
    }

    /**
     * The Part class represents a part of the snake's body.
     */
    public static class Part {
        /**
         * The direction the part is facing. Default is Up.
         */
        public Direction direction = Direction.Up;

        /**
         * The position of the part.
         */
        public Point position;

        /**
         * Constructs a Part at the specified position.
         *
         * @param position The position of the Part.
         */
        public Part(Point position) {
            this.position = position;
        }
    }
}
