package Utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Score class provides utility methods for managing high scores.
 * It allows saving and reading the high score to/from a JSON file.
 */
public class Score {
    private final int score;

    /**
     * Constructs a new Score object with the specified score value.
     * @param score the score value
     */
    public Score(int score) {
        this.score = score;
    }

    /**
     * The high score.
     */
    public static int HighScore = 0;

    /**
     * Saves the high score to a JSON file.
     * @param score the score to be saved
     */
    public static void saveHighScore(int score) {
        if (score <= HighScore) return;

        var scoreObj = new Score(score);
        var gson = new Gson();

        try (var writer = new FileWriter("scores.json")) {
            gson.toJson(scoreObj, writer);
        } catch (IOException _) { }
    }

    /**
     * Reads the high score from a JSON file.
     * @return the high score
     */
    public static int readHighScore() {
        var gson = new Gson();
        try (var reader = new FileReader("scores.json")) {
            var score = gson.fromJson(reader, Score.class);
            HighScore = score.score;
            return score.score;
        } catch (IOException _) {
            return 0;
        }
    }
}

