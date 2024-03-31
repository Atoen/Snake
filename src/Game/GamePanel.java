package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private final Grid _grid;
    private final Timer _timer;

    private final Player _player;

    private static final int TimerDelay = 50;
    private static final int StepsToGrow = 1;
    private int _step;

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    private static final int CellSize = 10;

    private boolean _gameRunning = true;

    private Direction _inputDirection = Direction.Up;

    public GamePanel() {

        _grid = new Grid(PanelWidth / CellSize, PanelHeight / CellSize);
        _player = new Player(new Point(_grid.getWidth() / 2, _grid.getHeight() / 2), CellSize);

        _timer = new Timer(TimerDelay, this);
        _timer.start();

        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new KeyListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        _player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!_gameRunning) {
            _timer.stop();
        }

        if (_step++ >= StepsToGrow) {
            _player.grow();
            _step = 0;
        }

        _player.Move(_inputDirection);

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

