package Game;

import Entities.*;
import Threads.TimerThread;

import java.util.ArrayList;

public class SnakeGame {

    private final ScoreUpdater _updater;
    private int _score;

    private final ArrayList<TimerThread> _threads = new ArrayList<>();

    private final Snake _player;
    private final ArrayList<AISnake> _aiPlayers = new ArrayList<>();

    public Direction inputDirection = Direction.Up;

    public SnakeGame(ScoreUpdater updater, Repainter repainter, int gridWidth, int gridHeight) {
        _updater = updater;

        EntityManager.getEntities().clear();
        EntityManager.createGrid(gridWidth, gridHeight);

        var center = EntityManager.getGrid().GetCenter();
        _player = EntityManager.createEntity(Snake.class, center, SnakeColor.Red, 3);

        setupEntities();

        _threads.add(new TimerThread(200, () -> {
            _player.direction = inputDirection;

            var collides = checkCollisions(_player, true);
            if (collides) {
                gameOver();
                return;
            }

            _player.move();
            repainter.requestRepaint();
        }));

        _threads.add(new TimerThread(300, () -> {
            var frogs = EntityManager.findAllEntities(Frog.class);
            for (var frog : frogs) {
                frog.move();
            }
            repainter.requestRepaint();
        }));

        _threads.add(new TimerThread(500, () -> {
            System.out.println("Ai snake #1");
        }));

        _threads.add(new TimerThread(500, () -> {
            System.out.println("Ai snake #2");
        }));

        startThreads();
    }

    private void startThreads() {
        _threads.forEach(TimerThread::start);
    }

    private void stopThreads() {
        _threads.forEach(TimerThread::terminate);
    }

    private void setupEntities() {
        EntityManager.spawnObstacles(20, 5);
        EntityManager.spawnEntities(Fruit.class, 3);
        EntityManager.spawnEntities(Frog.class, 2);
    }

    private void gameOver() {
        stopThreads();
        _updater.saveScore(_score);
        _updater.onGameOver();
    }

    public void setPlayerDirection(Direction newDirection) {
        switch (newDirection) {
            case Left -> {
                if (_player.direction != Direction.Right)
                    inputDirection = Direction.Left;
            }
            case Right -> {
                if (_player.direction != Direction.Left)
                    inputDirection = Direction.Right;
            }
            case Up -> {
                if (_player.direction != Direction.Down)
                    inputDirection = Direction.Up;
            }
            case Down -> {
                if (_player.direction != Direction.Up)
                    inputDirection = Direction.Down;
            }
        }
    }

    private boolean checkCollisions(Snake snake, boolean isPlayer) {
        var position = snake.getPosition();
        var nextPosition = snake.direction.translate(position);

        if (nextPosition.x < 0 || nextPosition.x > EntityManager.getGrid().getWidth() ||
            nextPosition.y < 0 || nextPosition.y > EntityManager.getGrid().getHeight()) {
            return true;
        }

        if (EntityManager.getGrid().isSpotAvailable(nextPosition)) return false;

        var entitiesToRemove = new ArrayList<Entity>();

        for (var entity : EntityManager.getEntities()) {
            if (!entity.isColliding(nextPosition)) continue;

            if (!(entity instanceof ScoreEntity scoreEntity)) return true;
            if (isPlayer) {
                _score += scoreEntity.getScore();
                _updater.updateScore(_score);
            }

            entitiesToRemove.add(entity);
            snake.grow(scoreEntity.getGrowLength());
        }

        EntityManager.getEntities().removeAll(entitiesToRemove);
        for (var entity : entitiesToRemove) {
            if (entity instanceof Fruit) {
                EntityManager.spawnEntity(Fruit.class);
            }
        }

        return false;
    }
}
