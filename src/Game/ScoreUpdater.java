package Game;

public interface ScoreUpdater {
    void updateScore(int score);

    void saveScore(int score);

    void onGameOver();
}
