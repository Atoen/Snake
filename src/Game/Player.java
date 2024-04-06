package Game;

import Entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int _spriteSize;
    private final Image _bodySprite;
    private final Image _tailSprite;
    private final Image _head1Sprite;
    private final Image _head0Sprite;

    private final Image[] _turnSprites;
    private final int _initiallength;

    public Direction direction;

    public final List<SnakePart> parts = new ArrayList<>();

    public final SnakePart head;


    Player(Point point, int initialLength, int spriteSize) {
        _initiallength = initialLength;

        _head0Sprite = new ImageIcon("src/sprites/head0.png").getImage();
        _head1Sprite = new ImageIcon("src/sprites/head1.png").getImage();
        _tailSprite = new ImageIcon("src/sprites/tail.png").getImage();
        _bodySprite = new ImageIcon("src/sprites/body.png").getImage();

        _turnSprites = new Image[4];
        _turnSprites[0] = new ImageIcon("src/sprites/bodyturn1.png").getImage();
        _turnSprites[1] = new ImageIcon("src/sprites/bodyturn2.png").getImage();
        _turnSprites[2] = new ImageIcon("src/sprites/bodyturn3.png").getImage();
        _turnSprites[3] = new ImageIcon("src/sprites/bodyturn4.png").getImage();

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
            DrawImageRotated(g, _head0Sprite, head.position, head.direction);
            return;
        }

        DrawImageRotated(g, _head1Sprite, head.position, head.direction);

        for (int i = 1; i < parts.size() - 1; i++) {
            var part = parts.get(i);
            var nextDirection = parts.get(i + 1).direction;
            if (part.direction == nextDirection) {
                DrawImageRotated(g, _bodySprite, part.position, part.direction);
            } else {
                DrawImageRotated(g, getTurnImage(part.direction, nextDirection), part.position, Direction.Down);
            }
        }

        var tail = parts.getLast();
        DrawImageRotated(g, _tailSprite, tail.position, tail.direction);
    }

    private Image getTurnImage(Direction current, Direction next) {
        return switch (current) {
            case Up -> next == Direction.Left ? _turnSprites[0] : _turnSprites[3];
            case Down -> next == Direction.Left ? _turnSprites[1] : _turnSprites[2];
            case Left -> next == Direction.Down ? _turnSprites[3] : _turnSprites[2];
            case Right -> next == Direction.Down ? _turnSprites[0] : _turnSprites[1];
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
