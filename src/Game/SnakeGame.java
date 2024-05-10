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
        _player = EntityManager.createEntity(Snake.class, center, SnakeColor.Green, 3);

        setupEntities();

        _aiPlayers.add(EntityManager.spawnEntity(AISnake.class, SnakeColor.Blue, 2));
        _aiPlayers.add(EntityManager.spawnEntity(AISnake.class, SnakeColor.Red, 2));

        _threads.add(new TimerThread(200, _ -> {
            _player.setDirection(inputDirection);

            var collides = checkCollisions(_player, true);
            if (collides) {
                gameOver();
                return;
            }

            _player.move();

            repainter.requestRepaint();
        }));

        _threads.add(new TimerThread(300, (thread) -> {
            var frogs = EntityManager.findEntitiesOfClass(Frog.class);

            if (frogs.isEmpty()) {
                thread.terminate();
                return;
            }

            for (var frog : frogs) {
                frog.move();
            }

            repainter.requestRepaint();
        }));

        _threads.add(new TimerThread(500, (thread) -> {
            var snake = _aiPlayers.getFirst();
            snake.CalculateNextDirection();

            if (!snake.isAlive) {
                thread.terminate();
                return;
            }

            checkCollisions(snake, false);

            snake.move();
            repainter.requestRepaint();
        }));

        _threads.add(new TimerThread(500, (thread) -> {
            var snake = _aiPlayers.getLast();
            snake.CalculateNextDirection();

            if (!snake.isAlive) {
                thread.terminate();
                return;
            }

            checkCollisions(snake, false);

            snake.move();
            repainter.requestRepaint();
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
        EntityManager.spawnEntities(Fruit.class, 5);
        EntityManager.spawnEntities(Frog.class, 2);
    }

    private void gameOver() {
        stopThreads();
        _updater.saveScore(_score);
        _updater.onGameOver();
    }

    private boolean checkCollisions(Snake snake, boolean isPlayer) {
        var position = snake.getPosition();
        var nextPosition = snake.direction.translate(position);

        if (nextPosition.x < 0 || nextPosition.x > EntityManager.getGrid().getWidth() ||
            nextPosition.y < 0 || nextPosition.y > EntityManager.getGrid().getHeight()) {
            return true;
        }

        if (EntityManager.getGrid().isSpotEmpty(nextPosition)) return false;

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
