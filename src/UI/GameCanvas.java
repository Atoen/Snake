package UI;

import Game.ScoreUpdater;
import Game.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCanvas extends JPanel implements ActionListener {
    private final Timer _timer;

    private static final int TimerDelay = 200;

    private static final int PanelWidth = 800;
    private static final int PanelHeight = 450;
    public static final int CellSize = 24;

    private final SnakeGame _game;

    public GameCanvas(ScoreUpdater updater) {

        _game = new SnakeGame(updater, PanelWidth / CellSize, PanelHeight / CellSize);
        _timer = new Timer(TimerDelay, this);
        _timer.start();

        setPreferredSize(new Dimension(PanelWidth, PanelHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(_game.keyListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        _game.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _game.tick();
        repaint();
    }
}

