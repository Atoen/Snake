package UI;

import Entities.EntityManager;
import Game.Direction;
import Game.Repainter;
import Game.ScoreUpdater;
import Game.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GameCanvas extends JPanel implements Repainter {

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    public static final int CellSize = 24;

    private final SnakeGame _game;

    public GameCanvas(ScoreUpdater updater) {

        _game = new SnakeGame(updater, this, PanelWidth / CellSize, PanelHeight / CellSize);

        var inFocusedWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        getInputMap(inFocusedWindow).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");

        getActionMap().put("left", createAction(Direction.Left));
        getActionMap().put("right", createAction(Direction.Right));
        getActionMap().put("up", createAction(Direction.Up));
        getActionMap().put("down", createAction(Direction.Down));

        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        setBackground(Color.black);
        setFocusable(true);
    }

    private AbstractAction createAction(Direction direction) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _game.inputDirection = direction;
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        EntityManager.drawEntities(g);
    }

    @Override
    public void requestRepaint() {
        repaint();
    }
}
