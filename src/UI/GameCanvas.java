package UI;

import Game.Direction;
import Game.ScoreUpdater;
import Game.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameCanvas extends JPanel implements ActionListener {
    private final Timer _timer;

    private static final int TimerDelay = 200;

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    public static final int CellSize = 24;

    private final SnakeGame _game;

    public GameCanvas(ScoreUpdater updater) {

        _game = new SnakeGame(updater, PanelWidth / CellSize, PanelHeight / CellSize);

        var inFocusedWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");

        getActionMap().put("left", createAction(Direction.Left));
        getActionMap().put("right", createAction(Direction.Right));
        getActionMap().put("up", createAction(Direction.Up));
        getActionMap().put("down", createAction(Direction.Down));

        _timer = new Timer(TimerDelay, this);
        _timer.start();

        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        setBackground(Color.black);
        setFocusable(true);
    }

    private AbstractAction createAction(Direction direction) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _game.setDirection(direction);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        _game.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!_game.running) {
            _timer.stop();
            return;
        }

        _game.tick();
        repaint();
    }
}
