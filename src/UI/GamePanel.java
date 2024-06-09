package UI;

import Game.ScoreUpdater;
import Utils.Score;

import javax.swing.*;
import java.awt.*;

/**
 * The GamePanel class represents the panel where the game is played.
 * It extends JPanel and implements the ScoreUpdater interface.
 * This class displays the current score, high score, and the game canvas.
 */
public class GamePanel extends JPanel implements ScoreUpdater {
    private final JLabel _scoreValueLabel;
    private final JLabel _highScoreValueLabel;
    private final MainFrameListener _listener;

    /**
     * Constructs a new GamePanel with the specified listener.
     * @param listener the listener for game events
     */
    public GamePanel(MainFrameListener listener) {
        _listener = listener;

        var topPanel = new JPanel(new BorderLayout());

        var border = BorderFactory.createEmptyBorder(5, 10, 5, 10);

        var scoreLabel = new JLabel("Score: ");
        var highScoreLabel = new JLabel("HighScore: ");
        _scoreValueLabel = new JLabel("0");
        _highScoreValueLabel = new JLabel("0");

        _scoreValueLabel.setBorder(border);
        _highScoreValueLabel.setBorder(border);

        var scorePanel = new JPanel(new BorderLayout());
        var highScorePanel = new JPanel(new BorderLayout());

        scorePanel.add(scoreLabel, BorderLayout.WEST);
        scorePanel.add(_scoreValueLabel, BorderLayout.EAST);

        highScorePanel.add(highScoreLabel, BorderLayout.WEST);
        highScorePanel.add(_highScoreValueLabel, BorderLayout.EAST);

        topPanel.add(scorePanel, BorderLayout.WEST);
        topPanel.add(highScorePanel, BorderLayout.EAST);

        add(topPanel);
        add(new GameCanvas(this));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        updateHighScore();
    }

    /**
     * Updates the displayed score.
     * @param score the new score value
     */
    public void updateScore(int score) {
        _scoreValueLabel.setText(Integer.toString(score));
    }

    /**
     * Saves the high score.
     * @param score the score to be saved
     */
    public void saveScore(int score) {
        Score.saveHighScore(score);
    }

    /**
     * Notifies the listener when the game is over.
     */
    public void onGameOver() {
        _listener.onGameOver();
    }

    /**
     * Updates the displayed high score.
     */
    public void updateHighScore() {
        _highScoreValueLabel.setText(Integer.toString(Score.readHighScore()));
    }
}

