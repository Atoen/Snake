package Game;

import Entities.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SnakeGame {

    private final Grid _grid;
    private final Point _center;

    private final RandomPointGenerator _pointGenerator;
    private final ArrayList<Entity> _entities = new ArrayList<>();
    private final ScoreUpdater _updater;

    private Player _player;
    private Direction _inputDirection = Direction.Up;

    public final KeyListener keyListener = new KeyListener();
    public boolean running = true;
    private int _score;


    public SnakeGame(ScoreUpdater updater, int gridWidth, int gridHeight) {
        _updater = updater;

        _grid = new Grid(gridWidth, gridHeight);
        _pointGenerator = new RandomPointGenerator(_grid.getWidth(), _grid.getHeight());
        _center = new Point(_grid.getWidth() / 2, _grid.getHeight() / 2);

        reset();
    }

    public void draw(Graphics g) {
        for (var entity : _entities) {
            entity.Draw(g);
        }

        _player.draw(g);
    }

    public void reset() {
        _entities.clear();
        _player = new Player(_center, 1);

        setObstacles(20, 5);
        spawnFruits(3);
        spawnFrog();
    }

    public void tick() {
        if (!checkCollisions(_inputDirection)) {
            _player.Move(_inputDirection);
        }
    }

    private void setObstacles(int amount, int clearAreaRadius) {
        var clearArea = new Rectangle(_center.x - clearAreaRadius, _center.y - clearAreaRadius, clearAreaRadius * 2, clearAreaRadius * 2);
        _pointGenerator.pickRandomPointsExcept(amount, clearArea)
                .forEach(x -> {
                    var rock = new Rock(x);
                    _entities.add(rock);
                });
    }

    private void spawnFruits(int number) {
        for (var i = 0; i < number; i++) {
            spawnFruit();
        }
    }

    private void spawnFruit() {
        Point position;
        do  {
            position = _pointGenerator.pickRandomPointExcept(_entities);
        } while (_player.isColliding(position));

        var apple = new Fruit(position);
        _entities.add(apple);
    }

    private void spawnFrog() {
        Point position;
        do  {
            position = _pointGenerator.pickRandomPointExcept(_entities);
        } while (_player.isColliding(position));

        var frog = new Frog(position);
        _entities.add(frog);
    }

    public boolean checkCollisions(Direction inputDirection) {
        var position = _player.GetHeadPosition();
        var nextPosition = inputDirection.translate(position);

        if (nextPosition.x < 0 || nextPosition.x > _grid.getWidth() ||
            nextPosition.y < 0 || nextPosition.y > _grid.getHeight()) {
            reset();
            return true;
        }

        if (_player.isColliding(nextPosition)) {
            reset();
            return true;
        }

        var entitiesToRemove = new ArrayList<Entity>();

        for (var entity : _entities) {
            if (entity.isColliding(nextPosition)) {
                if (entity instanceof ScoreEntity scoreEntity) {
                    _score += scoreEntity.getScore();
                    _updater.updateScore(_score);

                    entitiesToRemove.add(entity);
                    _player.grow(scoreEntity.getGrowLength());
                } else {
                    reset();
                    return true;
                }
            }
        }

        _entities.removeAll(entitiesToRemove);
        entitiesToRemove.forEach(x -> {
            if (x instanceof Fruit) spawnFruit();
        });

        return false;
    }

    public class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            var code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_LEFT -> {
                    if (_player.direction != Direction.Right)
                        _inputDirection = Direction.Left;
                }
                case KeyEvent.VK_RIGHT -> {
                    if (_player.direction != Direction.Left)
                        _inputDirection = Direction.Right;
                }
                case KeyEvent.VK_UP -> {
                    if (_player.direction != Direction.Down)
                        _inputDirection = Direction.Up;
                }
                case KeyEvent.VK_DOWN -> {
                    if (_player.direction != Direction.Up)
                        _inputDirection = Direction.Down;
                }
            }
        }
    }
}
