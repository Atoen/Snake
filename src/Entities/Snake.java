package Entities;

import Game.Direction;
import sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends Entity implements MovingEntity {

    private final List<Snake.SnakePart> _parts = new ArrayList<>();
    private final Snake.SnakePart _head;

    private int _targetLength;

    public Direction direction = Direction.Up;

    public Snake(Point point, int initialLength) {
        super(point);

        _targetLength = initialLength;
        _head = new SnakePart(point);
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
            SpriteManager.DrawSprite(g, SpriteManager.getPlayerHead0Sprite(), _head.position, _head.direction);
            return;
        }

        SpriteManager.DrawSprite(g, SpriteManager.getPlayerHead1Sprite(), _head.position, _head.direction);

        for (int i = 1; i < _parts.size() - 1; i++) {
            var part = _parts.get(i);
            EntityManager.getGrid().markAsOccupied(part.position);
            var nextDirection = _parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                SpriteManager.DrawSprite(g, SpriteManager.getPlayerBodySprite(), part.position, part.direction);
            } else {
                SpriteManager.DrawSprite(g, getTurnImage(part.direction, nextDirection), part.position);
            }
        }

        var tail = _parts.getLast();
        SpriteManager.DrawSprite(g, SpriteManager.getPlayerTailSprite(), tail.position, tail.direction);
        EntityManager.getGrid().markAsOccupied(tail.position);

    }

    private Image getTurnImage(Direction current, Direction next) {
        var sprites = SpriteManager.getPlayerBodyTurnSprites();
        return switch (current) {
            case Up -> next == Direction.Left ? sprites[2] : sprites[1];
            case Down -> next == Direction.Left ? sprites[3] : sprites[0];
            case Left -> next == Direction.Down ? sprites[1] : sprites[0];
            case Right -> next == Direction.Down ? sprites[2] : sprites[3];
        };
    }

    public void grow(int length) {
        _targetLength += length;
    }

    private void growOne() {
        var tail = _parts.getLast();
        var newPosition = new Point(tail.position);
        var newDirection = tail.direction;

        var newPart = new SnakePart(newPosition);
        newPart.direction = newDirection;

        _parts.add(newPart);
    }

    public static class SnakePart {
        public SnakePart(Point position) {
            this.position = position;
        }

        public Direction direction = Direction.Up;
        public Point position;
    }
}
