package Game;

import Entities.Apple;
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
    private final ArrayList<Apple> _apples = new ArrayList<>();
    private final ArrayList<Rock> _rocks = new ArrayList<>();

    private Player _player;

    private static final int TimerDelay = 200;

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    private static final int CellSize = 10;

    private boolean _gameRunning = true;

    private Direction _inputDirection = Direction.Up;

    public GamePanel() {

        _grid = new Grid(PanelWidth / CellSize, PanelHeight / CellSize);
        _pointGenerator = new RandomPointGenerator(_grid.getWidth(), _grid.getHeight());
        _player = new Player(new Point(_grid.getWidth() / 2, _grid.getHeight() / 2), CellSize);

        _timer = new Timer(TimerDelay, this);
        _timer.start();

        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new KeyListener());

        setObstacles();

        spawnApple();
        spawnApple();
        spawnApple();
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

    private void resetPlayer() {
        _player = new Player(new Point(_grid.getWidth() / 2, _grid.getHeight() / 2), CellSize);
    }

    private void setObstacles() {
        for (var i = 0; i < 20; i++) {
            var point = _pointGenerator.pickRandomPoint();
            _rocks.add(new Rock(point));
        }
    }

    private void spawnApple() {
        var attempt = 0;

        while (attempt++ < 100) {
            var point = _pointGenerator.pickRandomPoint();
            if (!_player.isColliding(point)) {
                _apples.add(new Apple(point));
                return;
            }
        }
    }

    private boolean CheckCollisions() {
        final var position = _player.GetHeadPosition();
        final var nextPosition = _inputDirection.move(position);

        if (nextPosition.x < 0 || nextPosition.x >= _grid.getWidth() ||
            nextPosition.y < 0 || nextPosition.y >= _grid.getHeight()) {
            resetPlayer();
            return true;
        }

        if (_player.isColliding(nextPosition)) {
            resetPlayer();
            return true;
        }

        for (var rock : _rocks) {
            if (_player.head.isColliding(rock)) {
                resetPlayer();
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
            _apples.remove(appleEaten.get());
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
            final var code = e.getKeyCode();
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
                default -> System.out.println(STR."Unhandled key code: \{code}");
            }
        }
    }
}

