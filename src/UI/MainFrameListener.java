package UI;

/**
 * The MainFrameListener interface defines methods for handling main frame events.
 * Implementations of this interface can be used to listen for events such as exiting the game,
 * starting a new game, game over, or restarting the game.
 */
public interface MainFrameListener {
    /**
     * Called when the user exits the game.
     */
    void onExit();

    /**
     * Called when the user starts a new game.
     */
    void onStartGame();

    /**
     * Called when the game is over.
     */
    void onGameOver();

    /**
     * Called when the user chooses to restart the game.
     */
    void onRestartGame();
}
