package Game;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame implements ScoreUpdater {
    private final JLabel _scoreValueLabel;
    private final JLabel _highScoreValueLabel;

    public GameFrame() {
        var mainPanel = new JPanel();
        var topPanel = new JPanel(new BorderLayout());

        var border = BorderFactory.createEmptyBorder(5, 10, 5, 10);;

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

        mainPanel.add(topPanel);
        mainPanel.add(new GamePanel(this));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        add(mainPanel);

        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    public void updateScore(int score) {
        _scoreValueLabel.setText(Integer.toString(score));
    }

    public void updateHighScore(int highScore) {
        _highScoreValueLabel.setText(Integer.toString(highScore));
    }
}
