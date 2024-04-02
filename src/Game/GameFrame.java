package Game;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        var mainPanel = new JPanel();
        var labelText = new JLabel("Score: ");

        mainPanel.add(labelText);

        mainPanel.add(new GamePanel());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        add(mainPanel);

        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
