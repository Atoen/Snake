package UI;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private final JButton startButton;
    private final JButton exitButton;
    private final MainFrameListener _listener;

    public StartPanel(MainFrameListener listener) {
        _listener = listener;

        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(200, 150));

        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150, 50));

        startButton.addActionListener(_ -> _listener.onStartGame());

        exitButton.addActionListener(_ -> _listener.onExit());

        add(startButton);
        add(exitButton);
    }
}
