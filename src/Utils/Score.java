package Utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {
    private final int score;

    public Score(int score) {
        this.score = score;
    }

    public static int HighScore = 0;

    public static void saveHighScore(int score) {
        if (score <= HighScore) return;

        var scoreObj = new Score(score);
        var gson = new Gson();

        try (var writer = new FileWriter("scores.json")) {
            gson.toJson(scoreObj, writer);
        } catch (IOException _) { }
    }

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
