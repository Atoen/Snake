package Game;

import Entities.Entity;
import Entities.EntityManager;
import Entities.SnakePart;
import sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    public final List<SnakePart> parts = new ArrayList<>();
    public final SnakePart head;

    public Direction direction = Direction.Up;
    public Direction inputDirection = Direction.Up;

    private int _targetLength;

    public Player(Point point, int initialLength) {
        _targetLength = initialLength;

        head = EntityManager.createSnakeHead(point);
        head.direction = direction;
        parts.add(head);
    }

    public Point GetHeadPosition() {
        return parts.getFirst().getPosition();
    }

    public void draw(Graphics g) {
        var head = parts.getFirst();
        if (parts.size() == 1) {
            SpriteManager.DrawSprite(g, SpriteManager.getPlayerHead0Sprite(), head.getPosition(), head.direction);
            EntityManager.getGrid().markAsOccupied(head.getPosition());
            return;
        }

        SpriteManager.DrawSprite(g, SpriteManager.getPlayerHead1Sprite(), head.getPosition(), head.direction);

        for (int i = 1; i < parts.size() - 1; i++) {
            var part = parts.get(i);
            EntityManager.getGrid().markAsOccupied(part.getPosition());
            var nextDirection = parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                SpriteManager.DrawSprite(g, SpriteManager.getPlayerBodySprite(), part.getPosition(), part.direction);
            } else {
                SpriteManager.DrawSprite(g, getTurnImage(part.direction, nextDirection), part.getPosition());
            }
        }

        var tail = parts.getLast();
        SpriteManager.DrawSprite(g, SpriteManager.getPlayerTailSprite(), tail.getPosition(), tail.direction);
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

    public void setDirection(Direction newDirection) {
        switch (newDirection) {
            case Left -> {
                if (direction != Direction.Right)
                    inputDirection = Direction.Left;
            }
            case Right -> {
                if (direction != Direction.Left)
                    inputDirection = Direction.Right;
            }
            case Up -> {
                if (direction != Direction.Down)
                    inputDirection = Direction.Up;
            }
            case Down -> {
                if (direction != Direction.Up)
                    inputDirection = Direction.Down;
            }
        }
    }

    public void Move() {
        direction = inputDirection;
        head.direction = direction;

        if (parts.size() < _targetLength) {
            grow();
        }

        for (int i = parts.size() - 1; i >= 1; i--) {
            var currentPart = parts.get(i);
            var previousPart = parts.get(i - 1);

            currentPart.setPosition(new Point(previousPart.getPosition()));
            currentPart.direction = previousPart.direction;
        }

        var head = parts.getFirst();
        switch (direction) {
            case Up -> head.getPosition().y--;
            case Down -> head.getPosition().y++;
            case Left -> head.getPosition().x--;
            case Right -> head.getPosition().x++;
        }
    }

    public boolean isColliding(Point point) {
        for (var part : parts) {
            if (part.isColliding(point)) return true;
        }

        return false;
    }

    public void grow(int length) {
        _targetLength += length;
    }

    private void grow() {
        var tail = parts.getLast();
        var newPosition = new Point(tail.getPosition());
        var newDirection = tail.direction;

        var newBody = EntityManager.createSnakePart(newPosition);
        newBody.direction = newDirection;

        parts.add(newBody);
    }
}
