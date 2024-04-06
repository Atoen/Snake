package Game;

import Entities.Apple;
import Entities.Entity;
import Entities.Rock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Optional;

public class GamePanel extends JPanel implements ActionListener {
    private final Grid _grid;
    private final Timer _timer;
    private final RandomPointGenerator _pointGenerator;
    private final ArrayList<Entity> _entities = new ArrayList<>();
    private final ArrayList<Apple> _apples = new ArrayList<>();
    private final ArrayList<Rock> _rocks = new ArrayList<>();
    private final ScoreUpdater _updater;

    private Player _player;

    private static final int TimerDelay = 500;

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    private static final int CellSize = 24;

    private boolean _gameRunning = true;

    private Direction _inputDirection = Direction.Up;
    private int _score;
    private final Point _center;

    public GamePanel(ScoreUpdater updater) {
        _updater = updater;

        _grid = new Grid(PanelWidth / CellSize, PanelHeight / CellSize);
        _pointGenerator = new RandomPointGenerator(_grid.getWidth(), _grid.getHeight());

        _center = new Point(_grid.getWidth() / 2, _grid.getHeight() / 2);

        _timer = new Timer(TimerDelay, this);
        _timer.start();

        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new KeyListener());

        resetGame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.red);
        for (var apple : _apples) {
            g.fillRect(apple.position.x * CellSize, apple.position.y * CellSize, CellSize, CellSize);
        }

        g.setColor(Color.gray);
        for (var rock : _rocks) {
            g.fillRect(rock.position.x * CellSize, rock.position.y * CellSize, CellSize, CellSize);
        }

        _player.draw(g);
    }

    private void resetGame() {
        _rocks.clear();
        _apples.clear();

        _player = new Player(_center, 3, CellSize);

        setObstacles(50, 5);
        spawnApples(3);
    }

    private void setObstacles(int amount, int clearAreaRadius) {
        var clearArea = new Rectangle(_center.x - clearAreaRadius, _center.y - clearAreaRadius, clearAreaRadius * 2, clearAreaRadius * 2);
        _pointGenerator.pickRandomPointsExcept(amount, clearArea)
                .forEach(x -> {
                    var rock = new Rock(x);
                    _entities.add(rock);
                    _rocks.add(rock);
                });
    }

    private void spawnApples(int number) {
        for (var i = 0; i < number; i++) {
            spawnApple();
        }
    }

    private void spawnApple() {
        Point position;
        do  {
           position = _pointGenerator.pickRandomPointExcept(_entities);
        } while (_player.isColliding(position));

        var apple = new Apple(position);

        _apples.add(apple);
        _entities.add(apple);
    }

    private boolean CheckCollisions() {
        var position = _player.GetHeadPosition();
        var nextPosition = _inputDirection.translate(position);

        if (nextPosition.x < 0 || nextPosition.x >= _grid.getWidth() ||
            nextPosition.y < 0 || nextPosition.y >= _grid.getHeight()) {
            resetGame();
            return true;
        }

        if (_player.isColliding(nextPosition)) {
            resetGame();
            return true;
        }

        for (var rock : _rocks) {
            if (_player.head.isColliding(rock)) {
                resetGame();
                return true;
            }
        }

        Optional<Apple> appleEaten = Optional.empty();
        for (var apple : _apples) {
            if (_player.head.isColliding(apple)) {
                appleEaten = Optional.of(apple);
            }
        }

        if (appleEaten.isPresent()) {
            _score += 100;
            _updater.updateScore(_score);

            _apples.remove(appleEaten.get());
            _entities.remove(appleEaten.get());
            _player.grow();

            spawnApple();
        }

        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!_gameRunning) {
            _timer.stop();
        }

        if (!CheckCollisions()) {
            _player.Move(_inputDirection);
        }

        repaint();
    }

    private class KeyListener extends KeyAdapter {
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
                case KeyEvent.VK_SPACE -> _gameRunning = !_gameRunning;
                default -> System.out.println(STR."Unhandled key code: \{code}");
            }
        }
    }
}

