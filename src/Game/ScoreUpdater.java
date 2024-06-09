package Game;

/**
 * The ScoreUpdater interface defines methods for updating and managing game scores.
 */
public interface ScoreUpdater {

    /**
     * Updates the game score.
     *
     * @param score The new score to update.
     */
    void updateScore(int score);

    /**
     * Saves the game score.
     *
     * @param score The score to save.
     */
    void saveScore(int score);

    /**
     * Handles actions to be performed when the game is over.
     */
    void onGameOver();
}
