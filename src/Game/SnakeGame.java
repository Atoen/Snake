package Game;

import Entities.*;
import Threads.TimerThread;

import java.awt.*;
import java.util.ArrayList;

public class SnakeGame {
    private final Point _center;

    private final RandomPointGenerator _pointGenerator;
    private final ScoreUpdater _updater;
    private final Snake _player;

    private int _score;

    private final TimerThread[] _threads = new TimerThread[4];
    public Direction inputDirection = Direction.Up;

    public SnakeGame(ScoreUpdater updater, Repainter repainter, int gridWidth, int gridHeight) {
        _updater = updater;

        EntityManager.getEntities().clear();
        EntityManager.createGrid(gridWidth, gridHeight);

        _pointGenerator = new RandomPointGenerator(EntityManager.getGrid().getWidth(), EntityManager.getGrid().getHeight());
        _center = new Point(EntityManager.getGrid().getWidth() / 2, EntityManager.getGrid().getHeight() / 2);
        _player = EntityManager.createSnake(_center, 10);

        setupEntities();

        _threads[0] = new TimerThread(200, () -> {
            var collides = checkCollisions();
            if (collides) {
                gameOver();
                return;
            }

            _player.direction = inputDirection;
            _player.move();
            repainter.requestRepaint();
        });

        _threads[1] = new TimerThread(300, () -> {
            var frogs = EntityManager.findAllEntities(Frog.class);
            for (var frog : frogs) {
                frog.move();
            }
            repainter.requestRepaint();
        });

        _threads[2] = new TimerThread(500, () -> {
            System.out.println("Ai snake #1");
        });

        _threads[3] = new TimerThread(500, () -> {
            System.out.println("Ai snake #2");
        });

        _threads[0].start();
        _threads[1].start();
        _threads[2].start();
        _threads[3].start();
    }

    private void stopThreads() {
        for (var thread : _threads) {
            thread.terminate();
        }
    }

    private void setupEntities() {
        setObstacles(20, 5);
        spawnFruits(3);
        spawnFrog();
        spawnFrog();
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

    private void setObstacles(int amount, int clearAreaRadius) {
        var clearArea = new Rectangle(_center.x - clearAreaRadius, _center.y - clearAreaRadius, clearAreaRadius * 2, clearAreaRadius * 2);
        _pointGenerator.pickRandomPointsExcept(amount, clearArea)
                .forEach(EntityManager::createRock);
    }

    private void spawnFruits(int number) {
        for (var i = 0; i < number; i++) {
            spawnFruit();
        }
    }

    private void spawnFruit() {
        Point position;
        do  {
            position = _pointGenerator.pickRandomPointExcept(EntityManager.getEntities());
        } while (_player.isColliding(position));

        EntityManager.createFruit(position);
    }

    private void spawnFrog() {
        Point position;
        do  {
            position = _pointGenerator.pickRandomPointExcept(EntityManager.getEntities());
        } while (_player.isColliding(position));

        EntityManager.createFrog(position);
    }

    public boolean checkCollisions() {
        var position = _player.getPosition();
        var nextPosition = _player.direction.translate(position);

        if (nextPosition.x < 0 || nextPosition.x > EntityManager.getGrid().getWidth() ||
            nextPosition.y < 0 || nextPosition.y > EntityManager.getGrid().getHeight()) {
            return true;
        }

        if (EntityManager.getGrid().isSpotAvailable(nextPosition)) return false;

        var entitiesToRemove = new ArrayList<Entity>();

        for (var entity : EntityManager.getEntities()) {
            if (entity.isColliding(nextPosition)) {
                if (entity instanceof ScoreEntity scoreEntity) {
                    _score += scoreEntity.getScore();
                    _updater.updateScore(_score);

                    entitiesToRemove.add(entity);
                    _player.grow(scoreEntity.getGrowLength());
                } else {
                    return true;
                }
            }
        }

        EntityManager.getEntities().removeAll(entitiesToRemove);
        entitiesToRemove.forEach(x -> {
            if (x instanceof Fruit) spawnFruit();
        });

        return false;
    }
}
