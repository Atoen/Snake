package Entities;

import Game.Direction;
import sprites.SpriteManager;

import java.awt.*;
import java.util.Random;

public class Frog extends Entity implements ScoreEntity {

    private static final int FramesPerJump = 3;
    private static final Random _random = new Random();
    private int _frame = 0;

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

        if (++_frame >= FramesPerJump) {
            _frame = 0;
            move();
        }

        super.Draw(g);
    }

    private void move() {

        var snakeHead = EntityManager.findFirstEntity(SnakeHead.class);
        assert snakeHead != null;

        var distance = Math.sqrt(Math.pow(position.x - snakeHead.position.x, 2) + Math.pow(position.y - snakeHead.position.y, 2));
        if (distance < 5) {
            avoidSnake(snakeHead);
        } else {
            moveRandomly();
        }
    }

    private void avoidSnake(SnakeHead snakeHead) {
        double angle = Math.atan2(position.y - snakeHead.position.y, position.x - snakeHead.position.x);
        int dx = (int) Math.round(Math.cos(angle));
        int dy = (int) Math.round(Math.sin(angle));

        var newPosition = (new Point(position.x + dx, position.y + dy));
        if (isValidPosition(newPosition)) {
            position = newPosition;
        } else {
            moveRandomly();
        }

    }

    private void moveRandomly() {
        for (int i = 0; i < 5; i++) {

            var direction = Direction.fromInt(_random.nextInt(4));

            var newPosition = direction.translate(position);
            if (isValidPosition(newPosition)) {
                position = newPosition;
                return;
            }
        }
    }

    private boolean isValidPosition(Point position) {
        return position.x >= 0 && position.x < EntityManager.grid.getWidth() &&
               position.y >= 0 && position.y < EntityManager.grid.getHeight() &&
               !EntityManager.grid.isOccupied(position);
    }

    @Override
    public Image getSprite() {
        return SpriteManager.getFrogSprite();
    }
}
