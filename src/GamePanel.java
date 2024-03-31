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

    private static final int TimerDelay = 500;

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    private static final int CellSize = 10;

    private boolean _gameRunning = true;

    private Direction _playerDirection = Direction.Up;
    private Direction _inputDirection = Direction.Up;

    public GamePanel() {

        _grid = new Grid(PanelWidth / CellSize, PanelHeight / CellSize);
        _player = new Player(_grid.getWidth() / 2, _grid.getHeight() / 2);

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

        switch (_inputDirection) {
            case Up -> _player.posY--;
            case Down -> _player.posY++;
            case Left -> _player.posX--;
            case Right -> _player.posX++;
        }

        _playerDirection = _inputDirection;

        g.setColor(Color.green);
        g.fillRect(_player.posX * CellSize, _player.posY * CellSize, CellSize, CellSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!_gameRunning) {
            _timer.stop();
        }

        repaint();
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            var code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_LEFT -> {
                    if (_playerDirection != Direction.Right)
                        _inputDirection = Direction.Left;
                }
                case KeyEvent.VK_RIGHT -> {
                    if (_playerDirection != Direction.Left)
                        _inputDirection = Direction.Right;
                }
                case KeyEvent.VK_UP -> {
                    if (_playerDirection != Direction.Down)
                        _inputDirection = Direction.Up;
                }
                case KeyEvent.VK_DOWN -> {
                    if (_playerDirection != Direction.Up)
                        _inputDirection = Direction.Down;
                }
                default -> System.out.println(STR."Unhandled key code: \{code}");
            }
        }
    }
}

