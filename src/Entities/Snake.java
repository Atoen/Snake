package Entities;

import Game.Direction;
import Game.SnakeColor;
import Game.SnakePart;
import Sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends Entity implements MovingEntity {

    private final List<Part> _parts = new ArrayList<>();
    private final Part _head;

    private int _targetLength;

    public Direction direction = Direction.Up;
    public final SnakeColor color;

    public Snake(Point point, SnakeColor color, int initialLength) {
        super(point);

        this.color = color;
        _targetLength = initialLength;
        _head = new Part(point);
        _parts.add(_head);
    }

    @Override
    public Point getPosition() {
        return _head.position;
    }

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

    @Override
    public boolean isColliding(Entity entity) {
        return isColliding(entity.getPosition());
    }

    @Override
    public boolean isColliding(Point position) {
        for (var part : _parts) {
            if (part.position.equals(position)) return true;
        }

        return false;
    }

    @Override
    public void Draw(Graphics g) {
        EntityManager.getGrid().markAsOccupied(_head.position);

        if (_parts.size() == 1) {
            SpriteManager.DrawSnakeSprite(g, color, SnakePart.Head0, _head.position, _head.direction);
            return;
        }

        SpriteManager.DrawSnakeSprite(g, color, SnakePart.Head1, _head.position, _head.direction);

        for (int i = 1; i < _parts.size() - 1; i++) {
            var part = _parts.get(i);
            EntityManager.getGrid().markAsOccupied(part.position);
            var nextDirection = _parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                SpriteManager.DrawSnakeSprite(g, color, SnakePart.Body, part.position, part.direction);
            } else {
                SpriteManager.DrawSprite(g, getTurnImage(part.direction, nextDirection), part.position);
            }
        }

        var tail = _parts.getLast();
        SpriteManager.DrawSnakeSprite(g, color, SnakePart.Tail, tail.position, tail.direction);
        EntityManager.getGrid().markAsOccupied(tail.position);
    }

    private Image getTurnImage(Direction current, Direction next) {
        var sprites = SpriteManager.getSnakeSprites(color);
        var part = switch (current) {
            case Up -> next == Direction.Left ? SnakePart.Turn3 : SnakePart.Turn2;
            case Down -> next == Direction.Left ? SnakePart.Turn4 : SnakePart.Turn1;
            case Left -> next == Direction.Down ? SnakePart.Turn2 : SnakePart.Turn1;
            case Right -> next == Direction.Down ? SnakePart.Turn3 : SnakePart.Turn4;
        };

        return sprites.get(part);
    }

    public void grow(int length) {
        _targetLength += length;
    }

    private void growOne() {
        var tail = _parts.getLast();
        var newPosition = new Point(tail.position);
        var newDirection = tail.direction;

        var newPart = new Part(newPosition);
        newPart.direction = newDirection;

        _parts.add(newPart);
    }

    public static class Part {
        public Part(Point position) {
            this.position = position;
        }

        public Direction direction = Direction.Up;
        public Point position;
    }
}
