package Entities;

import Game.Direction;
import Game.Player;
import sprites.SpriteManager;

import java.awt.*;
import java.util.Random;

public class Frog extends Entity implements ScoreEntity {
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
        var snakeHead = EntityManager.findFirstEntity(SnakeHead.class);
        assert snakeHead != null;

        var distance = Math.sqrt(Math.pow(getPosition().x - snakeHead.getPosition().x, 2) + Math.pow(getPosition().y - snakeHead.getPosition().y, 2));
        if (distance < 8) {
            avoidSnake(snakeHead);
        } else {
            moveRandomly();
        }
    }

    private void avoidSnake(SnakeHead snakeHead) {
        double angle = Math.atan2(getPosition().y - snakeHead.getPosition().y, getPosition().x - snakeHead.getPosition().x);
        int dx = (int) Math.round(Math.cos(angle));
        int dy = (int) Math.round(Math.sin(angle));

        var newPosition = (new Point(getPosition().x + dx, getPosition().y + dy));
        if (EntityManager.getGrid().isValidPosition(newPosition)) {
            setPosition(newPosition);
        } else {
            moveRandomly();
        }
    }

    private void moveRandomly() {
        for (int i = 0; i < 5; i++) {

            var direction = Direction.fromInt(_random.nextInt(4));

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
