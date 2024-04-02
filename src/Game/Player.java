package Game;

import Entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int _spriteSize;

    public Direction direction;

    public Color headColor = Color.orange;
    public Color bodyColor = Color.green;

    public final List<SnakePart> parts = new ArrayList<>();

    public final SnakePart head;

    Player(Point point, int initialLength, int spriteSize) {
        _spriteSize = spriteSize;
        direction = Direction.Up;

        head = new SnakePart(point);
        head.direction = direction;
        parts.add(head);

        for (var i = 0; i < initialLength; i++) {
            grow();
        }
    }

    public Point GetHeadPosition() {
        return parts.getFirst().position;
    }

    public void draw(Graphics g) {

        final var head = parts.getFirst();

        g.setColor(headColor);
        g.fillRect(head.position.x * _spriteSize, head.position.y * _spriteSize, _spriteSize, _spriteSize);

        if (parts.size() == 1) return;

//        g.setColor(bodyColor);

        for (int i = 1; i < parts.size(); i++) {
            final var part = parts.get(i);

            switch (part.direction) {
                case Up -> g.setColor(Color.cyan);
                case Down -> g.setColor(Color.magenta);
                case Left -> g.setColor(Color.white);
                case Right -> g.setColor(Color.PINK);
            }


            g.fillRect(part.position.x * _spriteSize, part.position.y * _spriteSize, _spriteSize, _spriteSize);
        }
    }

    public void Move(Direction direction) {
        this.direction = direction;
        head.direction = direction;

        for (int i = parts.size() - 1; i >= 1; i--) {
            var currentPart = parts.get(i);
            var previousPart = parts.get(i - 1);

            currentPart.position = new Point(previousPart.position);
            currentPart.direction = previousPart.direction;
        }

        final var head = parts.getFirst();
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
