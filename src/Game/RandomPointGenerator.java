package Game;

import java.awt.*;
import java.util.Random;

public class RandomPointGenerator {

    private final Random _random = new Random();
    private final int _maxX;
    private final int _maxY;

    public RandomPointGenerator(int maxX, int maxY) {
        _maxX = maxX;
        _maxY = maxY;
    }
    public Point pickRandomPoint() {
        final var x = _random.nextInt(_maxX);
        final var y = _random.nextInt(_maxY);

        return new Point(x, y);
    }
}
