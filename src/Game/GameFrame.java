package Game;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        add(new GamePanel());
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
