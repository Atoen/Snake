package UI;

import javax.swing.*;

/**
 * The MainFrame class represents the main window of the game application.
 * It extends the JFrame class and implements the MainFrameListener interface.
 * This class manages the switching between different panels of the game,
 * such as the start panel, game panel, and game over panel.
 */
public class MainFrame extends JFrame implements MainFrameListener {

    /**
     * Constructs a new MainFrame object.
     * Initializes the main window with the start panel.
     * Sets the title, default close operation, and resize properties.
     * Makes the main window visible.
     */
    public MainFrame() {
        add(new StartPanel(this));
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    /**
     * Handles the exit event triggered by the user.
     * Terminates the application.
     */
    @Override
    public void onExit() {
        System.exit(0);
    }

    /**
     * Handles the event of starting a new game.
     * Switches the panel to the game panel.
     */
    @Override
    public void onStartGame() {
        getContentPane().removeAll();
        add(new GamePanel(this));
        revalidate();
        repaint();
        pack();
    }

    /**
     * Handles the event of the game being over.
     * Switches the panel to the game over panel.
     */
    @Override
    public void onGameOver() {
        getContentPane().removeAll();
        add(new GameOverPanel(this));
        revalidate();
        repaint();
        pack();
    }

    /**
     * Handles the event of restarting the game.
     * Calls the onStartGame() method to switch to the game panel.
     */
    @Override
    public void onRestartGame() {
        onStartGame();
    }
}

