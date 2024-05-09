package Entities;

import Game.Direction;
import Sprites.SpriteManager;

import java.awt.*;
import java.util.Random;

public class Frog extends Entity implements ScoreEntity, MovingEntity {
    private static final Random _random = new Random();

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
        var snake = EntityManager.findFirstEntity(Snake.class);
        assert snake != null;

        var distance = Math.sqrt(Math.pow(getPosition().x - snake.getPosition().x, 2) + Math.pow(getPosition().y - snake.getPosition().y, 2));
        if (distance < 8) {
            avoidSnake(snake);
        } else {
            moveRandomly();
        }
    }

    private void avoidSnake(Snake snake) {
        var angle = Math.atan2(getPosition().y - snake.getPosition().y, getPosition().x - snake.getPosition().x);
        var dx = (int) Math.round(Math.cos(angle));
        var dy = (int) Math.round(Math.sin(angle));

        var newPosition = (new Point(getPosition().x + dx, getPosition().y + dy));
        if (EntityManager.getGrid().isValidPosition(newPosition)) {
            setPosition(newPosition);
        } else {
            moveRandomly();
        }
    }

    private void moveRandomly() {
        var index = _random.nextInt(4);
        for (var i = 0; i < 4; i++) {

            var rotatedIndex = (index + i) % 4;
            var direction = Direction.fromInt(rotatedIndex);

            var newPosition = direction.translate(getPosition());
            if (EntityManager.getGrid().isValidPosition(newPosition)) {
                setPosition(newPosition);
                return;
            }
        }
    }

    @Override
    public Image getSprite() {
        return SpriteManager.getFrogSprite();
    }
}
