package Game;

import Entities.SnakePart;
import sprites.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int _spriteSize;
    private final int _initiallength;

    public Direction direction;

    public final List<SnakePart> parts = new ArrayList<>();

    public final SnakePart head;


    public Player(Point point, int initialLength, int spriteSize) {
        _initiallength = initialLength;

        _spriteSize = spriteSize;
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
            DrawImageRotated(g, SpriteManager.getPlayerHead0Sprite(), head.position, head.direction);
            return;
        }

        DrawImageRotated(g, SpriteManager.getPlayerHead1Sprite(), head.position, head.direction);

        for (int i = 1; i < parts.size() - 1; i++) {
            var part = parts.get(i);
            var nextDirection = parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                DrawImageRotated(g, SpriteManager.getPlayerBodySprite(), part.position, part.direction);
            } else {
                DrawImageRotated(g, getTurnImage(part.direction, nextDirection), part.position, Direction.Down);
            }
        }

        var tail = parts.getLast();
        DrawImageRotated(g, SpriteManager.getPlayerTailSprite(), tail.position, tail.direction);
    }

    private Image getTurnImage(Direction current, Direction next) {
        var sprites = SpriteManager.getPlayerBodyTurnSprites();
        return switch (current) {
            case Up -> next == Direction.Left ? sprites[0] : sprites[3];
            case Down -> next == Direction.Left ? sprites[1] : sprites[2];
            case Left -> next == Direction.Down ? sprites[3] : sprites[2];
            case Right -> next == Direction.Down ? sprites[0] : sprites[1];
        };
    }

    private void DrawImageRotated(Graphics g, Image image, Point position, Direction direction) {
        var g2d = (Graphics2D) g;
        var startingTransform = g2d.getTransform();

        var centerX = position.x * _spriteSize + _spriteSize / 2;
        var centerY = position.y * _spriteSize + _spriteSize / 2;

        g2d.translate(centerX, centerY);
        g2d.rotate(direction.getRadians());

        g2d.drawImage(image, -_spriteSize / 2, -_spriteSize / 2, _spriteSize, _spriteSize, null);
        g2d.setTransform(startingTransform);
    }

    public void Move(Direction direction) {
        this.direction = direction;
        head.direction = direction;

        if (parts.size() < _initiallength) {
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

    public void grow() {
        var tail = parts.getLast();
        var newPosition = new Point(tail.position);
        var newDirection = tail.direction;

        var newBody = new SnakePart(newPosition);
        newBody.direction = newDirection;

        parts.add(newBody);
    }
}
