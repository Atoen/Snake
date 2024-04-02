package Game;

import Entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int _size;

    public Direction direction;

    public Color headColor = Color.orange;
    public Color bodyColor = Color.green;

    private final List<SnakePart> _parts = new ArrayList<>();

    public final SnakePart head;

    Player(Point point, int size) {
        _size = size;
        direction = Direction.Up;

        head = new SnakePart(point);
        _parts.add(head);
    }

    public Point GetHeadPosition() {
        return _parts.getFirst().position;
    }

    public void draw(Graphics g) {

        final var head = _parts.getFirst();

        g.setColor(headColor);
        g.fillRect(head.position.x * _size, head.position.y * _size, _size, _size);

        if (_parts.size() == 1) return;

        g.setColor(bodyColor);

        for (int i = 1; i < _parts.size(); i++) {
            final var part = _parts.get(i);
            g.fillRect(part.position.x * _size, part.position.y * _size, _size, _size);
        }
    }

    public void Move(Direction direction) {
        this.direction = direction;

        for (int i = _parts.size() - 1; i >= 1; i--) {
            var currentPart = _parts.get(i);
            var previousPart = _parts.get(i - 1);

            currentPart.position = new Point(previousPart.position);
            currentPart.direction = previousPart.direction;
        }

        final var head = _parts.getFirst();
        switch (direction) {
            case Up -> head.position.y--;
            case Down -> head.position.y++;
            case Left -> head.position.x--;
            case Right -> head.position.x++;
        }
    }

    public boolean isColliding(Point point) {
        for (var part : _parts) {
            if (part.isColliding(point)) return true;
        }

        return false;
    }

    public void grow() {
        var tail = _parts.getLast();
        var newPosition = new Point(tail.position);
        var newDirection = tail.direction;

        var newBody = new SnakePart(newPosition);
        newBody.direction = newDirection;

        _parts.add(newBody);
    }
}
