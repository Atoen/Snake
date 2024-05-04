package Game;

import Entities.SnakePart;
import sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    public final List<SnakePart> parts = new ArrayList<>();
    public final SnakePart head;

    public Direction direction;
    private int _targetLength;

    public Player(Point point, int initialLength) {
        _targetLength = initialLength;
        direction = Direction.Up;

        head = new SnakePart(point);
        head.direction = direction;
        parts.add(head);
    }

    public Point GetHeadPosition() {
        return parts.getFirst().position;
    }

    public void draw(Graphics g) {
        var head = parts.getFirst();
        if (parts.size() == 1) {
            SpriteManager.DrawSprite(g, SpriteManager.getPlayerHead0Sprite(), head.position, head.direction);
            return;
        }

        SpriteManager.DrawSprite(g, SpriteManager.getPlayerHead1Sprite(), head.position, head.direction);

        for (int i = 1; i < parts.size() - 1; i++) {
            var part = parts.get(i);
            var nextDirection = parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                SpriteManager.DrawSprite(g, SpriteManager.getPlayerBodySprite(), part.position, part.direction);
            } else {
                SpriteManager.DrawSprite(g, getTurnImage(part.direction, nextDirection), part.position);
            }
        }

        var tail = parts.getLast();
        SpriteManager.DrawSprite(g, SpriteManager.getPlayerTailSprite(), tail.position, tail.direction);
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

    public void Move(Direction direction) {
        this.direction = direction;
        head.direction = direction;

        if (parts.size() < _targetLength) {
            grow();
        }

        for (int i = parts.size() - 1; i >= 1; i--) {
            var currentPart = parts.get(i);
            var previousPart = parts.get(i - 1);

            currentPart.position = new Point(previousPart.position);
            currentPart.direction = previousPart.direction;
        }

        var head = parts.getFirst();
        switch (direction) {
            case Up -> head.position.y--;
            case Down -> head.position.y++;
            case Left -> head.position.x--;
            case Right -> head.position.x++;
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
        var newPosition = new Point(tail.position);
        var newDirection = tail.direction;

        var newBody = new SnakePart(newPosition);
        newBody.direction = newDirection;

        parts.add(newBody);
    }
}
