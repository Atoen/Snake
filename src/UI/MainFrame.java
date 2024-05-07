package UI;

import javax.swing.*;

public class MainFrame extends JFrame implements MainFrameListener {

    public MainFrame() {
        add(new StartPanel(this));
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    @Override
    public void onExit() {
        System.exit(0);
    }

    @Override
    public void onStartGame() {
        getContentPane().removeAll();
        add(new GamePanel(this));
        revalidate();
        repaint();
        pack();
    }

    @Override
    public void onGameOver() {
        getContentPane().removeAll();
        add(new GameOverPanel(this));
        revalidate();
        repaint();
        pack();
    }

    @Override
    public void onRestartGame() {
        onStartGame();
    }
}
