package Game;

import Entities.*;

import java.awt.*;
import java.util.ArrayList;

public class SnakeGame {
    private final Point _center;

    private final RandomPointGenerator _pointGenerator;
    private final ScoreUpdater _updater;

    private final Player _player;
    private Direction _inputDirection = Direction.Up;

    public boolean running = true;
    private int _score;

    public SnakeGame(ScoreUpdater updater, int gridWidth, int gridHeight) {
        _updater = updater;

        EntityManager.entities.clear();
        EntityManager.createGrid(gridWidth, gridHeight);

        _pointGenerator = new RandomPointGenerator(EntityManager.grid.getWidth(), EntityManager.grid.getHeight());
        _center = new Point(EntityManager.grid.getWidth() / 2, EntityManager.grid.getHeight() / 2);
        _player = new Player(_center, 3);

        setupEntities();
    }

    public void draw(Graphics g) {
        EntityManager.drawNonPlayerEntities(g);
        _player.draw(g);
    }

    private void setupEntities() {
        setObstacles(20, 5);
        spawnFruits(3);
        spawnFrog();
    }

    private void gameOver() {
        _updater.saveScore(_score);
        _updater.onGameOver();
        running = false;
    }

    public void setDirection(Direction direction) {
        switch (direction) {
            case Left -> {
                if (_player.direction != Direction.Right)
                    _inputDirection = Direction.Left;
            }
            case Right -> {
                if (_player.direction != Direction.Left)
                    _inputDirection = Direction.Right;
            }
            case Up -> {
                if (_player.direction != Direction.Down)
                    _inputDirection = Direction.Up;
            }
            case Down -> {
                if (_player.direction != Direction.Up)
                    _inputDirection = Direction.Down;
            }
        }
    }

    public void tick() {
        var collides = checkCollisions(_inputDirection);
        if (collides) {
            gameOver();
            return;
        }

        _player.Move(_inputDirection);
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
            position = _pointGenerator.pickRandomPointExcept(EntityManager.entities);
        } while (_player.isColliding(position));

        EntityManager.createFruit(position);
    }

    private void spawnFrog() {
        Point position;
        do  {
            position = _pointGenerator.pickRandomPointExcept(EntityManager.entities);
        } while (_player.isColliding(position));

        EntityManager.createFrog(position);
    }

    public boolean checkCollisions(Direction inputDirection) {
        var position = _player.GetHeadPosition();
        var nextPosition = inputDirection.translate(position);

        if (nextPosition.x < 0 || nextPosition.x > EntityManager.grid.getWidth() ||
            nextPosition.y < 0 || nextPosition.y > EntityManager.grid.getHeight()) {
            return true;
        }

        if (_player.isColliding(nextPosition)) {
            return true;
        }

        var entitiesToRemove = new ArrayList<Entity>();

        for (var entity : EntityManager.entities) {
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

        EntityManager.entities.removeAll(entitiesToRemove);
        entitiesToRemove.forEach(x -> {
            if (x instanceof Fruit) spawnFruit();
        });

        return false;
    }
}
