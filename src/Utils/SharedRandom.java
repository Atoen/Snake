package Utils;

import java.util.Random;

public class SharedRandom {
    private static final Random _random = new Random();

    public static synchronized int nextInt(int bound) {
        return _random.nextInt(bound);
    }
}
