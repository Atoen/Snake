package UI;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    public GameOverPanel(MainFrameListener listener) {
        setLayout(new BorderLayout());

        var buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        var _restartButton = new JButton("Restart");
        _restartButton.setPreferredSize(new Dimension(180, 60));
        _restartButton.addActionListener(_ -> listener.onRestartGame());

        var _quitButton = new JButton("Quit");
        _quitButton.setPreferredSize(new Dimension(180, 60));
        _quitButton.addActionListener(_ -> listener.onExit());

        buttonPanel.add(_restartButton);
        buttonPanel.add(_quitButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}